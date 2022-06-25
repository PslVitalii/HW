package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanA extends BaseBean implements BeanNameAware, InitializingBean, DisposableBean {
    private String beanName;

    public BeanA(){
        System.out.println("Bean A no args constructor");
    }

    public BeanA(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Bean A initializingBean afterPropertiesSet method");
    }

    @Override
    public void destroy() {
        System.out.println("Bean A disposableBean destroy method");
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
