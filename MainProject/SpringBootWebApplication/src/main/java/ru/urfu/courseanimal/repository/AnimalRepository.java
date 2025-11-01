package ru.urfu.courseanimal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.courseanimal.entity.Animal;
import ru.urfu.courseanimal.entity.User;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findByOwner(User owner);
}