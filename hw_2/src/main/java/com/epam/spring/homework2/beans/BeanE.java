package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanE extends BaseBean implements BeanNameAware {
    private String beanName;

    public BeanE(){
        System.out.println("Bean E constructor");
    }

    public BeanE(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @PostConstruct
    public void init(){
        System.out.println("Bean E postConstruct method");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Bean E preDestroy method");
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
