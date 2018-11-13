package com.github.eks5115.rest.exception;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eks5115
 */
public class RestValidationException extends RuntimeException {


    private List<ValidationError> errors = new ArrayList<>();

    public RestValidationException(BindingResult bindingResult) {
        Assert.notNull(bindingResult, "bindingResult is not null!");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(new ValidationError(fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public static class ValidationError {
        String entity, property;
        Object invalidValue;
        String message;

        ValidationError(String entity, String property, Object invalidValue, String message) {
            this.entity = entity;
            this.property = property;
            this.invalidValue = invalidValue;
            this.message = message;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Object getInvalidValue() {
            return invalidValue;
        }

        public void setInvalidValue(Object invalidValue) {
            this.invalidValue = invalidValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
