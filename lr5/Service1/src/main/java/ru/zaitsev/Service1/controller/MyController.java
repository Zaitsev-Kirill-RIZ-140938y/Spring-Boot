package ru.zaitsev.Service1.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.zaitsev.Service1.exception.UnsupportedCodeException;
import ru.zaitsev.Service1.exception.ValidationFailedException;
import ru.zaitsev.Service1.model.*;
import ru.zaitsev.Service1.service.AnnualBonusService;
import ru.zaitsev.Service1.service.ModifyRequestService;
import ru.zaitsev.Service1.service.ModifyResponseService;
import ru.zaitsev.Service1.service.ValidationService;
import ru.zaitsev.Service1.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    private final ModifyResponseService modifyResponseService;

    private final ModifyRequestService modifyRequestService;

    private final AnnualBonusService annualbonusService;

    @Autowired
    public  MyController(ValidationService validationService,
                         @Qualifier("ModifySystemTimeResponseService")
                         ModifyResponseService modifyResponseService,
                         ModifyRequestService modifyRequestService,
                         AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.annualbonusService = annualBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        // время получения запроса в текущем сервисе
        String service1RequestTime = DateTimeUtil.getCustomFormat().format(new Date());
        request.setService1RequestTime(service1RequestTime);

        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCES)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("response: {}", response);

        try {
            validationService.isValid(bindingResult, request);
            // bonusService для расчета
            if (request.getPosition() != null
                    && request.getSalary() != null
                    && request.getBonus() != null
                    && request.getWorkDays() != null
            ) {
                double annualBonus = annualbonusService.calculate( // Вызов через bonusService
                        request.getPosition(),
                        request.getSalary(),
                        request.getBonus(),
                        request.getWorkDays()
                );
                response.setAnnualBonus(annualBonus);

                // Расчет квартальной премии
                try {
                    double quarterBonus = annualbonusService.calculateQuarterBonus(
                            request.getPosition(),
                            request.getSalary(),
                            request.getBonus(),
                            request.getWorkDays()
                    );
                    response.setQuarterBonus(quarterBonus);
                } catch (IllegalArgumentException e) {
                    log.info("Quarter bonus not available: {}", e.getMessage());
                }
            }

        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("Validation failed - response: {}, error: {}", response, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) { // доработка со 2 лабы
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("Unsupported code - response: {}, error: {}", response, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("Unexpected error - response: {}, exception: {}", response, e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        log.info("Final successful response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}