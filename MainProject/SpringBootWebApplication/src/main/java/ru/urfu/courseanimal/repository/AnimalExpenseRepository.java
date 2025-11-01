package ru.urfu.courseanimal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.courseanimal.entity.Animal;
import ru.urfu.courseanimal.entity.AnimalExpense;

import java.util.List;
import java.util.Optional;

public interface AnimalExpenseRepository extends JpaRepository<AnimalExpense, Integer> {
    Optional<AnimalExpense> findByAnimal(Animal animal);
    List<AnimalExpense> findAllByAnimalIn(List<Animal> animals);
}