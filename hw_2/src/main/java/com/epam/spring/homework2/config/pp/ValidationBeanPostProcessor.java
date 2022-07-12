package com.epam.spring.homework2.config.pp;

import com.epam.spring.homework2.beans.BaseBean;
import com.epam.spring.homework2.validation.BeansValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ValidationBeanPostProcessor implements BeanPostProcessor {
	private final BeansValidator beanValidator;

	public ValidationBeanPostProcessor(BeansValidator beanValidator) {
		this.beanValidator = beanValidator;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof BaseBean) {
			BaseBean baseBean = (BaseBean) bean;
			boolean isValid = beanValidator.validate(baseBean);

			if (!isValid) {
				System.out.println(beanName + " failed validation");
			}
		}

		return bean;
	}
}
