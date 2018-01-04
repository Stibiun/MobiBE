package com.mobi5.webapp.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.mobi5.webapp.model.EducationalInstitution;

public class EducationalInstitutionRepository extends AbstractRepository<EducationalInstitution, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EducationalInstitutionRepository() {
        super(EducationalInstitution.class);
    }
}
