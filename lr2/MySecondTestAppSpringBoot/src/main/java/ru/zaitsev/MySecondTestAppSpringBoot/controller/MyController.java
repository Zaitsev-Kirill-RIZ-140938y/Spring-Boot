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

import java.time.Instant;
import java.time.format.DateTimeFormatter;

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

        try {
            // 1) bean-валидация по таблице 1.1
            validationService.isValid(bindingResult);

            // 2) бизнес-правило ЛР: uid == "123" → UnsupportedCodeException
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("UID '123' is not supported");
            }

            // 3) успешный ответ: текущее время сервера
            String serverNow = DateTimeFormatter.ISO_INSTANT.format(Instant.now());

            Response ok = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(serverNow)
                    .code("success")
                    .errorCode("")
                    .errorMessage("")
                    .build();

            return ResponseEntity.ok(ok);

        } catch (ValidationFailedException e) {
            Response bad = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    // при ошибке можно отдать то, что прислал клиент
                    .systemTime(request.getSystemTime())
                    .code("failed")
                    .errorCode("ValidationException")
                    .errorMessage("Ошибка валидации")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bad);

        } catch (UnsupportedCodeException e) {
            Response bad = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(request.getSystemTime())
                    .code("failed")
                    .errorCode("UnsupportedCodeException")
                    .errorMessage("Не поддерживаемая ошибка")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bad);

        } catch (Exception e) {
            Response err = Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(request.getSystemTime())
                    .code("failed")
                    .errorCode("UnknownException")
                    .errorMessage("Произошла непредвиденная ошибка")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }
}
