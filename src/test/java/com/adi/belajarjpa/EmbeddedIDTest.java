package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Department;
import com.adi.belajarjpa.model.DepartmentID;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmbeddedIDTest {

    @Test
    void insertEmbedIDTest() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentID departmentID = new DepartmentID();
        departmentID.setDepartmentId(1);
        departmentID.setCompanyId(3);

        Department department = new Department();
        department.setId(departmentID);
        department.setName("Teknologi Informasi");

        entityManager.persist(department);

        Department departmentFrmDB = entityManager.find(Department.class,departmentID);
        Assertions.assertEquals(department.getName(),departmentFrmDB.getName());

        entityTransaction.commit();
        entityManager.close();
    }
}
