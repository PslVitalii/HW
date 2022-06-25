package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.BeanNameAware;

public class BeanB extends BaseBean implements BeanNameAware {
    private String beanName;

    public BeanB(){
        System.out.println("Bean B no args constructor");
    }

    public BeanB(String name, int value){
        super(name, value);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void init(){
        System.out.println("Bean B custom init method");
    }

    public void otherInit(){
        System.out.println("Bean B init method set by CustomBeanFactoryPostProcessor");
    }

    public void destroy(){
        System.out.println("Bean B custom destroy method");
    }

    @Override
    public String toString() {
        return beanName + super.toString();
    }
}
