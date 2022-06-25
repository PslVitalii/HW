package com.epam.spring.homework2.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.epam.spring.homework2")
@Import(BeansConfig.class)
public class AppConfig {

}
