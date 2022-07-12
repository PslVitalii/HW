package com.epam.spring.homework2.beans;

public class BeanB extends BaseBean {

	public BeanB() {
		System.out.println("Bean B no args constructor");
	}

	public BeanB(String name, int value) {
		super(name, value);
	}

	public void otherInit() {
		System.out.println(super.getBeanName() + " init method set by CustomBeanFactoryPostProcessor");
	}
}
