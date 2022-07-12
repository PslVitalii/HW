package com.epam.spring.homework2;

import com.epam.spring.homework2.beans.BaseBean;
import com.epam.spring.homework2.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		System.out.println("\nTask related beans");
		Arrays.stream(context.getBeanDefinitionNames())
				.map(context::getBean)
				.filter(BaseBean.class::isInstance)
				.forEach(System.out::println);

		System.out.println("\nBeans configurations");
		Arrays.stream(context.getBeanDefinitionNames())
				.map(context::getBeanDefinition)
				.forEach(System.out::println);

		System.out.println("\nClosing context");
		context.close();
	}
}
