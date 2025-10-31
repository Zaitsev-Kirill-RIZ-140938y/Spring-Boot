package ru.zaitsev.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.zaitsev.MyThirdTestAppSpringBoot.exception.ValidationFailedException;

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
