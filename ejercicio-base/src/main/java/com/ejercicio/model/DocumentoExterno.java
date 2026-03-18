/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serializable;

/**
 *
 * @author lergh
 */
@Entity
//Configurar @DiscriminatorValue("EXTERNO").
@DiscriminatorValue("EXTERNO")
public class DocumentoExterno extends AbstractDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    //Agregar 1–2 campos propios (por ejemplo proveedor y/o numeroReferencia) con sus @Column.
    @Column(name = "Proveedor", length = 64)
    protected String proveedor;
    @Column(name = "Numero_Referencia", length = 64)
    protected String nroReferencia;
    @Column(name = "curier", length = 64)
    protected String curier;

    public DocumentoExterno() {
        super();
    }

    public DocumentoExterno(String proveedor, String nroReferencia, String curier) {
        super();
        this.proveedor = proveedor;
        this.nroReferencia = nroReferencia;
        this.curier = curier;
    }

    @Override
    protected String getTipoDocumento() {
        return "EXTERNO";
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNroReferencia() {
        return nroReferencia;
    }

    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
    }

    public String getCurier() {
        return curier;
    }

    public void setCurier(String curier) {
        this.curier = curier;
    }

}
