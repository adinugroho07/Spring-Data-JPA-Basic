package com.adi.belajarjpa;

import com.adi.belajarjpa.model.CustomerType;
import com.adi.belajarjpa.model.Customers;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class EnumTest {

    @Test
    void enumTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customers customers = new Customers();
        customers.setName("");
        customers.setAge(40);
        customers.setIsmarried(false);
        customers.setBirthdate(new Date());
        //contoh pengisian Enum value.
        customers.setType(CustomerType.PREMIUM);

        entityManager.persist(customers);

        entityTransaction.commit();
        entityManager.close();
    }
}
