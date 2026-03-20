/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.controller;

import com.ejercicio.model.AbstractDocument;
import com.ejercicio.model.DocumentoInterno;
import com.ejercicio.model.TipoDocumento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author lergh
 */
public class TipoDocumentoController extends AbstractObjectJPAController<TipoDocumento> {

    public TipoDocumentoController(EntityManager em) {
        super(em, TipoDocumento.class);
    }

    @Override
    public List<TipoDocumento> findAll() {
        TypedQuery<TipoDocumento> q = em.createQuery(
                "SELECT d FROM TipoDocumento d ORDER BY d.nombre ASC",
                TipoDocumento.class);
        return q.getResultList();
    }

}
