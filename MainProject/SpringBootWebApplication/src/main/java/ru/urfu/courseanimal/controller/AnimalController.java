package ru.urfu.courseanimal.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.urfu.courseanimal.entity.Animal;
import ru.urfu.courseanimal.entity.AnimalExpense;
import ru.urfu.courseanimal.repository.AnimalExpenseRepository;
import ru.urfu.courseanimal.service.AnimalService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final AnimalExpenseRepository expenseRepo;

    public AnimalController(AnimalService animalService, AnimalExpenseRepository expenseRepo) {
        this.animalService = animalService;
        this.expenseRepo = expenseRepo;
    }

    @GetMapping
    public String list(Model model, Authentication auth) {
        List<Animal> animals = animalService.listForCurrentUser(auth);

        // Считаем суммы расходов по каждому животному
        Map<Integer, BigDecimal> monthlyTotals = new HashMap<>();
        Map<Integer, BigDecimal> annualTotals  = new HashMap<>();

        List<AnimalExpense> expenses = animals.isEmpty()
                ? List.of()
                : expenseRepo.findAllByAnimalIn(animals);

        for (AnimalExpense e : expenses) {
            BigDecimal month = nz(e.getMonthlyFood())
                    .add(nz(e.getMonthlyVet()))
                    .add(nz(e.getMonthlyOther()));
            Integer aid = e.getAnimal().getId();
            monthlyTotals.put(aid, month);
            annualTotals.put(aid, month.multiply(BigDecimal.valueOf(12)));
        }

        model.addAttribute("animals", animals);
        model.addAttribute("monthlyTotals", monthlyTotals);
        model.addAttribute("annualTotals", annualTotals);
        return "animals/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animals/form";
    }

    @PostMapping
    public String create(@ModelAttribute Animal animal, Authentication auth) {
        animalService.create(animal, auth);
        return "redirect:/animals";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model, Authentication auth) {
        // берём из списка текущего пользователя (без отдельного сервиса getById)
        Animal animal = animalService.listForCurrentUser(auth)
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();
        model.addAttribute("animal", animal);
        return "animals/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Animal animal, Authentication auth) {
        animal.setId(id);
        animalService.update(animal, auth);
        return "redirect:/animals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, Authentication auth) {
        animalService.delete(id, auth);
        return "redirect:/animals";
    }

    private BigDecimal nz(BigDecimal x) { return x != null ? x : ZERO; }
}