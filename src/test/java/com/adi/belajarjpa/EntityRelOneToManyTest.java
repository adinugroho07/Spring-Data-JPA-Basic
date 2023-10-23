package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.model.Products;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityRelOneToManyTest {

    @Test
    void insertDataToTable() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brands brands = new Brands();
        brands.setId("X1");
        brands.setName("Samsung");
        brands.setDescription("Merek Electronic Sultan");
        entityManager.persist(brands);

        Products products = new Products();
        products.setId("P1");
        products.setName("Galaxy S23 Ultra");
        products.setDescription("Hp Mantep banget dah");
        products.setPrice(15_500_000L);
        products.setBrands(brands);
        entityManager.persist(products);

        Products products1 = new Products();

        products1.setId("P2");
        products1.setName("Galaxy Flip 4");
        products1.setDescription("Hp Mantep banget dah bisa di lipet cuy");
        products1.setPrice(12_000_000L);
        products1.setBrands(brands);
        entityManager.persist(products1);

        Products products2 = new Products();

        products2.setId("P3");
        products2.setName("Galaxy S24 Fold");
        products2.setDescription("Hp dengan layar lebar bisa di fold");
        products2.setPrice(23_500_000L);
        products2.setBrands(brands);
        entityManager.persist(products2);

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void testFindDataWithRelations() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Brands brands = new Brands();
        brands = entityManager.find(Brands.class,"X1");
        Assertions.assertNotNull(brands);
        Assertions.assertEquals(3, brands.getProducts().size());

        //untuk ngecek apakah relasi nya berjalan.
        brands.getProducts().forEach(products -> {
            System.out.println(products.getName());
            System.out.println(products.getPrice());
        });


        entityTransaction.commit();
        entityManager.close();
    }
}
