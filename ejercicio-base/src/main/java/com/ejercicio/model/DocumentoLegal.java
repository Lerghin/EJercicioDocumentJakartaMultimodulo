/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author lergh
 */
@Entity
@DiscriminatorValue(TipoDocumentoEnum.LEGAL_VAL)
public class DocumentoLegal extends AbstractDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NumeroExpediente", length = 64)
    private String numeroExpediente;

    @Column(name = "Juzgado", length = 128)
    private String juzgado;

    public DocumentoLegal() {
        super();
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    @Override
    public String getTipoDocumento() {
        return TipoDocumentoEnum.LEGAL_VAL;
    }

    @Override
    protected String getPrefijoDescripcion() {
        return "LE-";
    }

    @Override
    protected String getDetalleDescripcion() {
        return "Es un documento legal ";
    }

    @Override
    public String getDetalleListado() {
        return "Es el expediente nro #" + numeroExpediente;
    }


}
