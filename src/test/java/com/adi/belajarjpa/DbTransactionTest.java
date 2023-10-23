package com.adi.belajarjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class DbTransactionTest {

    /*
    * saat menggunakan database, fitur yang wajib kita mengerti adalah database transaction. saat menggunakan JDBC secara
    * default operasi ke DB adalah auto commit, sehingga kita perlu membuat transaction. namun di JPA secara default kita
    * wajib menggunakan database transactional saat melakukan operasi manipulasi data entity.
    *
    * Transaction di JPA di representasikan dalam interface EntityTransaction.
    * di dalam interface EntityTransaction terdapat beberapa mthod yang selalu kita gunakan :
    * - begin() : untuk memulai transaksi.
    * - commit() : untuk commit transaksi
    * - rollback() : untuk merollback transaksi
    * - setRollbackOnly() : untuk mengeset bahwa transaksi tersebut tidak akan di commit, namun di rollback only.
    * - isActive() : untuk mengecek apakah transaksi ini masih active atau tidak. karena sebuah transaksi jika sudah
    *                di commit atau di rollback maka otomatis transaksi tersebut menjadi tidak active.
    *
    * dalam hal memanipulasi data menggunakan JPA kita perlu menggunakan Transaction, namun jika kita hanya perlu membaca
    * data saja, maka kita juga bisa tidak menggunakan transaction tapi memang di saran kan jika kita ingin berinteraksi
    * dengan JPA (CRUD) usahakan menggunakan database transaction.
    * */

    @Test
    void transaction() {
        //create EntityManagerFactory atau get object EntityManagerFactory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("belajarjpa");
        //create EntityManager dari EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //create EntityTransaction dari EntityManager untuk mengcommit atau rollback transaksi.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            //operasi manipulasi data dengan EntityManager

            entityTransaction.commit();
        } catch (Throwable throwable){
            //jika di transaksi nya ada error maka di rollback.
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();

    }
}
