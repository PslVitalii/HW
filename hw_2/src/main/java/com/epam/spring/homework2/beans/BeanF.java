package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BeanF extends BaseBean implements BeanNameAware {
    private String beanName;

    public BeanF(){
        System.out.println("Bean F no args constructor");
    }

    public BeanF(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
