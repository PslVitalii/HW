package com.epam.spring.homework1.pet;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class Spider implements Animal{

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
