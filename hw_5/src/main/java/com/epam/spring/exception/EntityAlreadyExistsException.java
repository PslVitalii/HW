package com.epam.spring.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(long identifier, Class<?> type){
        super(String.format("%s with identifier: '%d' already exists", type.getSimpleName(), identifier));
    }

    public EntityAlreadyExistsException(String identifier, Class<?> type){
        super(String.format("%s with identifier: '%s' already exists", type.getSimpleName(), identifier));
    }
}
