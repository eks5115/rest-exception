package com.github.eks5115.rest.exception;

import org.springframework.validation.BindingResult;

/**
 * @author eks5115
 */
public class Exceptions {

    public static void handle(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestValidationException(bindingResult);
        }
    }
}
