package ru.zaitsev.MySpringBoot2Base.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zaitsev.MySpringBoot2Base.dao.DisciplineDAO;
import ru.zaitsev.MySpringBoot2Base.entity.Discipline;

import java.util.List;

@Service
public class DisciplineServiceImpl implements DisciplineService {

    @Autowired
    private DisciplineDAO disciplineDAO;

    @Override
    @Transactional
    public List<Discipline> getAllDisciplines() { return disciplineDAO.getAllDisciplines(); }

    @Override
    @Transactional
    public Discipline saveDiscipline(Discipline discipline) {
        return disciplineDAO.saveDiscipline(discipline);
    }

    @Override
    public Discipline getDiscipline(int id) {
        return disciplineDAO.getDiscipline(id);
    }

    @Override
    @Transactional
    public boolean deleteDiscipline(int id) {
        Discipline discipline = disciplineDAO.getDiscipline(id);
        if (discipline != null) {
            disciplineDAO.deleteDiscipline(id);
            return true;
        }
        return false;
    }
}
