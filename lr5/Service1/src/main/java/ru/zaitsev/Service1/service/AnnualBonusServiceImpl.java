package ru.zaitsev.Service1.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.Service1.model.Positions;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(
            Positions positions,
            double salary,
            double bonus,
            int workDays
    ) { // умножаем не на фиксированное кол-во дней (365 ранее), а получаем корректное значение
        int daysInYear = Year.now().length();
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }


    // метод для расчета квартальной премии
    @Override
    public double calculateQuarterBonus(
            Positions positions,
            double salary,
            double bonus,
            int workDays
    ) { // проверка поля и выброс эксепшена в случае значения false
        if(!positions.isManager()) {
            throw new IllegalArgumentException(
                    "Квартальная премия расчитывается только для руководителей, позиция "
                            + positions.name() + "не является руководящей"
            );
        }
        double annualBonus = calculate(positions, salary, bonus, workDays);
        return annualBonus / 4;
    }

}