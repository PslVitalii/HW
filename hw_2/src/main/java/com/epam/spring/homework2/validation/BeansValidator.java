package com.epam.spring.homework2.validation;

import com.epam.spring.homework2.beans.BaseBean;

public class BeansValidator {
	public boolean validate(BaseBean target) {
		return target.getName() != null && target.getValue() >= 0;
	}
}
