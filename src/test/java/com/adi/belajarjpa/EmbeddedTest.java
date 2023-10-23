package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Members;
import com.adi.belajarjpa.model.Name;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmbeddedTest {

    @Test
    void embedTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Members members = new Members();
        members.setEmail("adisiub_2012@yahoo.com");
        members.setCreateddAt(LocalDateTime.now());
        members.setUpdatedAt(LocalDateTime.now());

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Adi");
        name.setMiddleName("Nugroho");
        name.setLastName("Nugroho");
        members.setName(name);

        entityManager.persist(members);

        entityTransaction.commit();
        entityManager.close();
    }


}
