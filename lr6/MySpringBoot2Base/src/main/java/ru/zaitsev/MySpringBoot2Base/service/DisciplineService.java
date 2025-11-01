package ru.zaitsev.MySpringBoot2Base.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MySpringBoot2Base.entity.Discipline;

import java.util.List;

@Service
public interface DisciplineService {

    List<Discipline> getAllDisciplines();

    Discipline saveDiscipline(Discipline discipline);

    Discipline getDiscipline(int id);

    boolean deleteDiscipline(int id);
}
