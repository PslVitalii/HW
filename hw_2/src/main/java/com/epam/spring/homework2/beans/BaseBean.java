package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;

public abstract class BaseBean implements BeanNameAware {
	private String name;
	private int value;

	private String beanName;

	public BaseBean() {
	}

	public BaseBean(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getBeanName() {
		return beanName;
	}

	protected void init() {
		System.out.println(beanName + " custom init method");
	}

	protected void destroy() {
		System.out.println(beanName + " custom destroy method");
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public String toString() {
		return String.format("%s{name : %s, value : %d}", beanName, name, value);
	}
}
