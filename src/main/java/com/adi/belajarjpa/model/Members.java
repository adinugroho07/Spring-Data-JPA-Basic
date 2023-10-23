package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "members")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Members {

    /*
    * konsep Embeded Entity.
    * saat kita membuat class entity biasanya kita membuat seluruh attribute yang di gunakan untuk
    * mendefine kolom pada table di DB. jadi kayak 1 class entity ini merepresentasikan 1 table di DB.
    * namun bagaimana jika kolom yang ada di table tersebut ada 50 kolom, brarti kita akan membuat
    * 50 juga untuk attribute di class entity nya dan ini akan menjadi masalah karena code nya akan menjadi
    * panjang.
    *
    * oleh karena itu JPA memungkin kan kita untuk memecah2 attribute pada class entity. jadi misal beberapa
    * attribute di supply oleh class A, beberapa lagi oleh class B, hingga jumlah attribute nya sama dengan
    * jumlah kolom di table DB dan tetap class entity tersebut merepresentasikan 1 table di DB. konsep ini di
    * JPA kita sebut sebagai Embedd.
    *
    * untuk menandai sebuah class akan mensupply attribute nya untuk di consume sebagai attribute yang di mapping
    * ke table DB, perlu di berikan sebuah anotasi @Embeddable seperti di class Name. di class embedded tersebut
    * juga bisa di tambahkan anotasi @Column untuk memapping kan ke colom di table seperti di class Name.
    *
    * saat kita menggunakan attibute class yang ada di class Embedded di class Entity, maka kita wajib untuk
    * menambahkan anotasi @Embedded di attribute pada class Entity.
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    @Embedded
    private Name name;
    //embed dari class name, attribute yang ada di class name akan ikut di mapping ke tabel members oleh class entity ini.

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createddAt;

    //event listener di entity class. penjelasannya ada di class UpdatedAtListener
    @PrePersist
    public void setCreatedAndUpdatedDate(){
        updatedAt = LocalDateTime.now();
        createddAt = LocalDateTime.now();
    }

    //event listener di entity class. penjelasannya ada di class UpdatedAtListener
    @PreUpdate
    public void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }

    /*
    * collection table ini adalah versin lebih ringkas nya (tidak perlu membuat entity) dari join table. jadi
    * fokus nya hanya untuk join ke table nya dan ambil data yang di butuhkan. untuk melakukan hal ini tipe data
    * dari variable nya harus collection seperti List, Set dll.
    *
    * @ElementCollection -> untuk menandakan attribute ini menggunakan collection
    * @CollectionTable -> untuk menentukan table mana yang di gunakan sebagai table untuk menyimpan data collection.
    *
    * @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    * artinya adalah table collection yang kita buat ini nama nya hobbies, join nyaa menggunakan member_id dan di join
    * di table membernya denga kolom id.
    *
    * @Column(name = "name")
    * artinya dari table collection ini kita mau ambil kolom apa, karena di table collection ini tidak ada model nya
    * maka kita state kolom apa yang mau di ambil.
    *
    * semua collection table ini jika ada update dia akan delete dulu semua data yang id nya sama dengan id yang akan
    * di update baru dia insert data baru. oleh karena itu harus di perhatikan jika ada relasi lain yang merefer ke
    * table collection ini supaya tidak error. karena dia insert data baru maka id nya akan selalu berubah jika ada
    * update. jadi harus di pastikan bahwa id pada collection table ini tidak menjadi foreign key pada table lain.
    * maka untuk update data lebih aman menggunakan JPA Entity Relationship.
    * */

    @ElementCollection//menandakan bahwa element ini merupakan collection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    @Column(name = "name")//mengambil nama kolom name di table hobbies.
    private List<String> hobbies;//tipe datanya berupa string, buka object class atau entity.

    /*
    * hampir sama dengan List dan Set collection, bedanya adalah karana ini Map membutuhkan key dan value,
    * maka kita perlu men state mana kolom yang di jadikan key dan mana kolom yang di jadikan value menggunakan
    * annotation @MapKeyColumn dan @Column.
    *
    * @MapKeyColumn -> untuk memberitahu JPA kolom table yang di gunakan sebagai key
    * @Column -> untuk memberitahu JPA kolom table yang di gunakan sebagai value
    * */

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "name")//kolom yang di jadikan key.
    @Column(name = "value")//colom yang di jadikan value
    private Map<String,Integer> skills;

    @Transient
    private String fullName;

    //contoh event listener di entity class.
    @PostLoad
    public void postLoad(){
        fullName = name.getTitle() + ". " + name.getFirstName() + " " + name.getMiddleName() + " " + name.getLastName();
    }
}
