package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Student;
import sk.tacademy.gamestudio.entity.StudyGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class StudentGroupServiceJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void addStudyGroup(StudyGroup studyGroup) {
        entityManager.persist(studyGroup);
    }
}
