package com.ejercicio.controller;

import com.ejercicio.model.EntityBase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Objects;

public abstract class AbstractObjectJPAController<T extends EntityBase> {

    protected final EntityManager em;
    protected final Class<T> entityClass;

    protected AbstractObjectJPAController(EntityManager em, Class<T> entityClass) {
        this.em = Objects.requireNonNull(em);
        this.entityClass = Objects.requireNonNull(entityClass);
    }

    public abstract List<T> findAll();

    public T findById(Integer id) {
        if (id == null) return null;
        return em.find(entityClass, id);
    }

    public void create(T entity) throws Exception {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void edit(T entity) throws Exception {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(T entity) throws Exception {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
