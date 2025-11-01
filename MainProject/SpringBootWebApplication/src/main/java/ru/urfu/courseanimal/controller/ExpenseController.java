package ru.urfu.courseanimal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urfu.courseanimal.entity.AnimalExpense;
import ru.urfu.courseanimal.service.AnimalExpenseService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    private final AnimalExpenseService svc;
    public ExpenseController(AnimalExpenseService svc) { this.svc = svc; }

    @GetMapping("/{animalId}")
    public String edit(@PathVariable Integer animalId, Authentication auth, Model model) {
        AnimalExpense e = svc.getOrCreateForAnimal(animalId, auth);
        model.addAttribute("expense", e);
        return "expenses/form";
    }

    @PostMapping("/{animalId}")
    public String update(@PathVariable Integer animalId,
                         @ModelAttribute("expense") AnimalExpense form,
                         Authentication auth) {
        // грузим/создаём управляемую запись и переносим значения
        AnimalExpense db = svc.getOrCreateForAnimal(animalId, auth);
        db.setMonthlyFood(form.getMonthlyFood());
        db.setMonthlyVet(form.getMonthlyVet());
        db.setMonthlyOther(form.getMonthlyOther());
        svc.save(db, auth);
        return "redirect:/animals";
    }
}