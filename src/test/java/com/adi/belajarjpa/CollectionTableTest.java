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
import java.util.HashMap;

public class CollectionTableTest {

    @Test
    void collectionTest() {
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

        //add data to collection table.
        members.setHobbies(new ArrayList<>());
        members.getHobbies().add("Coding");
        members.getHobbies().add("Badminton");

        entityManager.persist(members);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void MapCollectionTableTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Name name = new Name();
        Members members = new Members();

        name.setTitle("Mr");
        name.setFirstName("Adi");
        name.setMiddleName("Nugroho");
        name.setLastName("Again");

        members.setName(name);
        members.setEmail("adisiub2012@yahoo.com");
        members.setCreateddAt(LocalDateTime.now());
        members.setUpdatedAt(LocalDateTime.now());

        members.setHobbies(new ArrayList<>());
        members.getHobbies().add("Eating");
        members.getHobbies().add("Shooting");

        members.setSkills(new HashMap<>());
        members.getSkills().put("Java", 100);
        members.getSkills().put("Golang", 70);
        members.getSkills().put("PHP", 80);

        entityManager.persist(members);
        entityTransaction.commit();
        entityManager.close();

    }
}
