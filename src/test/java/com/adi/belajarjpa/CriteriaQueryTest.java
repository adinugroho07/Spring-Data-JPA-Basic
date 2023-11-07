package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.model.JPAHelper.SimpleBrands;
import com.adi.belajarjpa.model.Products;
import com.adi.belajarjpa.util.JpaUtil;
import com.adi.belajarjpa.util.RupiahCurrency;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CriteriaQueryTest {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    @Test
    void criteriaTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * di JPA kita bisa membuat JPA QL secara dinamis. maksudnya adalah saat kita akan membuat query di JPA maka
        * kita harus memlilih dulu query nya apakah menggunakan Native Query atau JPA QL, nah JPA sediakan fitur
        * khusus di tengah2 ini sehingga bisa dinamis. jadi di dalam fitur ini kita tidak menyebutkan query mana yang
        * akan di gunakan namun kita hanya menyebutkan ambil data dari table apa. fitur ini nama nya adalah criteria
        *
        * untuk membuat criteria ini kita perlu membuat criteria builder dengan menggunakan entity manager.
        * setelah itu kita harus membuat criteria query dimana ini berguna untuk menambahkan
        * informasi query yang akan kita lakukan , seperti select dari entity mana, field apa yang akan
        * diambil dan kondisi where apa yang akan digunakan. setelah itu kita bisa gunakan
        * EntityManager.createQuery(criteria) untuk mengkonversi menjadi Query hasilnya.
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();//initiate criteria.

        //untuk menentukan query nya seperti apa. jadi ini akan membuat query ke table brands
        CriteriaQuery<Brands> criteria = builder.createQuery(Brands.class);
        Root<Brands> root = criteria.from(Brands.class);//untuk menentukan data yang di ambil dari table mana.
        //ini sama seperti select * from brands. jadi seperti aksi si criteria ini dan ini kita select ke entity
        criteria.select(root);
        //jadi query nya sama seperti select b from Brands b jika di JPA QL.

        TypedQuery<Brands> query = entityManager.createQuery(criteria);
        List<Brands> resultList = query.getResultList();
        for (Brands brands : resultList) {
            System.out.println(brands.getName());
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaNonEntityTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();//initiate criteria.
        // untuk menentukan query nya seperti apa. karena kita hanya melakukan select beberapa kolom
        // maka nya menggunakan Object[]. untuk mengakses data2 kolom nya tinggal menggunakan index element nya.
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Brands> root = criteria.from(Brands.class);//select nya dari table Brands.
        //select kolom2 yang kita ingin select jadi ga select semua kolom.
        criteria.select(builder.array(root.get("id"),root.get("name"),root.get("description")));
        //ini sama seperti query select b.id, b.name, b.description from Brands b


        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> resultList = query.getResultList();
        resultList.forEach(objects -> {
            System.out.println("Id : " + objects[0]);
            System.out.println("Name :" + objects[1]);
            System.out.println("Description :" + objects[2]);
        });

        System.out.println("===============================");

        //yang menggunakan constructor expression, jadi sama seperti di JPA QL kita menggunakan class helper
        //yang non entity untuk mengconversikan data hasil select kita, jadi akses data kolom nya ga pake Object[]
        //yang di akses dari index element nya.
        CriteriaQuery<SimpleBrands> criteriaNew = builder.createQuery(SimpleBrands.class);
        Root<Brands> rootNew = criteriaNew.from(Brands.class);//select nya dari table Brands.
        //select kolom2 nya nanti akan di mapping kan ke class SimpleBrands, padahal kita state di awal select nya dari
        //table Brands.
        criteriaNew.select(
                builder.construct(
                        SimpleBrands.class,
                        rootNew.get("id"),
                        rootNew.get("name"),
                        rootNew.get("description"))
        );
        //ini sama seperti query select b.id, b.name, b.description from Brands b

        TypedQuery<SimpleBrands> query1 = entityManager.createQuery(criteriaNew);
        //data hasil query nya akan di conversikan ke class SimpleBrands lewat constructor class itu.
        List<SimpleBrands> resultList1 = query1.getResultList();
        resultList1.forEach(objects -> {
            System.out.println("Id : " + objects.getId());
            System.out.println("Name :" + objects.getName());
            System.out.println("Description :" + objects.getDescription());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaQueryWithWhereClauseTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * di dalam criteria query builder ini kita juga pasti bisa menggunakan where clause.
        * untuk menambahkan kondisi seperti =,!=/<>,dll kita bisa menggunakan criteria builder yang sudah kita buat.
        *
        * di dalam query where ini ketika kita tidak perlu men state criteria.select(root) karena ketika kita sudah
        * menggunakan where clause artinya kita akan melakukan select * .
        *
        * di dalam where clause nya , secara default kondisi yang kita sebut kan menggunakan criteria builder
        * adalah AND, jadi jika kita ingin menggunakan OR maka perlu kita definisikan.
        *
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();//initiate criteria.
        // untuk menentukan query nya seperti apa.
        CriteriaQuery<Brands> criteria = builder.createQuery(Brands.class);
        Root<Brands> root = criteria.from(Brands.class);//select nya dari table Brands.
        criteria.select(root);
        //where clause nya dan cara penggunaannya.
        criteria.where(
                //melakukan penkondisian dalam where clause.
                builder.equal(root.get("name"), "Razer"),
                builder.isNotNull(root.get("createdAt"))

                //kode di bawah ini sama dengan yang di atas karena secara default untuk operasi AND kita tidak
                //perlu menstate hal tersebut.
//                builder.and(
//                        builder.equal(root.get("name"), "Razer"),
//                        builder.isNotNull(root.get("createdAt"))
//                )
        );
        //ini sama seperti query select * from Brands b where b.name = 'Razer' And created_at is not nul;


        TypedQuery<Brands> query = entityManager.createQuery(criteria);
        List<Brands> resultList = query.getResultList();
        resultList.forEach(objects -> {
            System.out.println("Id : " + objects.getId());
            System.out.println("Name :" + objects.getName());
            System.out.println("Description :" + objects.getDescription());
        });

        System.out.println("=============OR================");

        criteria.select(root);
        //contoh penggunaan operasi OR
        criteria.where(
          builder.or(
                  builder.equal(root.get("name"), "Razer"),
                  builder.equal(root.get("name"), "Samsung")
          )
        );
        //ini sama seperti query select * from Brands b where b.name = 'Razer' OR b.name = 'Samsung';

        TypedQuery<Brands> query1 = entityManager.createQuery(criteria);
        List<Brands> resultList1 = query1.getResultList();
        resultList1.forEach(objects -> {
            System.out.println("Id : " + objects.getId());
            System.out.println("Name :" + objects.getName());
            System.out.println("Description :" + objects.getDescription());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void combinedWhereAndSelectCriteriaTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();//initiate criteria.
        // untuk menentukan query nya seperti apa.
        CriteriaQuery<SimpleBrands> criterianew = builder.createQuery(SimpleBrands.class);
        Root<Brands> root = criterianew.from(Brands.class);
        //set select dan where clause nya.
        criterianew.select(builder.construct(
                SimpleBrands.class,
                root.get("id"),
                root.get("name"),
                root.get("description")
                )
        ).where(
                builder.isNotNull(root.get("createdAt")),
                builder.or(
                        builder.equal(root.get("name"), "Razer"),
                        builder.equal(root.get("name"), "Oppo")
                )
        );
        // ini sama seperti query berikut :
        // select b.id, b.name, b.description from Brands b where created_at is not nul AND (
        // b.name = 'Razer' OR b.name = 'Samsung' );

        TypedQuery<SimpleBrands> query2 = entityManager.createQuery(criterianew);
        //data hasil query nya akan di conversikan ke class SimpleBrands lewat constructor class itu.
        List<SimpleBrands> resultList2 = query2.getResultList();
        resultList2.forEach(objects -> {
            System.out.println("Id : " + objects.getId());
            System.out.println("Name :" + objects.getName());
            System.out.println("Description :" + objects.getDescription());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaJoinTableTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        /*
        * criteria join. hampir sama dengan entity join. tinggal menggunakan Root<T>.join("nama join nya")
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Products> criteria = builder.createQuery(Products.class);
        Root<Products> productsRoot = criteria.from(Products.class);
        //join table Products ke Brands
        Join<Products, Brands> brandRoot = productsRoot.join("brands");
        //nama join pada Entity Products adalah brands.

        criteria.select(productsRoot);
        criteria.where(
                builder.equal(brandRoot.get("name"),"Oppo")
        );
        //select p from Products p join Brands b on b.id = p.brand_id
        //where b.name = 'Oppo'

        TypedQuery<Products> query = entityManager.createQuery(criteria);
        List<Products> resultList = query.getResultList();
        for (Products products : resultList) {
            System.out.println("Merek : " + products.getBrands().getName());
            System.out.println("Name : " + products.getName());
            System.out.println("Price : " + products.getPrice());
            System.out.println("Descriptions : " + products.getDescription());
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void parameterCriteriaQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * di criteria ini kita juga bisa menggunakan paramter query seperti di JPA QL.
        * caranya adalah dengan menggunakan CriteriaBuilder.paramter(Object class type nya)
        *
        * nanti ketika di entity manager set parameter nya, key value untuk parameter nya adalah
        * variable dari class ParameterExpression<Object> yang sudah di definisikan.
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Products> criteria = builder.createQuery(Products.class);
        Root<Products> productsRoot = criteria.from(Products.class);
        //join table
        Join<Products, Brands> rootBrands = productsRoot.join("brands");

        //pembuatan parameter criteria string type
        ParameterExpression<String> nameParameter = builder.parameter(String.class);
        //pembuatan parameter criteria integer type.
        ParameterExpression<Long> priceParameter = builder.parameter(Long.class);

        //query nyaa sudah menggunakan parameter.
        criteria.select(productsRoot).where(
                builder.equal(rootBrands.get("name"), nameParameter),
                builder.greaterThanOrEqualTo(productsRoot.get("price"),priceParameter)
        );
        //SELECT p FROM Products p JOIN Brands b on b.id = p.brand_id
        //WHERE b.name = :nameParameter AND p.price >= :priceParameter

        TypedQuery<Products> query = entityManager.createQuery(criteria);
        //nama parameter di ambil dari parameter criteria yang sudah di buat tadi.
        query.setParameter(nameParameter,"Nokia");
        query.setParameter(priceParameter, 4_000_000L);

        List<Products> resultList = query.getResultList();
        for (Products products : resultList) {
            System.out.println("Merek : " + products.getBrands().getName());
            System.out.println("Name : " + products.getName());
            System.out.println("Price : " + products.getPrice());
            System.out.println("Descriptions : " + products.getDescription());
        }


        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void aggregateCriteriaQueryTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        RupiahCurrency rp = new RupiahCurrency();
        /*
        * di dalam criteria kita juga bisa menggunakan aggregate query seperti Group By, Having,
        * min(),max(),avg() dll.
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Products> productsRoot = criteria.from(Products.class);
        //join table
        Join<Products, Brands> rootBrands = productsRoot.join("brands");

        ParameterExpression<Long> priceParameter = builder.parameter(Long.class);

        //contoh penggunaan group by, having, min , max dan avg
        criteria.select(builder.array(
                rootBrands.get("id"),
                rootBrands.get("name"),
                builder.min(productsRoot.get("price")),
                builder.max(productsRoot.get("price")),
                builder.avg(productsRoot.get("price"))
                )
        ).groupBy(
                rootBrands.get("id"),
                rootBrands.get("name")
        ).having(
                builder.greaterThanOrEqualTo(builder.min(productsRoot.get("price")), priceParameter)
        );
        // SELECT b.id, b.name, min(p.price), max(p.price) , avg(p.price) FROM Products p
        // JOIN Brands b ON b.id = p.brand_id
        // GROUP BY b.id , b.name
        // HAVING min(p.price) > :priceParameter

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        query.setParameter(priceParameter, 500_000L);
        List<Object[]> resultList = query.getResultList();
        for (Object[] objects : resultList) {
            System.out.println("Id :" + objects[0]);
            System.out.println("Name :" + objects[1]);
            System.out.println("Min :" + rp.toRupiahFormat(Integer.valueOf(objects[2].toString())));
            System.out.println("Max :" + rp.toRupiahFormat(Integer.valueOf(objects[3].toString())));
            System.out.println("Avg :" + objects[4]);
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaUpdateNonQueryTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * criteria non query biasanya di gunakan untuk melakukan update atau delete.
        *
        * untuk membuat non query ini kita mesti melakukan hal berikut :
        * - untuk update kita bisa membuat perintah Update dengan menggunakan fungsi builder
        *   CriteriaBuilder.createCriteriaUpdate(class) dan return type nya adalah object CriteriaUpdate<T> .
        * - untuk delete kita bisa membuat perintah delete dengan menggunakan fungsi builder
        *   CriteriaBuilder.createCriteriaDelete(class) dan return type nya adalah object CriteriaDelete<T> .
        *
        * sama seperti hal nya di JPA QL, untuk lock nya jika menggunakan non query ini tidak akan mengupdate
        * version locking nya, jadi cara ini kurang di sarankan.
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //berbeda dengan yang biasanya kita membuat query, sekarang kita membuat query update dengan non query.
        CriteriaUpdate<Brands> criteria = builder.createCriteriaUpdate(Brands.class);
        Root<Brands> root = criteria.from(Brands.class);

        ParameterExpression<String> idParameter = builder.parameter(String.class);

        //set yang akan di update colom nya.
        criteria.set(root.get("name"),"Nokia Update");
        //set where clause nya.
        criteria.where(
                builder.equal(root.get("id"), idParameter)
        );
        //UPDATE Brands b SET b.name = 'Nokia Update' WHERE b.id = :idParameter

        Query query = entityManager.createQuery(criteria);
        query.setParameter(idParameter,"X8");
        int impactRecords = query.executeUpdate();
        System.out.println("Success Update " + impactRecords + " rows");

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void criteriaDeleteNonQueryTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * ini contoh untuk delete dengan criteria non query.
        * */

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //berbelete dengan neda dengan yang biasanya kita membuat query, sekarang kita membuat query don query.
        CriteriaDelete<Brands> criteria = builder.createCriteriaDelete(Brands.class);
        //menentukan table mana yang akan di delete data nya.
        Root<Brands> root = criteria.from(Brands.class);

        //set parameter
        ParameterExpression<String> idParameter = builder.parameter(String.class);

        //set where clause nya.
        criteria.where(
                builder.equal(root.get("id"), idParameter)
        );
        //DELETE FROM Brands b WHERE b.id = :idParameter

        Query query = entityManager.createQuery(criteria);
        query.setParameter(idParameter,"X12");
        int impactRecords = query.executeUpdate();
        System.out.println("Success Delete " + impactRecords + " rows");

        entityTransaction.commit();
        entityManager.close();
    }
}
