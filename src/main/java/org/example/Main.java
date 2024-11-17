package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Book;
import org.example.entities.ElectronicDevice;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String puName = "pu-name";
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","create");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName),props);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Book b = new Book();
            b.setId(1);
            b.setAuthor("John Doe");

            ElectronicDevice e = new ElectronicDevice();
            e.setId(2);
            e.setVoltage(220);

            em.persist(b);
            em.persist(e);

            var sql = "SELECT p FROM Product p";
            em.createQuery(sql, Product.class)
                            .getResultList().forEach(System.out::println);

            em.getTransaction().commit();
        }finally {
            em.close();
        }

    }
}