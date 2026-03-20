package com.ejercicio.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "JPADC", discriminatorType = DiscriminatorType.STRING, length = 32)
@Access(AccessType.FIELD)
@Table(name = "TECDocument")
public abstract class AbstractDocument extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PORTAL_TABLE_NAME = "TECDocument";
    

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "Nombre")
    protected String nombre;

    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    protected Date fecha;

    @Size(max = 32)
    @Column(name = "Estado")
    protected String estado;

    protected AbstractDocument() {
        super();
        this.nombre = null;
        this.fecha = new Date();
        this.estado = "ACTIVO";
    }

    public abstract String getTipoDocumento();

    private static final String SEPARADOR_CAMPOS = " | ";

    public final String getDescripcionCompleta() {
        return getPrefijoDescripcion()
                + SEPARADOR_CAMPOS
                + getTipoDocumento()
                + SEPARADOR_CAMPOS
                + nombre
                + SEPARADOR_CAMPOS
                + getDetalleDescripcion()
                + " (" + estado + ")";
    }

    protected abstract String getPrefijoDescripcion();

    protected abstract String getDetalleDescripcion();
    public abstract String getDetalleListado();
   

    @Override
    public String getTableName() {
        return PORTAL_TABLE_NAME;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 31 * hash + Objects.hashCode(nombre);
        hash = 31 * hash + Objects.hashCode(fecha);
        hash = 31 * hash + Objects.hashCode(estado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        AbstractDocument other = (AbstractDocument) obj;
        return Objects.equals(nombre, other.nombre)
                && Objects.equals(fecha, other.fecha)
                && Objects.equals(estado, other.estado);
    }
}
