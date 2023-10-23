package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Credentials;
import com.adi.belajarjpa.model.Users;
import com.adi.belajarjpa.model.Wallet;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityRelOneToOneTest {

    @Test
    void InsertTblCredentialsAndUsers() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Credentials credentials = new Credentials();
        credentials.setId("X1");
        credentials.setEmail("adisiub_2012@yahoo.com");
        credentials.setPassword("adisiub");

        Users users = new Users();
        users.setId("X1");
        users.setName("Adi Nugroho");

        entityManager.persist(users);
        entityManager.persist(credentials);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void relationsOneToOneTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Users users = new Users();
        //kita memanggil data Users maka data di table credentials pun akan terpanggil data dengan ID yang sama.
        //karena sudah berlasi antara Entity Users dengan Entity Credentials.
        users = entityManager.find(Users.class,"X1");
        Assertions.assertNotNull(users);

        Assertions.assertEquals("adisiub_2012@yahoo.com", users.getCredentials().getEmail());
        Assertions.assertEquals("adisiub", users.getCredentials().getPassword());
        Assertions.assertEquals(1_000_000L, users.getWallet().getBalance());

        Credentials credentials = new Credentials();
        //untuk ngetest sebaliknya jika table credential yang di panggil maka data pada table Users dengan
        //primary key yang sama juga akan ter panggil.
        credentials = entityManager.find(Credentials.class, "X1");
        Assertions.assertNotNull(credentials);

        Assertions.assertEquals("Adi Nugroho",credentials.getUsers().getName());

        //test untuk table wallet dan relasinya dengan table users dan credentials
        Wallet wallet = new Wallet();
        wallet = entityManager.find(Wallet.class,1);
        Assertions.assertNotNull(wallet);

        Assertions.assertEquals("Adi Nugroho", wallet.getUsers().getName());
        Assertions.assertEquals("adisiub", wallet.getUsers().getCredentials().getPassword());


        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void insertDataToWallet() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Users users = new Users();
        users = entityManager.find(Users.class,"X1");

        Wallet wallet = new Wallet();
        wallet.setUsers(users);
        wallet.setBalance(1_000_000L);

        entityManager.persist(wallet);

        entityTransaction.commit();
        entityManager.close();

    }
}
