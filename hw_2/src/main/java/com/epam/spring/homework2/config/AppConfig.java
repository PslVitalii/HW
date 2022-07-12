package com.epam.spring.homework2.config;

import com.epam.spring.homework2.validation.BeansValidator;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.epam.spring.homework2")
@Import(BeansConfig.class)
public class AppConfig {

	@Bean
	public BeansValidator beanValidator() {
		return new BeansValidator();
	}
}
