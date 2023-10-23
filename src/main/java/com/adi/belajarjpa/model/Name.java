package com.adi.belajarjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable //untuk menandakan bahwa class ini bisa meng embedd.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Name {

    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

}
