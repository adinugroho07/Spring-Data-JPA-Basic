package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Category;
import com.adi.belajarjpa.model.Members;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityEvenetListenerTest {

    @Test
    void PrePersistEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();

        category.setName("Ice Boba");
        category.setDescription("Kopi susu gula aren dengan boba");
        //pengisian createdAt dan updatedAt akan di isi kan oleh event listener

        entityManager.persist(category);

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void PreUpdateEntity() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category = entityManager.find(Category.class, 3);

        category.setName("Ice Kopi Boba");

        //perubahan value pada kolom updatedAt akan di isi kan oleh event listener

        entityManager.merge(category);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void eventListenerEntityClass() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Members members = new Members();

        members = entityManager.find(Members.class, 4);
        Assertions.assertEquals("Mr. Adi Nugroho Again", members.getFullName());
        System.out.println(members.getFullName());
        //attribute fullname akan di isi kan oleh event listerner di entity class Members.
        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void eventListenerEntityClassAgain() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Members members = new Members();

        members = entityManager.find(Members.class, 4);
        members.getName().setLastName("Banget");

        entityManager.merge(members);
        //untuk ngetest apakah kolom updated_at nya di updated oleh event listener. silahkan di liat di db nya.

        entityTransaction.commit();
        entityManager.close();

    }
}
