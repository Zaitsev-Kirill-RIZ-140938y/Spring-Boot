package ru.zaitsev.MySecondTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.zaitsev.MySecondTestAppSpringBoot.exception.ValidationFailedException;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
}
