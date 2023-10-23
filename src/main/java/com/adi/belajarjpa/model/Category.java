package com.adi.belajarjpa.model;

import com.adi.belajarjpa.Listener.UpdatedAtAware;
import com.adi.belajarjpa.Listener.UpdatedAtListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
@EntityListeners({ //register event listener berupa class2 yang merupakan event entity listener.
        UpdatedAtListener.class
})
public class Category implements UpdatedAtAware {
//harus implement UpdatedAtAware supaya set method nya di panggil

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//menggunakan auto increment table.
    private Integer id;

    private String name;

    private String description;

    /*
    * JPA juga bisa melakukan mapping tipe data Date and Time secara otomatis.
    * mapping tipe data date and time adalah sebagai berikut :
    * Tipe Data Database -> Class di Java
    * DATE -> java.sql.Date, java.time.LocalDate
    * TIME -> java.sql.Time, java.time.LocalTime
    * TIMESTAMP -> java.sql.Timestamp, java.time.Instant, java.time.LocalDateTime, java.time.OffsetDateTime ,
    *              java.time.ZonedDateTime
    *
    * jadi misal kita menggunakan tipe data java.sql.Timestamp maka akan secara otomatis JPA akan memapping kan ke
    * kolom yang mempunyai tipe data TIMESTAMP.
    *
    * sebisa mungkin kita menggunakan package java.time di banding nyakan java.util karena jpa tidak memapping kan
    * DATE dan TIME dari package java.util. sehingga jika kita menggunakan package java.util di attribute entity nya
    * kita perlu menambahkan anotasi @Temporal() dan di parameter anotasi nya kita perlu sebutkan, apakah
    * attribute tipe data ini akan di mapping kan oleh JPA ke tipe data di database nya, ke TIMESTAMP kah, ke DATE
    * atau ke TIME.
    *
    * contoh package java.util -> java.util.Date dan java.util.Calendar
    * */

    @Temporal(TemporalType.TIMESTAMP)//untuk di mappingkan ke tipe data di DB
    @Column(name = "created_at")
    private Calendar createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;//secara otomatis akan di mappingkan ke TIMESTAMP.

}
