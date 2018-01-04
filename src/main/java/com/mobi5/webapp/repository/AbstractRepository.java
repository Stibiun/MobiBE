package com.mobi5.webapp.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;

abstract class AbstractRepository<T, I extends Serializable> {

    private final Class<T> persistedClass;

    protected abstract EntityManager getEntityManager();

    protected AbstractRepository(Class<T> persistedClass) {
        this.persistedClass = persistedClass;
    }

    public T create(@Valid T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public T edit(@Valid T entity) {
        entity = getEntityManager().merge(entity);
        getEntityManager().flush();
        return entity;
    }

    public void remove(I id) {
        T entity = find(id);
        T mergedEntity = getEntityManager().merge(entity);
        getEntityManager().remove(mergedEntity);
        getEntityManager().flush();
    }

    public void remove(@Valid T entity) {
        T mergedEntity = getEntityManager().merge(entity);
        getEntityManager().remove(mergedEntity);
        getEntityManager().flush();
    }

    public List<T> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(persistedClass);
        query.from(persistedClass);
        return getEntityManager().createQuery(query).getResultList();
    }

    public T find(I id) {
        return getEntityManager().find(persistedClass, id);
    }

    List<T> getAllByNamedQuery(String queryName) {
        Query query = getEntityManager().createNamedQuery(queryName);
        return query.getResultList();
    }

    List<T> getAllByNamedQuery(String queryName, Map<String, Object> parameters) {
        Query query = getEntityManager().createNamedQuery(queryName);
        parameters.forEach((key, value) -> {
            query.setParameter(key, value);
        });
        return query.getResultList();
    }

    T getByNamedQuery(String queryName, Map<String, Object> parameters) {
        Query query = getEntityManager().createNamedQuery(queryName);
        parameters.forEach((key, value) -> {
            query.setParameter(key, value);
        });
        return (T) query.getSingleResult();
    }
}
