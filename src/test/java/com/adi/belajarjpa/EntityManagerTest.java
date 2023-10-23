package com.adi.belajarjpa;

import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {

    /*
    * jadi EntityManagerFactory itu mirip seperti datasource, di gunakan untuk memanagement EntityManager.
    * sedangkan EntityManager itu mirip seperti Connection pada JDBC, dimana jika kita ingin berinteraksi dengan
    * database ini, maka kita akan menggunakan EntityManager.
    *
    * EntityManager di butuhkan ketika kita akan berinteraksi dengan database dan setelah selesai kita berinteraksi
    * maka kita perlu menutup kembali EntityManager tersebut, sama seperti ketika kita menggunakan Connection pada JDBC
    * kalo sudah tidak di gunakan kita perlu tutup.
    * */

    @Test
    void create() {
        //create EntityManagerFactory atau get object EntityManagerFactory
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //nantinya object EntityManager ini yang akan kita gunakan untuk CRUD ke DB.
        Assertions.assertNotNull(entityManager);

        entityManager.close();
        entityManagerFactory.close();

    }
}
