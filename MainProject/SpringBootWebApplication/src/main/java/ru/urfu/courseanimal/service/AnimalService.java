package ru.urfu.courseanimal.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfu.courseanimal.entity.Animal;
import ru.urfu.courseanimal.entity.User;
import ru.urfu.courseanimal.repository.AnimalExpenseRepository;
import ru.urfu.courseanimal.repository.AnimalRepository;
import ru.urfu.courseanimal.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class AnimalService {
    private final AnimalRepository animalRepo;
    private final UserRepository userRepo;
    private final AnimalExpenseRepository expenseRepo;

    public AnimalService(AnimalRepository animalRepo,
                         UserRepository userRepo,
                         AnimalExpenseRepository expenseRepo) {
        this.animalRepo = animalRepo;
        this.userRepo = userRepo;
        this.expenseRepo = expenseRepo;
    }

    public List<Animal> listForCurrentUser(Authentication auth) {
        boolean isAdmin = auth.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin) return animalRepo.findAll();
        String email = auth.getName();
        User u = userRepo.findByEmail(email);
        if (u == null) return List.of();
        return animalRepo.findByOwner(u);
    }

    public Animal create(Animal a, Authentication auth) {
        boolean canWrite = auth.getAuthorities().stream().anyMatch(ga ->
                ga.getAuthority().equals("ROLE_USER") || ga.getAuthority().equals("ROLE_ADMIN"));
        if (!canWrite) throw new AccessDeniedException("READ_ONLY cannot create");
        User owner = userRepo.findByEmail(auth.getName());
        a.setOwner(owner);
        return animalRepo.save(a);
    }

    public Animal update(Animal a, Authentication auth) {
        Animal db = animalRepo.findById(a.getId()).orElseThrow();
        boolean isAdmin = auth.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (!isAdmin && !db.getOwner().getEmail().equals(auth.getName()))
            throw new AccessDeniedException("Not your animal");
        db.setName(a.getName());
        db.setSpecies(a.getSpecies());
        db.setAge(a.getAge());
        return db;
    }

    public void delete(Integer id, Authentication auth) {
        Animal db = animalRepo.findById(id).orElseThrow();
        boolean isAdmin = auth.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (!isAdmin && !db.getOwner().getEmail().equals(auth.getName()))
            throw new AccessDeniedException("Not your animal");

        // 1) сначала удаляем связанные расходы (если есть)
        expenseRepo.findByAnimal(db).ifPresent(expenseRepo::delete);
        // 2) потом само животное
        animalRepo.delete(db);
    }
}