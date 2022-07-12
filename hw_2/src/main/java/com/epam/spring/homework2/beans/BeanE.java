package com.epam.spring.homework2.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanE extends BaseBean {
	private String beanName;

	public BeanE() {
		System.out.println("Bean E constructor");
	}

	public BeanE(String name, int value) {
		super(name, value);
	}

	@PostConstruct
	public void init() {
		super.init();
	}

	@PreDestroy
	public void destroy() {
		super.init();
	}
}
