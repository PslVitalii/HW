package com.epam.spring.homework1.pet;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pet {
    private final List<Animal> animals;

    public Pet(List<Animal> animals){
        this.animals = animals;
    }

    public void printPets(){
        System.out.println(animals);
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
