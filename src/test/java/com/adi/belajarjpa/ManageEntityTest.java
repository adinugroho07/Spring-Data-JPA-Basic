package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ManageEntityTest {

    @Test
    void manageEntityTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        /*
        * life cycle manage entity ini hanya sebatas transaksi saja, artinya jika transaksi sudah di commit dan di close
        * maka entity akan kembali menjadi unmanage entity. untuk membuat menjadi manage entity lagi maka perlu di
        * set up ulang entity manager dan entity transaction.
        * */

        //ketika kita define object di bawah ini, ini akan di anggap sebagai Unmanage Entity.
        //unmanage entity ini tidak akan di store ke DB.
        Brands brands = new Brands();
        brands.setId("X3");
        brands.setName("Xiaomi");
        brands.setDescription("Product Midle yang cukup baik");
        brands.setCreatedAt(LocalDateTime.now());
        brands.setUpdatedAt(LocalDateTime.now());

        //karena di atas hanya set up value dari attribute object yang Unmanage entity, maka jika kita run tidak akan di
        //store ke db.

        //ketika kita lakukan perist,merge,find maka dia akan berubah menjadi manage entity.
        entityManager.persist(brands);

        //ketika sudah menjadi manager entity, apa pun perubahan yang ada akan di update biar pun kita secara manual
        //melakukan merge via entity Manager.

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void manageEntityTestFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        //find pun termasuk ke dalam manage entity, jadi jika ada perubahan data pada entity setelah find akan di update
        //juga ke db
        Brands brands = entityManager.find(Brands.class,"X3");

        //tanpa kita melakukan merger entity, dia akan tetap ke update data description ini karena find di atas termasuk
        //ke dalam manage entity.
        brands.setDescription("Product Midle yang cukup baik dan massive");

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void manageEntityTestDetach() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        /*
        * kadang kita ingin membuat yang tadinya manager entity menjadi unmanage entity, cara nya kita bisa menggunakan
        * EntityManager.detach(). jika sudah menjadi unmanage entity ini secara otomatis perubahan yang terjadi di entity
        * tidak akan di simpan pada saat melakukan commit.
        * */

        //manage entity
        Brands brands = entityManager.find(Brands.class,"X3");

        //merubah manage entity menjadi unmanage entity, sehingga tidak akan melakukan update data.
        entityManager.detach(brands);
        brands.setDescription("Product Midle yang cukup baik dan massive");

        entityTransaction.commit();
        entityManager.close();
    }
}
