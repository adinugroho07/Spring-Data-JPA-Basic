package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PessimisticLockingTest {

    @Test
    void pessimisticDemo1() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        //menggunakan locking pessimistic PESSIMISTIC_WRITE, jadi dia akan di lock dulu untuk transaksi update data.
        Brands brands = entityManager.find(Brands.class,"X2", LockModeType.PESSIMISTIC_WRITE);
        brands.setName("Apple");
        brands.setDescription("Another Brand sultan yang lebih sultan dari samsung update Optimistic");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        //ketika transaksi ini di eksekusi brarti tidak ada transaksi lain yang bisa melakukan take over transaksi ini.
        Thread.sleep(10 * 1000L);

        entityManager.persist(brands);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void pessimisticDemo2() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        //menggunakan locking pessimistic PESSIMISTIC_WRITE, jadi dia akan di lock dulu untuk transaksi update data.
        Brands brands = entityManager.find(Brands.class,"X2", LockModeType.PESSIMISTIC_WRITE);
        brands.setName("Apple");
        brands.setDescription("Another Brand sultan yang lebih sultan dari samsung update Optimistic second");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        //transaksi ini akan menunggu transaksi sebelum nya selesai dulu.
        entityManager.persist(brands);

        entityTransaction.commit();
        entityManager.close();
    }
}
