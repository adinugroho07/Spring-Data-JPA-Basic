package com.adi.belajarjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BelajarJpaApplication {

	/*
	* ORM adalah teknik mapping antara basis data relational (database) dengan object (Hasil class) pada bahasa pemrograman.
	* jadi ORM ini akan menampung data2 dari DB atau insert ke DB dengan di mapping kan data dari atau ke DB ke dalam class.
	* dengan ORM kita bisa melakukan manipulasi data di DB layaknya manipulasi object tanpa harus menulis SQL nya secara langsung.
	*
	* Jakarta Persistence API (JPA) adalah salah satu standarisasi untuk Object Relational Mapping (ORM) di java. jadi
	* JPA ini adalah salah satu yang mengimplementasikan Framework ORM di java.
	*
	* Diagram JPA : kode java kita --> JPA --> JPA Provider --> JDBC --> JDBC Driver --> DB
	* - kode java akan memanggil fitur JPA nya
	* - JPA ini merupakan spesifikasi untuk ORM di java, oleh karena itu dia membutuhkan provider (JPA Provider)
	*   atau implementasi nya
	* - dari JPA Provider tersebut akan mentranslate ke JDBC dan akan memilih JDBC Driver.
	*
	* sudah di jelaskan di atas bahwa JPA merupakan spesifikasi untuk ORM di java, jadi kita perlu
	* menggunakan implementasi nya atau JPA Provider. ada banyak sekali JPA Provider yang bisa kita pilih saat ini
	* - Eclipse Link
	* - OpenJPA
	* - Hibernate ORM
	* namun karena Hibernate ORM sangat populer akhirnya Hibernate ORM ini di tarik dan di masukkan menjadi fitur
	* di JPA.
	*
	* */


	public static void main(String[] args) {
		SpringApplication.run(BelajarJpaApplication.class, args);
	}

}
