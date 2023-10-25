package com.adi.belajarjpa;

import com.adi.belajarjpa.model.Brands;
import com.adi.belajarjpa.model.JPAHelper.AggregateProducts;
import com.adi.belajarjpa.model.JPAHelper.SimpleBrands;
import com.adi.belajarjpa.model.Members;
import com.adi.belajarjpa.model.Products;
import com.adi.belajarjpa.model.Users;
import com.adi.belajarjpa.util.JpaUtil;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JPAQueryTest {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    /*
    * JPA QL (Query language)
    * JPA juga memiliki Query Language atau yang biasa di sebut sebagai JPA QL. JPA QL ini mempunyai standarisasi
    * Query language, jadi ga ada spesific syntaks ke database apa karena memang JPA di set untuk bisa connect dan
    * berganti2 database. namun JPA QL ini syntaksnya hampir sama dengan SQL biasa.
    *
    * setiap kali kita create Query dengan JPA QL, maka dia return type nya adalah object class Query, namun jika kita
    * sudah mengetahui Entity yang kita select apa maka kita bisa menggunakan class TypedQuery<T>. kalo yang object
    * class Query itu dia mesti di konversi ke tipe data atau ke entity secara manual.
    *
    * */

    @Test
    void selectTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * dalam syntaks JPA QL, untuk melakukan select kita tidak menyebutkan nama table di db nya, melainkan nama Entity nya.
        * selain itu ketika kita ingi melakukan select semua kolom, kita tidak menggunakan bintang (*), melainkan menggunakan
        * nama alias dari entity nya.
        * */

        //contoh pengaksesan Entity menggunakan JPA QL.
        TypedQuery<Brands> query = entityManager.createQuery("select b from Brands b", Brands.class);
        //alias entity Brands adalah b, maka select all nya tidak menggunakan *, melainkan menggunakan alias entity nya, jadi select b
        List<Brands> brands = query.getResultList();//menampung list data dengan Object Brands pake collection.
        brands.forEach(brands1 -> {
            System.out.println(brands1.getName());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void selectWhereTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * saat kita menggunakan where clause, kita bisa menggunakan nama attribute di entity nya, bukan nama
        * kolom di table dan jika attribute di entity nya berupa emmbedded class, kita bisa menyebut kan object di dalam
        * atau nama attribute yang merupakan object yang di embedded tersebut dan di sambung dengan menggunakan
        * tanda titik (.), contoh -> m.name.lastName, object embedded nya adalah name dan kita mengakses attribute
        *                            lastName dari object embedded Name.
        *
        * ketika kita membutuhkan parameter pada where clause, kita bisa menggunakan tanda titik 2 (:) di ikuti dengan
        * nama parameter, contoh :firstName , :lastName , :id .
        * nanti untuk mengisikan value di parameternya kita menggunakan fungsi setParameter("keyparameter","valueparameter");
        * yang merupakan bagian dari Object Query atau TypedQuery.
        * contoh : query.setParameter("firstName","Adi"); -> nama parameter nya adalah firstName dan value nya adalah Adi
        * */

        //contoh pengaksesan Entity menggunakan JPA QL with where clause.
        TypedQuery<Members> query = entityManager.createQuery("select m from Members m where m.name.firstName = :firstName" +
                " and m.name.lastName = :lastName and m.id = :id", Members.class);

        //set up parameter where clause query
        query.setParameter("firstName","Adi");
        query.setParameter("lastName","Nugroho");
        query.setParameter("id",3);
        //query di atas beserta where nya sama seperti syntaks query berikut
        //select * form members where firstName='Adi' and lastName = 'Nugroho' and id = 3
        List<Members> members = query.getResultList();
        members.forEach(members1 -> {
            System.out.println(members1.getName().getFirstName()+" "+members1.getName().getLastName());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void selectJoinTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * di JPA QL ini juga kita bisa melakukan Join Query. karena di Entity Class nya sudah ada informasi Join nya
        * maka kita tinggal gunakan perintah join di ikuti dengan attribute join di Entity Class nya.
        * contoh : Entity Users : select p from Users p join p.wallet b -> table Users di join ke table wallet.
        * namun dengan menggunakan join ini sebetulnya di blakang layar JPA akan melakukan 2 kali query, pertama query
        * ke table Users, kedua dia akan query ke table Wallet berdasarkan id Users yang di cari.
        *
        * kita juga bisa menambahkan fetch di query nya untuk membuat JPA melakukan 1 kali query ketika join, jadi tidak
        * 2 query seperti sebelum nya. caranya tingal menambahkan fetch setelah kata join.
        * contoh : select u from Users u join fetch u.likes p
        * */

        //contoh query Join di JPA
        TypedQuery<Products> query = entityManager.createQuery("select p from Products p join p.brands b"
                + " where b.name = :brandname ", Products.class);
        query.setParameter("brandname","Samsung");
        List<Products> products = query.getResultList();
        products.forEach(products1 -> {
            System.out.println(products1.getName());
        });

        //contoh query Join with fetch di JPA
        TypedQuery<Users> query1 = entityManager.createQuery("select u from Users u join fetch u.likes p"
                + " where p.name = :product", Users.class);
        query1.setParameter("product","Galaxy S23 Ultra");
        List<Users> users = query1.getResultList();
        for (Users data: users) {
            System.out.println("User : "+data.getName());
            for (Products prd: data.getLikes()) {
                System.out.println("Product : " + prd.getName());
            }
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void orderByAndLimitOffsetTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //contoh penggunaan order by
        TypedQuery<Brands> query = entityManager.createQuery("select b from Brands b order by b.name desc", Brands.class);
        List<Brands> brands = query.getResultList();
        brands.forEach(brands1 -> {
            System.out.println(brands1.getName());
        });

        TypedQuery<Brands> query1 = entityManager.createQuery("select b from Brands b order by b.name desc", Brands.class);
        //untuk mengatur offset atau data yang di skip. jika di set 5 maka data 1 - 5 akan di skip dan di mulai dari data 6
        query1.setFirstResult(5);
        //untuk mengatur limit data yang bisa di select
        query1.setMaxResults(5);
        List<Brands> brands1 = query1.getResultList();
        brands1.forEach(brands2 -> {
            System.out.println(brands2.getName());
        });

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void selectBeberapaKolomTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * kadang kita membutuhkan untuk melakukan select hanya beberapa kolom saja. di JPA kita juga bisa melakukan ini.
        * ada beberapa cara :
        * 1. ganti mapping Object nya yang tadi nya adalah class Entity ke Object[]. namun karena hasilnya adalah Object[]
        *    maka kita perlu konversi ke tipe data manual
        * 2. Menggunakan Constructor Expression dari sebuah class pembantu yang attribute nya merupakan field2 yang akan
        *    kita select. ini lebih aman karena secara data sudah di conversikan oleh class pembantu tersebut, namun
        *    agak repot dalam pembuatan Class pembantunya itu. jadi schema nya adalah kita select beberapa kolom dari
        *    dari sebuah Entity, setelah itu kita select class pembantu tersebut dan masukan kolom2 yang kita select itu
        *    ke constructor class pembantu tersebut untuk di conversikan tipe datanya, setelah itu kita bisa mengakses
        *    data list nya dari object list class pembantu tersebut, jadi akan lebih aman.
        *
        * untuk yang langkah 2, di dalam query select nya kita select nya ke class pembantu tersebut dengan cara
        * select new nama.package.Class(field,field,,,) seperti contoh di bawah ini.
        * select new com.adi.belajarjpa.model.JPAHelper.SimpleBrands(b.id, b.name, b.description)
        * */

        //contoh select beberapa kolom dengan menggunakan cara Object[].
        TypedQuery<Object[]> query = entityManager.createQuery("select b.id, b.name from Brands b where b.name = :name"
                ,Object[].class);
        query.setParameter("name","Razer");
        List<Object[]> objects = query.getResultList();
        //cara mengakses data nya, object[0] = data kolom b.id, object[1] = data kolom b.name
        for (Object[] object : objects) {
            //aksesnya adalah banyak kolom yang di select akan menjadi index element array nya.
            System.out.println(object[0] + " - " + object[1]);
        }

        //contoh select beberapa kolom dengan menggunakan cara Constructor Expression.
        TypedQuery<SimpleBrands> query1 = entityManager.
                createQuery("select new com.adi.belajarjpa.model.JPAHelper.SimpleBrands(b.id, b.name, b.description) " +
                        "from Brands b where b.name = :name" ,SimpleBrands.class);
        //SimpleBrands adalah class pembantu, jadi nanti JPA akan menjadi class tersebut sebagai Entity sementara untuk
        //menampung select beberapa kolom tersebut.
        query1.setParameter("name","Samsung");
        List<SimpleBrands> resultList = query1.getResultList();
        for (SimpleBrands simpleBrands : resultList) {
            System.out.println(simpleBrands.getId()+ " - " +simpleBrands.getName() + " - " + simpleBrands.getDescription());
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void aggregateQueryTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * JPA juga mendukung aggregate query (min(),max(),avg(),dll), namun untuk mengecek aggregate function yang di
        * dukung oleh JPA kita bisa melihat di link di bawah ini :
        * https://docs.jboss.org/hibernate/orm/6.2/userguide/html_single/Hibernate_User_Guide.html#hql-aggregate-functions
        *
        * dalam menggunakan aggregate function ini juga terdapat 2 cara :
        * 1. dengan menggunakan Object[]. minus nya adalah karena dia tipe datanya object maka perlu di conversikan.
        * 2. dengan menggunakan class Helper atau Constructor Expression. minus nya adalah harus membuat class helper nya.
        *
        * untuk step2nya juga sama seperti kita melakukan select beberapa kolom.
        *
        * */

        //contoh aggregate function menggunakan cara Object[]
        TypedQuery<Object[]> query = entityManager.
                createQuery("select min(p.price), max(p.price), avg(p.price) from Products p", Object[].class);
        List<Object[]> resultList = query.getResultList();
        for (Object[] objects : resultList) {
            System.out.println("min = " + objects[0]);
            System.out.println("max = " + objects[1]);
            System.out.println("avg = " + objects[2]);
        }

        //contoh aggregate function menggunakan cara class Helper atau Constructor Expression
        TypedQuery<AggregateProducts> query1 = entityManager.
                createQuery("select new com.adi.belajarjpa.model.JPAHelper.AggregateProducts(min(p.price),max(p.price),avg(p.price)) "
                        + " from Products p", AggregateProducts.class);
        List<AggregateProducts> resultList1 = query1.getResultList();
        for (AggregateProducts aggregateProducts : resultList1) {
            System.out.println("With class Helper atau Constructor Expression");
            System.out.println("min = " + aggregateProducts.getMin());
            System.out.println("max = " + aggregateProducts.getMax());
            System.out.println("avg = " + aggregateProducts.getAvg());
        }

        //Using Group By dan Having
        TypedQuery<Object[]> query2 = entityManager.createQuery("select b.id, min(p.price) , max(p.price), "
                + "avg(p.price) from Products p join p.brands b group by b.id having min(p.price) > :min", Object[].class);
        query2.setParameter("min",500_000L);
        List<Object[]> resultList2 = query2.getResultList();
        for (Object[] objects : resultList2) {
            System.out.println("With Having And Group By");
            System.out.println("Brand = " + objects[0]);
            System.out.println("min = " + objects[1]);
            System.out.println("max = " + objects[2]);
            System.out.println("avg = " + objects[3]);
        }
        
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void nativeQueryTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * kita juga bisa membuat query yang langsung spesifik menggunakan SQL database.
        * namun hal ini tidak di sarankan di karena kan bisa membuat kita kesulitan ketika akan mengubah database yang
        * sedang di gunakan.
        *
        * native query ini bisa kita create menggunakan method EntityManager.createNativeQuery(query, class)
        * */

        //contoh penggunaan native query di JPA
        Query nativeQuery = entityManager.createNativeQuery(
                "select * from brands where brands.created_at is not null order by brands.name asc"
                , Brands.class);
        List<Brands> resultList = nativeQuery.getResultList();
        for (Brands brands : resultList) {
            System.out.println(brands.getId() + " : " + brands.getName() + " : " + brands.getCreatedAt());
        }

        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    void updateViaJPAQL() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        /*
        * di JPA QL kita juga bisa membuat perintah update atau delete (ga lewat entity remove atau merge) fitur ini
        * di sebut sebagai Non Query. contoh nya ada di bawah.
        *
        * update atau delete data dengan menggunakan Non Query ini tidak di saran kan, karena fitur Optimistic Locking
        * nya tidak akan berjalan karena kolom version nya tidak akan berubah ketika kita update.
        * */

        Query query = entityManager.createQuery("update Brands b set b.name = :name where b.id = :id ");
        query.setParameter("name","BenQ");
        query.setParameter("id","X9");
        int impactedRecords = query.executeUpdate();
        System.out.println("Success update " + impactedRecords + " records");

        entityTransaction.commit();
        entityManager.close();
    }
}
