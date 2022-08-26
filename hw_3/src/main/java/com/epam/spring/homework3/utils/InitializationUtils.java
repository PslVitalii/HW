package com.epam.spring.homework3.utils;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.spring.homework3.model.entity.Role;
import com.epam.spring.homework3.model.entity.User;
import com.epam.spring.homework3.persistence.UserRepository;

public class InitializationUtils{

    public static void saveAdmin(
        String email,
        String password,
        PasswordEncoder passwordEncoder,
        UserRepository userRepository
    ) {
        User admin = new User();
        admin.setEmail(email);
        admin.setRole(Role.ADMIN);
        admin.setEnable(true);

        String encodedPassword = passwordEncoder.encode(password);
        admin.setPassword(encodedPassword);

        Optional<User> possibleUser = userRepository.findByEmail(admin.getEmail());
        if (possibleUser.isEmpty()) {
            userRepository.save(admin);
        }
    }
}