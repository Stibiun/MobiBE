package com.mobi5.webapp.producers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProducer {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("DEFAULT_PU");

    @Produces
    @RequestScoped
    public EntityManager entityManager() {
        return factory.createEntityManager();
    }

    public void close(@Disposes EntityManager manager) {
        manager.close();
    }
}
