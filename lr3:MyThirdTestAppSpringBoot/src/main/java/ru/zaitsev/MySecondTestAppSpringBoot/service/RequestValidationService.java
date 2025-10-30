package ru.zaitsev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.zaitsev.MySecondTestAppSpringBoot.exception.ValidationFailedException;

@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bingingResult) throws ValidationFailedException {
        if (bingingResult.hasErrors()) {
            throw new
                    ValidationFailedException(bingingResult.getFieldError().toString());
        }
    }
}
