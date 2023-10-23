package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Images;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LargeObjectTest {

    @Test
    void LOBTest() throws IOException {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Images images = new Images();
        images.setName("foto1");
        images.setDescription("Deskripsi image");

        //read file image
        Path path = new File(getClass().getResource("/images/Untitled.png").getFile()).toPath();
        byte[] bytes = Files.readAllBytes(path);

        images.setImage(bytes);

        entityManager.persist(images);

        entityTransaction.commit();
        entityManager.close();
    }
}
