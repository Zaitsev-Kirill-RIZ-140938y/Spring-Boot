package ru.urfu.courseanimal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.courseanimal.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}