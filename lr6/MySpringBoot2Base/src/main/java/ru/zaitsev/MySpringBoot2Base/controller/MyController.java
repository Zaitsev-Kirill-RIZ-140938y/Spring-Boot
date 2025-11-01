package ru.zaitsev.MySpringBoot2Base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zaitsev.MySpringBoot2Base.entity.Discipline;
import ru.zaitsev.MySpringBoot2Base.entity.Student;
import ru.zaitsev.MySpringBoot2Base.service.StudentService;
import ru.zaitsev.MySpringBoot2Base.service.DisciplineService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    private final StudentService studentService;
    private final DisciplineService disciplineService;

    @Autowired
    public MyController(StudentService studentService, DisciplineService disciplineService) {
        this.studentService = studentService;
        this.disciplineService = disciplineService;
    }

    @GetMapping("/students")
    public List<Student> allStudents() {
        List<Student> allStudents = studentService.getAllStudents();
        return allStudents;
    }

    @GetMapping("/disciplines")
    public List<Discipline> allDisciplines() {
        List<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        return allDisciplines;
    }

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable("id") int id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/disciplines/{id}")
    public Discipline getDiscipline(@PathVariable("id") int id) {
        return disciplineService.getDiscipline(id);
    }


    @PostMapping("/students")
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        Student saved = studentService.saveStudent(student);
        if (saved.getId() != null) {
            return ResponseEntity.ok("Студент успешно сохранён с id = " + saved.getId());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сохранения студента");
        }
    }

    @PostMapping("/disciplines")
    public ResponseEntity<?> saveDiscipline(@RequestBody Discipline discipline) {
        Discipline saved = disciplineService.saveDiscipline(discipline);
        if (saved.getId() != null) {
            return ResponseEntity.ok("Дисциплина успешно сохранена с id = " + saved.getId());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка сохранения дисциплины");
        }
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @PutMapping("/disciplines")
    public Discipline updateStudent(@RequestBody Discipline discipline) {
        disciplineService.saveDiscipline(discipline);
        return discipline;
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok("Студент с id = " + id + " удалён");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент не найден");
        }
    }

    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<?> deleteDiscipline(@PathVariable("id") int id) {
        boolean deleted = disciplineService.deleteDiscipline(id);
        if (deleted) {
            return ResponseEntity.ok("Дисциплина с id = " + id + " удалена");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Дисциплина не найдена");
        }
    }

}
