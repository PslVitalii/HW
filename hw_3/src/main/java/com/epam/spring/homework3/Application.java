package com.epam.spring.homework3;

import com.epam.spring.homework3.model.entity.Role;
import com.epam.spring.homework3.model.entity.User;
import com.epam.spring.homework3.persistence.UserRepository;
import com.epam.spring.homework3.utils.InitializationUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {

    @Value("${security.admin.email}")
    private String adminEmail;

    @Value("${security.admin.password}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    protected CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return args -> {
            InitializationUtils.saveAdmin(adminEmail, adminPassword, passwordEncoder, userRepository);
        };
    }
}
