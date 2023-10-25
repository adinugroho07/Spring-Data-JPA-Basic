package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//contoh namedQueries. jika kita hanya ingin membuat 1 alias query kita bisa menggunakan @NamedQuery() saja, gausah @NamedQueries
@NamedQueries({//@NamedQueries di gunakan jika kita ingin membuat alias Query lebih dari 1.
        @NamedQuery(name = "Brands.findAll", query = "select b from Brands b order by b.id"),
        @NamedQuery(name = "Brands.findAllByName", query = "select b from Brands b where b.name = :name"),
        @NamedQuery(name = "Brands.findById", query = "select b from Brands b where b.id = :id")
})
@NamedNativeQueries({//Named Native Query, hampir sama dengan NamedQuery biasa cuman di sini snytaks SQL nya adalah syntaks SQL DB.
        @NamedNativeQuery(name = "Brands.native.findAllOrderByName",
                query = "select * from brands where brands.created_at is not null order by brands.name asc",
                resultClass = Brands.class)
})
public class Brands extends AuditableEntity<String>{

    @Id
    private String id;

    private String name;

    private String description;

    /*
    * OneToMany Relastion : relasi dimana 1 data mempunyai relasi ke beberapa data di table lain.
    * @OneToMany -> anotasi untuk menunjukkan one to many
    * @ManyToOne -> anotasi untuk menunjukkan many to one.
    *
    * di one to many ini table yang menjadi many dalam relasi nya harus di bungkus dengan collection seperti List,dll.
    *
    * di class Brands ini jadi schema nya adalah dimana 1 brands mempunyai beberapa Products.
    *
    * cara join nya sama dengan one to one, kita bisa join di table yang memiliki foreign key dan di table itu kita set
    * relasinya adalah ManyToOne, sedangkan di table yang tidak memiliki foreign key kita set sebagai OneToMany. itu lah
    * kenapa di class Brands ini di set OneToMany dan di class Products di set ManyToOne, karena memang di class Products
    * itu dia terdapat kolom untuk foregin key nya.
    *
    * */

    @OneToMany(mappedBy = "brands", fetch = FetchType.LAZY)//karena tidak mempunyai kolom foreign key maka di set OneToMany
    private List<Products> products;

    /*
    * Fetch -> cara JPA melakukan query ke db beserta yang di join2 kan.
    * Fetch type ini ada 2 EAGER dan LAZY ;
    * EAGER : saat kita melakukan find Entity atau query select ke sebuah table, maka seluruh Entity/table yang berelasi
    *         dengan Entity yang kita find akan di select juga. jadi select semua table dan relasi2 nya biar pun tidak
    *         di butuhkan datanya. jika di cek di query nya dia akan di query join untuk EAGER.
    * LAZY : saat kita melakukan find Entity maka, Entity yang berelasi dengan Entity yang kita find baru akan di panggil
    *        jika properties/attribute nya di panggil, jika tidak di tidak di panggil maka tidak akan di query oleh JPA.
    *        jika di cek di query nya dia tidak akan di query join untuk relasi2 nya, namun akan di query ulang table2
    *        yang berelasi namun di kasih where nya id sama dengan id yang ada di parent table nya.
    *
    * Defautl Value di relasi join :
    * One to One   : EAGER
    * One to Many  : LAZY
    * Many to One  : EAGER
    * Many to Many : LAZY
    *
    * jika kita ingin mengubah default value fetch nya bisa di rubah di attribute annotasi relasi nya.
    * @OneToOne(fetch = FetchType.LAZY)
    * */

    @Version
    private Long version;//penambahan versi data yang akan di jadikan acuan optimistic locking
    /*
    * LOCKING DATA
    * Locking -> aksi untuk mencegah data berubah dalam jeda waktu ketika kita melakukan Read,Write data di table.
    * Locking ada 2 jenis :
    * Optimistic Locking -> proses multiple transaksi, dimana tiap transaksi tidak melakukan lock terhadap data. namun
    *                       sebelum melakukan commit transaksi, tiap transaksi akan mengecek terlabih dahulu apakah data
    *                       sudah berubah atau belum, jika sudah berubah di karenakan transaksi lain, maka transaksi
    *                       tersebut akan melakukan rollback. jadi dia itu lebih cepat karena tidak tunggu menungg atau
    *                       data nya tidak di lock, nanti ketika sudah ada 1 data yang ter update maka transaksi yang
    *                       lain akan di rollback.
    * Pessimistic Locking -> proses multiple transaksi, dimana tiap transaksi akan melakukan lock terhadap data yang
    *                        digunakan. jadi 1 data akan di lock untuk di proses dan jika ada data lain yang akan
    *                        memproses transaksi yang sama maka dia harus menunggu transaksi yang di lock itu di buka
    *                        atau selesai. karena harus menunggu lock data selesai baru bisa proses jadi nya banyak
    *                        tunggu menunggu sehingga proses nya jadi lama.
    *
    *
    * jadi optimistic ini adalah lock yang balap2an cepat memproses transaksi dengan data yang sama. yang paling cepat
    * akan menang dan akan melakukan perubahan data atau retrieve data. data2 yang kalah cepat maka akan error dan di
    * rollback. jadi kenapa namanya optimistic locking, karena transaksi yang pertama selesai akan optimis bahwa datanya
    * tidak akan di rubah oleh transaksi lain dengan data yang sama. contoh ada 10 transaksi yang melakukan perubahan data
    * di data yang sama, maka 10 transaksi itu akan berlomba siapa yang cepat dalam memproses data itu (update). yang
    * paling cepat akan jadi pemenang sehingga dari 10 transaksi tadi pasti ada 1 pemenang nya dan yang kalah 9 transaksi
    * tersebut akan di rollback dan error.
    *
    * cara locking optimistic JPA berkerja :
    * 1. menambahkan version data pada class entity (menggunakan annotaasi @Version) untuk memberitahu jika ada perubahan
    *    pada entity. JPA mendukung dua jenis tipe data version, Number (Integer, Short, Long) dan Timestamp (java.sql.Timestamp, java.time.Instant)
    *
    * 2. pada saat beberapa transaksi ini akan melakukan commit, transaksi akan melakukan pengecekan version terlebih
    *    dahulu, jika ternyata version nya sudah berubah maka artinya sudah ada pemenang dari balapan proses data ini
    *    sehingga secara otomatis transaksi akan melakukan rollback.
    *
    * 3. JPA akan secara otomatis mengupdate attribut version/versi setiap kali kita melakukan update data tersebut
    *
    * konsekuensi dari optimistic locking ini adalah transaksi nya akan jadi lebih sering melakukan rollback ketimbang
    * commit.
    *
    * Pessimistic Locking ini locking yang akan me lock data ketika di select dan menjadikan transaksi lain tidak bisa
    * mengubah data sampai transaksi yang pertama melakukan lock selesai melakukan commit transaksi. semua jenis2 lock
    * ini type nya adalah ENUM LockModeType, detail nya bisa di lihat di sini.
    * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/lockmodetype
    * jenis-jenis locking :
    * - NONE -> Tidak ada lock, semua lock terjadi di akhir transaksi ketika data di update
    * - READ / OPTIMISTIC -> Versi entity akan di cek di akhir transaksi (ini adalah Optimistic Locking)
    * - WRITE / OPTIMISTIC_FORCE_INCREMENT -> Versi entity akan dinaikkan secara otomatis walaupun data tidak di update
    * - PESSIMISTIC_FORCE_INCREMENT -> Entity akan di lock secara pessimistic, dan versi akan dinaikkan walaupun data
    *                                  tidak di update
    * - PESSIMISTIC_READ Entity -> akan di lock secara pessimistic menggunakan shared lock (jika database mendukung),
    *                              SELECT FOR SHARE
    * - PESSIMISTIC_WRITE Entity -> akan di lock secara explicit, SELECT FOR UPDATE
    *
    * kenapa nama nya Pessimistic Locking, karena ketika transaksi pertama sudah sukses, bisa saja datanya di ubah oleh
    * transaksi selanjutnya walaupun transaksi pertama lebih dulu selesai.
    * */
}
