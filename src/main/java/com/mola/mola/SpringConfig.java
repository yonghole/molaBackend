package com.mola.mola;

import com.mola.mola.repository.JpaPointRecordRepository;
import com.mola.mola.repository.JpaUserRepository;
import com.mola.mola.repository.UserRepository;
import com.mola.mola.repository.PointRecordRepository;
import com.mola.mola.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.awt.*;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService memberService(){
        return new UserService(userRepository(),pointRecordRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new JpaUserRepository(em);
    }

    @Bean
    public PointRecordRepository pointRecordRepository() {return new JpaPointRecordRepository(); }
}
