package com.epam.spring.homework2.config.pp;

import com.epam.spring.homework2.beans.BeanB;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
			if (factory.getType(beanDefinitionName) == BeanB.class) {
				BeanDefinition beanBDefinition = factory.getBeanDefinition(beanDefinitionName);
				beanBDefinition.setInitMethodName("otherInit");
			}
		}
	}
}
