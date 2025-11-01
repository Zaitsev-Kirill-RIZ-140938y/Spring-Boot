package ru.zaitsev.Service1.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.Service1.model.Positions;

@Service
public interface AnnualBonusService {

    double calculate(
            Positions positions,
            double salary,
            double bonus,
            int workDays
    );

    double calculateQuarterBonus(
            Positions positions,
            double salary,
            double bonus,
            int workDays
    );

}
