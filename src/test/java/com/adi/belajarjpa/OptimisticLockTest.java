package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class OptimisticLockTest {

    @Test
    void insertBrandTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brands brands = new Brands();
        brands.setId("X2");
        brands.setName("Apple");
        brands.setDescription("Another Brand sultan");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        entityManager.persist(brands);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void optimisticDemo() throws InterruptedException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brands brands = entityManager.find(Brands.class,"X2");
        brands.setName("Apple IOS");
        brands.setDescription("Another Brand sultan yang lebih sultan dari samsung");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        //simulasi transaksi ini kita pending 10 detik dan kita salip dengan transaksi yang lain, jadi seharusnya error.
        Thread.sleep(10 * 1000L);

        //untuk data version nanti dia akan secara otomatis di update sama JPA.
        entityManager.merge(brands);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void optimisticDemoPasangan() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        //transaksi yang akan menyalip transaksi di atas yang di beri jeda 10 detik.
        Brands brands = entityManager.find(Brands.class,"X2");
        brands.setName("APPLE");
        brands.setDescription("Another Brand sultan yang lebih sultan dari samsung");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        entityManager.merge(brands);

        entityTransaction.commit();
        entityManager.close();
    }


}
