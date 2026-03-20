package com.ejercicio.web;

import com.ejercicio.controller.DocumentoJpaController;
import com.ejercicio.model.AbstractDocument;
import com.ejercicio.model.DocumentoExterno;
import com.ejercicio.model.DocumentoInterno;
import com.ejercicio.model.DocumentoLegal;
import com.ejercicio.singletons.ApplicationManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ejercicio.model.TipoDocumentoEnum;

@Named("documentoWebController")
@SessionScoped
public class DocumentoWebController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionController sessionController;

    private DocumentoJpaController jpaController;
    private List<AbstractDocument> items = new ArrayList<>();
    private AbstractDocument selected;
    private String tipoDocumento;

    @PostConstruct
    public void init() {
        jpaController = new DocumentoJpaController(ApplicationManager.getEntityManager());
        loadItems();
    }

    public void loadItems() {
        items = jpaController.findAll();
    }

    private void instanciarSeleccionado() {
        TipoDocumentoEnum tipo = TipoDocumentoEnum.fromString(tipoDocumento);
        switch (tipo) {
            case EXTERNO ->
                this.selected = new DocumentoExterno();
            case LEGAL ->
                this.selected = new DocumentoLegal();
            default -> {
                this.selected = new DocumentoInterno();
                this.tipoDocumento = TipoDocumentoEnum.INTERNO.toString();
            }
        }
    }

    public String create() {
        try {

            selected.setFecha(new Date());
            if (selected.getEstado() == null) {
                selected.setEstado("ACTIVO");
            }
            jpaController.create(selected);
            ApplicationManager.getInstance().registrarDocumentoCreado(selected.getNombre());
            loadItems();
            return "list?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String update() {
        try {
            jpaController.edit(selected);
            loadItems();
            return "list?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String delete() {
        try {
            jpaController.delete(selected);
            loadItems();
            return "list?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String save() {
        return selected.getId() == null ? create() : update();
    }

    public String prepareCreate() {
        this.tipoDocumento = TipoDocumentoEnum.INTERNO.toString();

        instanciarSeleccionado();
        // Es vital limpiar también los mensajes de la sesión anterior
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);
        return "manage?faces-redirect=true";
    }

    public String prepareEdit(Object obj) {
        if (obj == null) {
            return null;
        }
        this.selected = (AbstractDocument) obj;
        this.tipoDocumento = TipoDocumentoEnum.fromString(
                this.selected.getTipoDocumento()).toString();;
        return "manage?faces-redirect=true";
    }

    public void limpiarSeleccion() {
        this.selected = null;
    }

    public void cambiarTipo() {
        AbstractDocument datosAnteriores = this.selected;
        TipoDocumentoEnum tipo = TipoDocumentoEnum.fromString(tipoDocumento);
        switch (tipo) {
            case INTERNO ->
                this.selected = new DocumentoInterno();
            case EXTERNO ->
                this.selected = new DocumentoExterno();
            case LEGAL ->
                this.selected = new DocumentoLegal();
        }
        if (datosAnteriores != null) {
            this.selected.setNombre(datosAnteriores.getNombre());
            this.selected.setEstado(datosAnteriores.getEstado());
            this.selected.setFecha(datosAnteriores.getFecha());
        }
    }

    public TipoDocumentoEnum[] getTiposDocumento() {
        return TipoDocumentoEnum.values();
    }

    public void prepareDetalle(AbstractDocument doc) {
        this.selected = doc;
    }

    public boolean esInterno(Object doc) {
        return doc instanceof DocumentoInterno;
    }

    public boolean esExterno(Object doc) {
        return doc instanceof DocumentoExterno;
    }

    public List<AbstractDocument> getItems() {
        return items;
    }

    public void setItems(List<AbstractDocument> items) {
        this.items = items;
    }

    public AbstractDocument getSelected() {
        return selected;
    }

    public void setSelected(AbstractDocument selected) {
        this.selected = selected;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public boolean isInstanceOfDocumentIntern() {
        return selected instanceof DocumentoInterno;
    }

    public boolean isInstanceOfDocumentExtern() {
        return selected instanceof DocumentoExterno;
    }

    public boolean isInstanceOfDocumentLegal() {
        return selected instanceof DocumentoLegal;
    }
}
