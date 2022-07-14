package com.epam.spring.homework3.validation.annotations;

import com.epam.spring.homework3.validation.validators.YoutubeVideoUrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YoutubeVideoUrlValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YoutubeVideoUrlValidation {
    String message() default "Invalid video URL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
