package com.epam.spring.homework3.validation.validators;

import com.epam.spring.homework3.validation.annotations.YoutubeVideoUrlValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YoutubeVideoUrlValidator implements ConstraintValidator<YoutubeVideoUrlValidation, String> {
    private final static String YOUTUBE_VIDEO_URL_REGEX = "(https?://)?(www.)?youtube.com/watch\\?v=[\\w&=]*";

    @Override
    public void initialize(YoutubeVideoUrlValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return field != null && !field.isBlank() && field.matches(YOUTUBE_VIDEO_URL_REGEX);
    }
}
