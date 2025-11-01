package ru.zaitsev.MySpringBoot2Base.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MySpringBoot2Base.entity.Student;

import java.util.List;

@Service
public interface StudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    boolean deleteStudent(int id);

}
