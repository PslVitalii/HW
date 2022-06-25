package com.epam.spring.homework2.config.pp;

import com.epam.spring.homework2.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Validator){
            boolean valid = ((Validator) bean).validate();
            if(!valid) {
                // Ideally it should throw BindingException or something similar
                System.out.printf("Bean %s failed validation\n", beanName);
            }
        }

        return bean;
    }
}
