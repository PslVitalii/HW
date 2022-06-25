package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

public class BeanC extends BaseBean implements BeanNameAware {
    private String beanName;

    public BeanC(){
        System.out.println("Bean C no args constructor");
    }

    public BeanC(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void init(){
        System.out.println("Bean C custom init method");
    }

    public void destroy(){
        System.out.println("Bean C custom destroy method");
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
