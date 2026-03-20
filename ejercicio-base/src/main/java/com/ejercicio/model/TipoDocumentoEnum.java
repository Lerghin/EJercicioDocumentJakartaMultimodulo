/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ejercicio.model;

/**
 *
 * @author lergh
 */
public enum TipoDocumentoEnum {
    INTERNO,
    EXTERNO,
    LEGAL;

    public static final String INTERNO_VAL = "INTERNO";
    public static final String EXTERNO_VAL = "EXTERNO";
    public static final String LEGAL_VAL   = "LEGAL";

    @Override
    public String toString() {
        return this.name();
    }

    public static TipoDocumentoEnum fromString(String valor) {
        return TipoDocumentoEnum.valueOf(valor.toUpperCase());
    }
}
