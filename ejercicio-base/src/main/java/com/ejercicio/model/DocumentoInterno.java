package com.ejercicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("INTERNO")
public class DocumentoInterno extends AbstractDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Departamento", length = 64)
    protected String departamento;

    public DocumentoInterno() {
        super();
        this.departamento = null;
    }

    @Override
    public String getTipoDocumento() {
        return "INTERNO";
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    protected String getPrefijoDescripcion() {
       return "INT-";
    }

    @Override
    protected String getDetalleDescripcion() {
     return "Uso interno - Área: " + departamento;
    }

    @Override
    public String getDetalleListado() {
       return "El departame es: "+ departamento;
    }
}
