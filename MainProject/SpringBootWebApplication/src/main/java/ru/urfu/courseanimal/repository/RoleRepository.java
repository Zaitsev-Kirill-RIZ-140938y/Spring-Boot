package ru.urfu.courseanimal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.courseanimal.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}