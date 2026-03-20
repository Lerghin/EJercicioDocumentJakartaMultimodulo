/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejercicio.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author lergh
 */
@Entity
@Table(name = "TipoDocumento")
@Access(AccessType.FIELD)
public class TipoDocumento extends EntityBase implements Serializable {
    private static final long serialVersionUID=1L;
     public static final String PORTAL_TABLE_NAME = "TipoDocumento";

    @Column(name="Codigo", length=32)
    private String codigo;
    
    @Column (name="Nombre", length=132)
    private String nombre;
     @Column(name = "ClaseJava", length = 256)
    private String claseJava;   // "com.ejercicio.model.DocumentoLegal"

    @Column(name = "Activo")
    private boolean activo;

    public TipoDocumento() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClaseJava() {
        return claseJava;
    }

    public void setClaseJava(String claseJava) {
        this.claseJava = claseJava;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String getTableName() {
        return PORTAL_TABLE_NAME;
    }
    
    
}
