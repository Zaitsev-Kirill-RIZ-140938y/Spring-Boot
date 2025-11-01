package ru.urfu.courseanimal.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CostCalculatorService {
    public BigDecimal annual(BigDecimal food, BigDecimal vet, BigDecimal other) {
        BigDecimal month = n(food).add(n(vet)).add(n(other));
        return month.multiply(BigDecimal.valueOf(12));
    }
    private BigDecimal n(BigDecimal b) { return b != null ? b : BigDecimal.ZERO; }
}