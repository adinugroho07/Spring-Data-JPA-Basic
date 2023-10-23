package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Customers;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class CRUDTest {

    /*
    * untuk melakukan proses CRUD ke database, kita bisa menggunakan EntityManager yang secara otomatis JPA akan
    * membuatkan perintah SQL untuk Insert,Update,Delete dan Read dari Entity class yang kita gunakan.
    *
    * EntityManager method untuk CRUD :
    * - persist(Entity) : untuk menyimpan entity atau insert data ke db
    * - merge(Entity) : untuk mengupdate entity atau update data ke db
    * - remove(Entity) : untuk menghapus entity atau delete data ke db
    * - find(Class, id) : untuk read data berdasarkan entity class dan id entity.
    * */

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    @Test
    void insertTest() {
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //create EntityTransaction dari EntityManager untuk mengcommit atau rollback transaksi.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //inisialisasi data dan set data entity
            Customers customers = new Customers();
            customers.setName("adi baru");
            customers.setAge(28);
            customers.setIsmarried(true);
            customers.setBirthdate(new Date());

            //save transaction or insert transaction
            entityManager.persist(customers);

        } catch (Exception e){
            System.out.println(e);
            entityTransaction.rollback();
        }
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void findByIDTest() {
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //create EntityTransaction dari EntityManager untuk mengcommit atau rollback transaksi.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //inisialisasi data dan set data entity
            Customers customers = new Customers();

            //find transaction by id
            customers = entityManager.find(Customers.class, 1);
            System.out.println("name : " + customers.getName());
            System.out.println("age : " + customers.getAge());
            System.out.println("birthdate : " + customers.getBirthdate().toString());
            Assertions.assertNotNull(customers);
            Assertions.assertEquals("adi", customers.getName());
            Assertions.assertEquals(1, customers.getId());

        } catch (Exception e){
            System.out.println(e);
            entityTransaction.rollback();
        }
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void updateTest() {
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //create EntityTransaction dari EntityManager untuk mengcommit atau rollback transaksi.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //inisialisasi data dan set data entity
            Customers customers = new Customers();

            //find transaction by id
            customers = entityManager.find(Customers.class, 1);
            //set new value
            customers.setName("Adi Nugroho");
            customers.setAge(25);

            //update transaction
            entityManager.merge(customers);

        } catch (Exception e){
            System.out.println(e);
            entityTransaction.rollback();
        }
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void deleteTest() {
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //create EntityTransaction dari EntityManager untuk mengcommit atau rollback transaksi.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //inisialisasi data dan set data entity
            Customers customers = new Customers();

            //find transaction by id
            customers = entityManager.find(Customers.class, 2);

            //delete transaction
            entityManager.remove(customers);

        } catch (Exception e){
            System.out.println(e);
            entityTransaction.rollback();
        }
        entityTransaction.commit();
        entityManager.close();
    }
}
