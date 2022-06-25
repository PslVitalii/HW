package com.epam.spring.homework2.beans;

import com.epam.spring.homework2.validation.Validator;

public abstract class BaseBean implements Validator {
    private String name;
    private int value;

    public BaseBean(){}

    public BaseBean(String name, int value){
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean validate(){
        return this.name != null && this.value >= 0;
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

    @Override
    public String toString() {
        return String.format("{name : %s, value : %d}", name, value);
    }
}
