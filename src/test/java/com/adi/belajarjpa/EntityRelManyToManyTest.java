package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Products;
import com.adi.belajarjpa.model.Users;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class EntityRelManyToManyTest {

    @Test
    void insertDataMiddleTable() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Users users = entityManager.find(Users.class,"X1");
        //syntaks ini yang membuat JPA menghapus dulu semua data users_like_products yang user_id nya sama dengan yang kita punya
        users.setLikes(new HashSet<>());
        users.getLikes().add(entityManager.find(Products.class,"P1"));
        users.getLikes().add(entityManager.find(Products.class,"P2"));
        users.getLikes().add(entityManager.find(Products.class,"P3"));

        entityManager.merge(users);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void relationTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Users users = entityManager.find(Users.class,"X1");
        Assertions.assertNotNull(users);
        users.getLikes().forEach(products -> {
            System.out.println(products.getName());
            System.out.println(products.getPrice());
        });

        entityTransaction.commit();
        entityManager.close();
    }
}
