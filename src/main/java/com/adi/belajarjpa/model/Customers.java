package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")//untuk set nama table di DB nya biar berbeda dengan nama class Entity nya.
public class Customers {

    /*
    * Entity adalah sebuah class yang merepresentasikan table di database. jadi entity ini bisa kita sebuat juga
    * sebagai model.
    *
    * Cara membuat Entity adalah kita perlu membuat sebuah class dimana attribute di dalam class tersebut akan
    * merepresentasikan kolom2 di table nya dan di class tersebut di berikan annotasi @Entity seperti contoh di atas.
    * tiap2 attribute yang memiliki setter dan getter di class Entity tersebut, maka akan merepresentasikan kolom di
    * database nya. di class ini setter dan getternya di cover oleh lombok.
    *
    * Di dalam class Entity di wajib kan memiliki default construktor atau construktor tanpa parameter, agar JPA bisa
    * membuat object baru tanpa parameter ketika melakukan mapping data dari table ke object Entity. jika di class
    * Entity tidak di berikan default construktor, maka dia akan error ketika mapping data dari table ke object Entity.
    *
    * saat membuat class entity, JPA mewajibkan tiap class entity memiliki Primary key dengan menambahkan anotasi
    * @Id pada attribute class. biasanya di berikan di attribute dengan tipe data integer.
    *
    * nama class entity ini bisa menjadi nama table pada DB, namun kita juga bisa setup untuk nama table nya
    * berbeda dengan nama class Entity nya dengan cara memberikan anotasi @Table(name="nama table") seperti
    * contoh di atas.
    *
    * secara default attribute yang ada di dalam Entity class akan menjadi kolom di DB, namun kita bisa mengeset secara
    * manual nama kolom tersebut untuk tidak sama dengan nama attribute pada class Entity. caranya adalah dengan
    * menggunakan anotasi @Column(name = "nama kolom"). di dalam parameter tersebut kita juga bisa mendefine beberapa
    * hal seperti length atau maximal panjang charakter yang boleh, precision untuk mengatur angka di blakang koma, dll.
    *
    * di database , untuk id dia memiliki fitur auto increment dimana value id nyaa nanti akan secara otomatis di insert
    * kan oleh DB. hal ini menyebabkan dalam JPA kita tidak bisa mengubah value id nya karena value id nya akan di buat
    * oleh database. oleh karena itu kita bisa menandai bawah value id di buat secara otomatis oleh database dengan
    * menambahkan anotasi @GeneratedValue(strategy atau generator).
    *
    * selain menggunakan auto increment kita juga bisa menggunakan cara lain dalam membuat id otomatis, yaitu bisa
    * dengan membuat sequence di table, atau menggunakan table lain untuk memlakukan management id atau bahkan
    * menggunakan UUID. oleh karena itu kita di spring harus memberitahu kan strategy apa yang di gunakan untuk membuat
    * id otomatis tersebut. berikut strategy nya :
    * - AUTO : untuk pengisian ID otomatis dia akan ngecek dulu tipe data key attribute, kalo numeric (int,float,dll)
    *          maka akan menggunakan sequence atau table generator, tapi kalo tipe nya UUID maka akan menggunakan
    *          UUID generator.
    * - IDENTITY : untuk pengisian ID otomatis menggunakan auto increment di table database.
    * - SEQUENCE : untuk pengisian ID otomatis menggunakan sequence yang ada di DB
    * - TABLE : untuk pengisian ID otomatis menggunakan table yang mengatur id2 tersebut unique.
    *           metode ini mempunyai kekurangan, yaitu susah jika kita mau scale up table nya dan performance
    *           cost nya tinggi.
    * - UUID : untuk pengisian ID otomatis menggunakan keys yang di generate oleh tipe data UUID.
    * jelasnya bisa buka link berikut :
    * - https://www.baeldung.com/hibernate-identifiers
    * - https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/generationtype
    *
    * pada tipe data attribute class entity nanti akan di mapping kan oleh JPA dengan tipe data di DB nya. di sarankan
    * untuk tipe data attribute class menggunakan tipe data object (Integer,String,Boolean) bukan yang
    * non object (int,float,boolean).
    * */

    @Id//setting primary key di clas entity. ini wajib.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//untuk menandakan id nya auto increment oleh DB.
    private Integer id;

    @Column(name = "name")//setting nama kolom di db, biar ga ngikut nama attribute.
    private String name;

    private Integer age;

    private Boolean ismarried;

    private Date birthdate;

    /*
    * di JPA kita juga bisa menyimpan data dengan tipe data Enum. cara nya adalah kita di Entity nya kita berikan
    * anotasi @Enumerated() pada attribute di entity nya seperti contoh di bawah. di parameter entitynya kita
    * masukkan apakah akan menyimpan data Enum nya secara String atau kan interger. jika dengan String maka
    * nanti yang akan di store ke DB adalah value dari Enum nya, jika dengan Interger maka yang di store adalah
    * index dari value Enum nya.
    *
    * Sangat di saran kan kita menggunakan yang String karena jika menggunakan yang interger jika ada perubahan
    * data enum maka posisi index nya akan berubah.
    * */
    @Enumerated(EnumType.STRING)
    private CustomerType type;

    /*
    * secara default seluruh attribute di class entity akan di anggap sebagai colom di table db.
    * jika kita membutuhkan sebuah attribute di dalam class entity tapi tidak di anggap sebagai colom
    * di table db maka kita perlu tambahkan anotasi @Transient pada attribute tersebut, seperti contoh
    * di bawah.
    * */
    @Transient//akan di exclude dari mapping JPA ke kolom table.
    private String fullname;
}
