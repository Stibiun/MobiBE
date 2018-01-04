package com.mobi5.webapp.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.mobi5.webapp.model.Student;

public class StudentRepository extends AbstractRepository<Student, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentRepository() {
        super(Student.class);
    }
}
