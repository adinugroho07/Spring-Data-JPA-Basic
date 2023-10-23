package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Category;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTest {

    @Test
    void dateAndTime() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("Chiki");
        category.setDescription("Chiki chetoos");
        category.setUpdatedAt(LocalDateTime.now());
        category.setCreatedAt(Calendar.getInstance());

        entityManager.persist(category);

        entityTransaction.commit();
        entityManager.close();
    }
}
