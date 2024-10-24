package com.epam.gym.atlass_gym;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtlassGymApplication {
    @PersistenceContext
    EntityManager entityManager;

    public static void main(String[] args) {
        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();

        SpringApplication.run(AtlassGymApplication.class, args);

        //entityManager.close();
        //entityManagerFactory.close();
    }
}
