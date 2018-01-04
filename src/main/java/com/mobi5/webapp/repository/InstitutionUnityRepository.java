package com.mobi5.webapp.repository;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.mobi5.webapp.model.InstitutionUnity;

public class InstitutionUnityRepository extends AbstractRepository<InstitutionUnity, Long> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstitutionUnityRepository() {
        super(InstitutionUnity.class);
    }
}
