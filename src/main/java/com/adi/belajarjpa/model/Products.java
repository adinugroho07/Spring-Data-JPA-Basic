package com.adi.belajarjpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    private String id;

    private String name;

    private Long price;

    private String description;

    @ManyToOne
    @JoinColumn(//relasi nya
            name = "brand_id",//nama kolom foreign key pada table products
            referencedColumnName = "id"//nama kolom primary key pada table brands
    )
    private Brands brands;//properties brands ini akan menjadi value pada mappedBy di class brands.
    //kenapa config join nya berada di class Products , karena di class/table products ini terdapat kolom yang
    //menampung foreign key relasi antara table Brands dan table Products.


    //contoh penggunaan many to many
    @ManyToMany(mappedBy = "likes")//menggunakan config properties yang sudah di define pada class Users.
    private Set<Users> likedBy;
}
