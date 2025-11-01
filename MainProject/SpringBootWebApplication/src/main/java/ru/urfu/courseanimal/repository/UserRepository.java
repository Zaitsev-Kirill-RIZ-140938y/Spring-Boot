package ru.urfu.courseanimal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.courseanimal.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}