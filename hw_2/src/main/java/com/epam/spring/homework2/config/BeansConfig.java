package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class BeansConfig {

	@Bean(initMethod = "init", destroyMethod = "destroy")
	BeanD beanD(@Value("${beanD.name}") String name, @Value("${beanD.value}") int value) {
		return new BeanD(name, value);
	}

	@Bean(initMethod = "init", destroyMethod = "destroy")
	@DependsOn("beanD")
	BeanB beanB(@Value("${beanB.name}") String name, @Value("${beanB.value}") int value) {
		return new BeanB(name, value);
	}

	@Bean(initMethod = "init", destroyMethod = "destroy")
	@DependsOn("beanB")
	BeanC beanC(@Value("${beanC.name}") String name, @Value("${beanC.value}") int value) {
		return new BeanC(name, value);
	}
}
