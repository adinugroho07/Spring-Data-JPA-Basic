package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long balance;

    //contoh join dengan menggunakan foreignkey
    @OneToOne
    @JoinColumn(
            name = "user_id",//nama kolom foreign key pada table wallet
            referencedColumnName = "id"//nama kolom primary key pada table Users
    )
    private Users users;//properties users ini akan menjadi value pada mappedBy di class Users.
    //kenapa config join nya di store di class Wallet, karena di class Wallet ini lah yang mempunyai kolom untuk
    //foreign key.
}
