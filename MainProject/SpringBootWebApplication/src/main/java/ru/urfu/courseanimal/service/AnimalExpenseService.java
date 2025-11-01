package ru.urfu.courseanimal.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.courseanimal.entity.*;
import ru.urfu.courseanimal.repository.*;

import java.math.BigDecimal;

@Service
@Transactional
public class AnimalExpenseService {
    private final AnimalExpenseRepository repo;
    private final AnimalRepository animalRepo;

    public AnimalExpenseService(AnimalExpenseRepository repo, AnimalRepository animalRepo) {
        this.repo = repo; this.animalRepo = animalRepo;
    }

    public AnimalExpense getOrCreateForAnimal(Integer animalId, Authentication auth) {
        Animal a = animalRepo.findById(animalId).orElseThrow();
        boolean admin = auth.getAuthorities().stream()
                .anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"));
        if (!admin && (a.getOwner()==null || !a.getOwner().getEmail().equals(auth.getName())))
            throw new AccessDeniedException("Not your animal");

        return repo.findByAnimal(a).orElseGet(() -> {
            AnimalExpense e = new AnimalExpense();
            e.setAnimal(a);
            e.setMonthlyFood(BigDecimal.ZERO);
            e.setMonthlyVet(BigDecimal.ZERO);
            e.setMonthlyOther(BigDecimal.ZERO);
            return repo.save(e);
        });
    }

    public AnimalExpense save(AnimalExpense in, Authentication auth) {
        Integer animalId = (in.getAnimal()!=null) ? in.getAnimal().getId() : null;
        if (animalId == null) throw new IllegalArgumentException("animalId required");

        // managed Animal + проверка владельца
        Animal a = animalRepo.findById(animalId).orElseThrow();
        boolean admin = auth.getAuthorities().stream()
                .anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"));
        if (!admin && (a.getOwner()==null || !a.getOwner().getEmail().equals(auth.getName())))
            throw new AccessDeniedException("Not your animal");

        // если запись уже есть — обновим её; если нет — создадим
        AnimalExpense db = (in.getId() != null)
                ? repo.findById(in.getId()).orElseGet(() -> repo.findByAnimal(a).orElse(new AnimalExpense()))
                : repo.findByAnimal(a).orElse(new AnimalExpense());

        db.setAnimal(a);
        db.setMonthlyFood(defaultZero(in.getMonthlyFood()));
        db.setMonthlyVet(defaultZero(in.getMonthlyVet()));
        db.setMonthlyOther(defaultZero(in.getMonthlyOther()));

        return repo.save(db);
    }

    private BigDecimal defaultZero(BigDecimal x) {
        return x != null ? x : BigDecimal.ZERO;
    }
}