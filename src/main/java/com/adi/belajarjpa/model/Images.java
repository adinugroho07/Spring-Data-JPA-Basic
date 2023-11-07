package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Images {

    /*
    * di database terdapat 2 jenis Large Object (LOB) :
    * - Character Large Object (CLOB) -> untuk menampung tipe data text yang besar
    * - Binary Large Object (BLOB) -> untuk menamping tipe data binary besar seperti gambar,video,audio,dll.
    *
    * representasi Large Object di java ole JDBC bisa menggunakan 2 interface :
    * - java.sql.Clob --> untuk Character Large Object
    * - java.sql.Blob --> untuk Binary Large Object
    *
    * namun untuk menggunakan java.sql.Clob dan java.sql.Blob agak menyulitkan karena harus membaca data file nya
    * secara manual menggunakan java IO. oleh karena itu Di JPA juga bisa di gunakan untuk secara otomatis mengkonversi
    * tipe data Large Object ke tipe data yang lebih familiar. berikut mapping nya
    * - String dan Char[] --> untuk tipe data Character Large Object (CLOB)
    * - byte[] --> untuk tipe data Binary Large Object (BLOB)
    * untuk biar JPA bisa secara otomatis mengkonversikan tipe data Large Object ke tipe data yang familiar dengan
    * kita maka pada attribute entity nya perlu di tambahkan anotasi @Lob
    *
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob//anotasi untuk mengkonversi large object
    private String description;

    @Lob
    private byte[] image;
}
