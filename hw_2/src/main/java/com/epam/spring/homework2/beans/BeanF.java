package com.epam.spring.homework2.beans;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BeanF extends BaseBean {

	public BeanF() {
		System.out.println("Bean F no args constructor");
	}

	public BeanF(String name, int value) {
		super(name, value);
	}
}
