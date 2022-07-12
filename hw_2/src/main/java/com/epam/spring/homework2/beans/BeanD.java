package com.epam.spring.homework2.beans;

public class BeanD extends BaseBean {

	public BeanD() {
		System.out.println("Bean D no args constructor");
	}

	public BeanD(String name, int value) {
		super(name, value);
	}
}
