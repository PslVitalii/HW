package com.epam.spring.homework1.pet;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Cheetah implements Animal{
    private String type;

    public Cheetah(){
        this.type = "@Component";
    }

    public Cheetah(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + type;
    }
}
