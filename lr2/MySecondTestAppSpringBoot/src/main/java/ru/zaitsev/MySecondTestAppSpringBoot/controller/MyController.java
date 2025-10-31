package ru.zaitsev.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zaitsev.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.zaitsev.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.zaitsev.MySecondTestAppSpringBoot.model.Request;
import ru.zaitsev.MySecondTestAppSpringBoot.model.Response;
import ru.zaitsev.MySecondTestAppSpringBoot.service.ValidationService;

@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime()) // по ТЗ — из запроса
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            // 1) bean validation
            validationService.isValid(bindingResult);

            // 2) бизнес-правило ЛР: uid == "123" → UnsupportedCodeException
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("UID '123' is not supported");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage("Не поддерживаемая ошибка");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
