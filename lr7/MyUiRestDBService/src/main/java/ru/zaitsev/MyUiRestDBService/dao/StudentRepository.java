package ru.zaitsev.MyUiRestDBService.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zaitsev.MyUiRestDBService.entity.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}