package com.adi.belajarjpa.Listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.Calendar;

public class UpdatedAtListener {

    /*
    * class Entity Event Listener -> merupakan sebuah listener jika ada operation pada sebuah entity dia akan
    * ter trigger untuk melakukan sebuah action. class listener ini bisa di apply di class entity mana pun yang
    * mempunyai kolom yang sama. ini bisa di gunakan contoh jika kita ingin melakukan update atau insert data
    * dengan entity maka class event entity listener ini bisa di panggil.
    *
    * Action :
    * @PrePersist -> Dieksekusi sebelum melakukan persist entity
    * @PostPersist -> Dieksekusi setelah melakukan persist entity
    * @PreRemove -> Dieksekusi sebelum melakukan remove entity
    * @PostRemove -> Dieksekusi setelah melakukan remove entity
    * @PreUpdate -> Dieksekusi sebelum melakukan update entity
    * @PostUpdate -> Dieksekusi setelah melakukan update entity
    * @PostLoad -> Dieksekusi setelah melakukan load entity
    *
    * kita juga membuat event listener di dalam class entity 1-1 namun kekurangannya adalah listerner tersebut hanya
    * akan berlaku di class entity itu sendiri, tidak bisa di gunakan di class entity yang lain. contoh nya ada di
    * class Members.
    * */


    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware object){
        object.setUpdatedAt(LocalDateTime.now());//set update kolom updated_at
    }

    @PrePersist
    public void setLastOfCreatedDate(UpdatedAtAware object){
        object.setCreatedAt(Calendar.getInstance());//set update kolom created_at
        object.setUpdatedAt(LocalDateTime.now());//set update kolom updated_at
    }
}
