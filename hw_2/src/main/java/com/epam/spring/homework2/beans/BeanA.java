package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanA extends BaseBean implements InitializingBean, DisposableBean {

	public BeanA() {
		System.out.println("Bean A no args constructor");
	}

	public BeanA(String name, int value) {
		super(name, value);
	}

	@Override
	public void afterPropertiesSet() {
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
