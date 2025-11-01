package ru.urfu.courseanimal.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.urfu.courseanimal.service.CostCalculatorService;

import java.math.BigDecimal;

@Controller
public class CalculatorController {
    private final CostCalculatorService calc;
    public CalculatorController(CostCalculatorService calc) { this.calc = calc; }

    @GetMapping("/calculator")
    public String form() { return "calculator/form"; }

    @PostMapping("/calculator")
    public String calc(
            @RequestParam BigDecimal food,
            @RequestParam BigDecimal vet,
            @RequestParam BigDecimal other,
            Model model
    ) {
        model.addAttribute("result", calc.annual(food, vet, other));
        model.addAttribute("food", food); model.addAttribute("vet", vet); model.addAttribute("other", other);
        return "calculator/form";
    }
}