package com.adi.belajarjpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credentials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    @Id
    private String Id;

    private String email;

    private String password;

    //ini join one to one dengan table Users dan config join nya menggunakan properties credentials yang ada di class
    //Users.
    @OneToOne(mappedBy = "credentials")
    //jadi jika properties config join nya di class Users berubah, maka value pada mappedBy ini punerubah sesuai dengan
    //nama properties config join yang ada di class Users.
    private Users users;
}
