package ru.zaitsev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.zaitsev.MySecondTestAppSpringBoot.exception.ValidationFailedException;

import java.util.stream.Collectors;

@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getAllErrors().stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.joining("; "));
            throw new ValidationFailedException(msg);
        }
    }
}
