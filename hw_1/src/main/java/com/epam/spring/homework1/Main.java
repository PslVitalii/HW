package com.epam.spring.homework1;

import com.epam.spring.homework1.config.BeansConfig;
import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Pet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);

        Pet pet = context.getBean(Pet.class);
        pet.printPets();

        System.out.println("Experiments: ");
        Cheetah cheetah1 = context.getBean(Cheetah.class);
        System.out.println("By type - " + cheetah1);

        Cheetah cheetah2 = context.getBean("cheetahB", Cheetah.class);
        System.out.println("By bean name - " + cheetah2);
    }
}
