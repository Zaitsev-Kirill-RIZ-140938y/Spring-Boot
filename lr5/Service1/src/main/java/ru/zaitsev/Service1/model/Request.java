package ru.zaitsev.Service1.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    // Не пустой, макс. размер до 32 символов
    @NotBlank
    @Size(max = 32)
    private String uid;

    // До 32 символов
    @Size(max = 32)
    private String operationUid;

    private Systems systemName;
    private String systemTime;
    private String source;

    // Поле для передачи времени получения запроса
    private String service1RequestTime;

    // Доработка с 5 лабы
    // Позиция
    private Positions position;
    // Зарплата
    private Double salary;
    // Бонус
    private Double bonus;
    // Рабочие дни
    private Integer workDays;


    // Диапазон валидных значений
    @Min(value = 1)
    @Max(value = 100000)
    private Integer communicationId;

    private Integer templateId;
    private Integer productCode;
    private Integer smsCode;

    @Override
    public String toString() {
        return "{" +
                "uid= " + uid + '\'' +
                ", operationUid= " + operationUid + '\'' +
                ", systemName= " + systemName + '\'' +
                ", systemTime= " + systemTime + '\'' +
                ", source= " + source + '\'' +
                ", service1RequestTime= " + service1RequestTime + '\'' +
                ", communicationId= " + communicationId +
                ", templateId= " + templateId +
                ", productCode= " + productCode +
                ", smsCode= " + smsCode +
                '}';
    }
}
