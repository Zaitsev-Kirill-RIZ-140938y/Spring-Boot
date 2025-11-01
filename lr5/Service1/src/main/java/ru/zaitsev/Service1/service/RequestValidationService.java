package ru.zaitsev.Service1.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.zaitsev.Service1.exception.UnsupportedCodeException;
import ru.zaitsev.Service1.exception.ValidationFailedException;
import ru.zaitsev.Service1.model.Request;

@Service
public class RequestValidationService  implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult, Request request)
            throws ValidationFailedException, UnsupportedCodeException {
        if ("123".equals(request.getUid())) {
            throw new UnsupportedCodeException("UID 123 не поддерживается");
        }

        if (bindingResult.hasErrors()) {
            throw new
                    ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }
}
