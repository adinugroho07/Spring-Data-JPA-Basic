package com.adi.belajarjpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    /*
    * saat menggunakan JPA, hal pertama yang harus kita buat adalah EntityManagerFactory. ini seperti datasource
    * dimana kita hanya membuat 1 kali saja dalam satu aplikasi. kenapa hanya di buat 1 kali saja karena EntityManagerFactory
    * merupakan object yang sangat berat, karena perlu membaca Entity class dan fitur2 lain nya saat pertama kali di buat.
    *
    * kita juga perlu membuat file config JPA yang di store di folder Resources/META-INF dengan nama file persistence.xml
    * dimana nanti config JPA di dalam file ini akan di baca oleh EntityManagerFactory.
    * */


    private static EntityManagerFactory entityManagerFactory = null;

    public static EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null){
            //pembuatan entity manager factory secara singleton
            //jika entityManagerFactory tidak null maka object yang sama yang akan di gunakan.
            //jika entityManagerFactory adalah null maka akan create ulang dengan nama persistence unit belajarjpa.
            entityManagerFactory = Persistence.createEntityManagerFactory("belajarjpa");
            //code di atas akan manggil config JPA dengan nama persistence unit belajarjpa. config nya ada di file persistence.xml.
        }
        return entityManagerFactory;
    }
}
