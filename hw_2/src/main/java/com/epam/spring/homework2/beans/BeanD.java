package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public class BeanD extends BaseBean implements BeanNameAware {
    private String beanName;

    public BeanD(){
        System.out.println("Bean D no args constructor");
    }

    public BeanD(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void init(){
        System.out.println("Bean D custom init method");
    }

    public void destroy(){
        System.out.println("Bean D custom destroy method");
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
