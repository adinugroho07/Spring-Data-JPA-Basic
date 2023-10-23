package com.adi.belajarjpa.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    /*
    * embeded id ini konsep nyaa sama dengan embeded. kalo embed yang sebelum nya adalah kolom2 yang bukan id
    * kalo ini hanya kolom yang merupakan id (di tabel posisi nya kolom nya sebagai primary key).
    *
    * ini bisa kita gunakan jika case nya adalah di dalam 1 tabel ini terdapat lebih dari 1 primary key, karena
    * di Entity class itu tidak memungkin kan untuk mempunyai lebih dari 1 @Id, maka konsep EmbededId ini bisa di
    * gunakan.
    *
    * anotasi @EmbeddedId ini akan sama fungsi dengan anotasi @Id
    * */

    @EmbeddedId
    private DepartmentID id;

    private String name;
}
