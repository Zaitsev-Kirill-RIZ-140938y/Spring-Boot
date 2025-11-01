package ru.zaitsev.MySpringBoot2Base.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zaitsev.MySpringBoot2Base.dao.StudentDAO;
import ru.zaitsev.MySpringBoot2Base.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    @Transactional
    public boolean deleteStudent(int id) {
        Student student = studentDAO.getStudent(id);
        if (student != null) {
            studentDAO.deleteStudent(id);
            return true;
        }
        return false;
    }
}
