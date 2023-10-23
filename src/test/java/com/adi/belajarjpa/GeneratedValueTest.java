package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Category;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneratedValueTest {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    @Test
    void insertCategory() {
        //test auto increment id
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Category category = new Category();
            category.setName("Makanan");
            category.setDescription("makan-makan terus");

            entityManager.persist(category);

        } catch (Exception e){
            entityTransaction.rollback();
        }

        entityTransaction.commit();
        entityManager.close();

    }
}
