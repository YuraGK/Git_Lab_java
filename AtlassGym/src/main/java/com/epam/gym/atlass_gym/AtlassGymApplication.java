package com.epam.gym.atlass_gym;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
@EnableFeignClients
public class AtlassGymApplication extends SpringBootServletInitializer {


    @PersistenceContext
    static
    EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        SpringApplication.run(AtlassGymApplication.class, args);

        entityManager.close();
        entityManagerFactory.close();
    }
}
