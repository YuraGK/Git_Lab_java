package com.epam.gym.atlass_gym.config;


import com.epam.gym.atlass_gym.dao.TraineeDAO;
import com.epam.gym.atlass_gym.dao.TrainerDAO;
import com.epam.gym.atlass_gym.dao.TrainingDAO;
import com.epam.gym.atlass_gym.filter.LoggingFilter;
import com.epam.gym.atlass_gym.repository.TraineeRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainerRepositoryImpl;
import com.epam.gym.atlass_gym.repository.TrainingRepositoryImpl;
import com.epam.gym.atlass_gym.service.DataManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.gym.atlass_gym"})
public class SingletonConfig {

    @Bean
    public TrainerDAO trainerDAO() {
        return new TrainerDAO();
    }

    @Bean
    public TraineeDAO traineeDAO() {
        return new TraineeDAO();
    }

    @Bean
    public TrainingDAO trainingDAO() {
        return new TrainingDAO();
    }

    @Bean
    public DataManager dataManager() {
        return new DataManager();
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("default");
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public TraineeRepositoryImpl traineeRepository() {
        return new TraineeRepositoryImpl(entityManager());
    }

    @Bean
    public TrainerRepositoryImpl trainerRepository() {
        return new TrainerRepositoryImpl(entityManager());
    }

    @Bean
    public TrainingRepositoryImpl trainingRepository() {
        return new TrainingRepositoryImpl(entityManager());
    }

}
