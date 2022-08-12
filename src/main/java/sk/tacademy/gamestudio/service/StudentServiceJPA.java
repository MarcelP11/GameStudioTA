package sk.tacademy.gamestudio.service;

import sk.tacademy.gamestudio.entity.Score;
import sk.tacademy.gamestudio.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentServiceJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    public List<Student> getStudents() {
        return entityManager
                .createQuery("select s from Student s")
                .getResultList();
    }

    public void reset() {
        entityManager.createNativeQuery("DELETE FROM student").executeUpdate();

    }
}
