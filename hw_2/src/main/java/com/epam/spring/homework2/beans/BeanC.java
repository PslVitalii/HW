package com.epam.spring.homework2.beans;

public class BeanC extends BaseBean {

	public BeanC() {
		System.out.println("Bean C no args constructor");
	}

	public BeanC(String name, int value) {
		super(name, value);
	}
}
