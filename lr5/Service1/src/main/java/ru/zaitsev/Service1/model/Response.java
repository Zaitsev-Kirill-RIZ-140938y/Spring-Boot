package ru.zaitsev.Service1.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    // Уникальный id
    private String uid;
    // id операции
    private String operationUid;
    // Системное время
    private String systemTime;
    // Код
    private Codes code;
    // Годовая премия
    private Double annualBonus;
    // Квартальная премия
    private Double quarterBonus;
    // Код ошибки
    private ErrorCodes errorCode;
    // Сообщение об ошибке
    private ErrorMessages errorMessage;
}
