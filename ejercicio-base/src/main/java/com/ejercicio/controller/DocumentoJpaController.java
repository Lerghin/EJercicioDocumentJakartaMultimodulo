package com.ejercicio.controller;

import com.ejercicio.model.AbstractDocument;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DocumentoJpaController extends AbstractObjectJPAController<AbstractDocument> {

    public DocumentoJpaController(EntityManager em) {
        super(em, AbstractDocument.class);
    }

    @Override
    public List<AbstractDocument> findAll() {
        TypedQuery<AbstractDocument> q = em.createQuery(
                "SELECT d FROM AbstractDocument d ORDER BY d.fecha DESC",
                AbstractDocument.class);
        return q.getResultList();
    }
}
