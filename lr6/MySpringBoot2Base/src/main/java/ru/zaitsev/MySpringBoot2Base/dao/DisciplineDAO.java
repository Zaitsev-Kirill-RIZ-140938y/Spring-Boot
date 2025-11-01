package ru.zaitsev.MySpringBoot2Base.dao;

import org.springframework.stereotype.Repository;
import ru.zaitsev.MySpringBoot2Base.entity.Discipline;

import java.util.List;

@Repository
public interface DisciplineDAO {

    List<Discipline> getAllDisciplines();

    Discipline saveDiscipline(Discipline discipline);

    Discipline getDiscipline(int id);

    void deleteDiscipline(int id);
}
