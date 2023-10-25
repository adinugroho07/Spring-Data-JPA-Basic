package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NamedQueriesTest {

    @Test
    void brandsNamedQueryTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * Named Query
        * fitur pada JPA dimana kita bisa membuat alias untuk JPA QL yang kita buat. Named Query ini di tempatkan di
        * class entity sesuai dengan query nya (bisa cek di Class/Entity Brands). keuntungannya menggunakan alias ini
        * adalah kita tidak perlu menuliskan lagi Query JPA nya jika kita membutuhkan query yang sama, jadi tinggal di
        * panggil saja alias nya (reusable).
        *
        * untuk memanggil Named Query kita bisa menggunakan fungsi entityManager.createNamedQuery("alias",Entity class);
        *
        * di class entity nya terdapat 2 anotasi untuk membuat NamedQuery atau alias query:
        * 1. @NamedQuery() -> pembuatan single Named Query , syntaks: @NamedQuery(name = "alias", query = "query")
        *    contoh: @NamedQuery(name = "Brand.findAll", query = "select b from Brands b order by b.id")
        * 2. @NamedQueries() -> pembuatan multiple NamedQuery, syntaks: @NamedQueries({ @NamedQuery(),@NamedQuery() })
        *    contoh : @NamedQueries({
        *   @NamedQuery(name = "Brand.findAll", query = "select b from Brands b order by b.id"),
        *   @NamedQuery(name = "Brand.findAllByName", query = "select b from Brands b where b.name = :name")
        * })
        *
        * untuk Naming alias query biasakan menggunakan prefix Entity.alias.
        * contoh : Brands.findAll (Brands = Entity, findAll = nama aliasnya)
        *
        * jika di NamedQuery itu terdapat parameter, maka kita wajib untuk mengisikan parameter tersebut dengan menggunakan
        * fungsi setParameter("parameterName","value") pada Class TypedQuery.
        * */

        //contoh pemanggilan Named Query pada Entity Brands dengan alias query Brand.findAll
        TypedQuery<Brands> namedQuery = entityManager.createNamedQuery("Brands.findAll", Brands.class);
        List<Brands> resultList = namedQuery.getResultList();
        for (Brands brands : resultList) {
            System.out.println(brands.getName());
        }

        //contoh Named Query dengan paramter
        TypedQuery<Brands> namedQuery1 = entityManager.createNamedQuery("Brands.findById", Brands.class);
        namedQuery1.setParameter("id","X1");
        List<Brands> resultList1 = namedQuery1.getResultList();
        for (Brands brands : resultList1) {
            System.out.println(brands.getName());
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void namedNativeQueryTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //contoh penggunaan native query menggunakan named native query.
        TypedQuery<Brands> query = entityManager.createNamedQuery("Brands.native.findAllOrderByName", Brands.class);
        List<Brands> resultList = query.getResultList();
        for (Brands brands : resultList) {
            System.out.println(brands.getId() + " : " + brands.getName() + " : " + brands.getCreatedAt());
        }

        entityTransaction.commit();
        entityManager.close();
    }
}
