package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    /*
    * OneToOne adalah sebuah relasi Table dimana 1 data di table A berelasi dengan 1 data di table B dan hanya 1
    * row data saja.
    *
    * di class ini ada contoh terkait join table OneToOne antara table Users dengan Table Credentials.
    * join antar 2 table ini menggunakan Primary key. oleh karena itu Annotation yang di gunakan adalah
    * @PrimaryKeyJoinColumn, bukan @JoinColumn .
    *
    * Join OneToOne dengan JPA ini terdapat 2 cara untuk berelasi :
    * - Join Dengan Foreignkey --> menggunakan @JoinColumn.
    * - Join dengan Primarykey yang sama --> menggunakan @PrimaryKeyJoinColumn
    *
    * untuk membuat entity berelasi dengan entity lain nya kita membutuhkan sebuah anotasi relasi
    * salah satu nya adalah @OneToOne.
    *
    * @OneToOne
    * @PrimaryKeyJoinColumn( name = "id", referencedColumnName = "id") -> artinya adalah Entity Users ini melakukan
    * join dengan Entity lain. Relasi nya menggunakan primary key yang sama (menggunakan anotasi @PrimaryKeyJoinColumn)
    * di join kan pada kolom Id di table users (di isi kan pada parameter name) dengan kolom Id di table credentilas
    * (di isi kan pada parameter referencedColumnName).
    * */

    @Id
    private String Id;

    private String name;

    //join table dari users ke table credentials
    @OneToOne//relasi nya
    @PrimaryKeyJoinColumn(
            name = "id", //nama kolom primary key pada table Users
            referencedColumnName = "id"//nama kolom primary key pada table Credentials.
    )
    //jadi ini mendefinisikan bahwa table users ini akan di join dengan table Credential menggunakan primary key.
    //name adalah nama kolom di table users nya yang akan di join, referencedColumnName nama kolom di table credentials
    //yang juga akan di join kan dengan table users.
    private Credentials credentials;//properties credentials ini akan menjadi value pada mappedBy di class Credentials.


    //ini join one to one dengan table wallet dan config join nya menggunakan properties users yang ada di class Wallet
    @OneToOne(mappedBy = "users")
    private Wallet wallet;


    /*
    * Users Join ManyToMany -> schema nya adalah user bisa like banyak product dan product juga bisa di like oleh
    *                          banyak user. namun dalam join many to many ini pun harus mempunyai table di tengah
    *                          sebagai translator.
    * yang membedakan dengan relasi lain nya, relasi many to many ini membutuhkan tabel tambahan di tengah untuk sebagai
    * translator atau jembatan. oleh karena itu jika join nya tidak lagi menggunakan annotasi @JoinColumn atau
    * @PrimaryKeyJoinColumn, namun kita menggunakan @JoinTable
    *
    * @JoinTable(
            name = "users_like_products",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    * code di atas ini artinya table users akan di join kan dengan table users_like_products. kolom yang di join kan
    * adalah kolom id dari table users dan kolom user_id dari table users_like_products. dan di joinkan juga ke table
    * product dengan kolom id dari table product dan product_id dari table users_like_products. jadi tabel
    * users_like_products ini sama menjoin kan antara table Users dan table products.
    * */
    @ManyToMany
    @JoinTable(
            name = "users_like_products",//nama table di tengah nya atau jembatannya
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            //join ke table Users lewat kolom id dari table user dan kolom user_id dari tabel users_like_products
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
            //join ke table Products lewat kolom id dari table Products dan kolom product_id dari tabel users_like_products
    )
    private Set<Products> likes;//properties likes ini akan menjadi value pada mappedBy di class Products.
}
