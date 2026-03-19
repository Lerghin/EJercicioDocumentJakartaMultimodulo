package com.ejercicio.web;

import com.ejercicio.controller.DocumentoJpaController;
import com.ejercicio.model.AbstractDocument;
import com.ejercicio.model.DocumentoExterno;
import com.ejercicio.model.DocumentoInterno;
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

@Named("documentoWebController")
@SessionScoped
public class DocumentoWebController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionController sessionController;

    private DocumentoJpaController jpaController;
    private List<AbstractDocument> items = new ArrayList<>();
    private AbstractDocument selected = new DocumentoInterno();

    private String tipoDocumento;

    @PostConstruct
    public void init() {
        jpaController = new DocumentoJpaController(ApplicationManager.getEntityManager());
        loadItems();
    }

    /*
    public void loadItems() {
        items = new ArrayList<>();
        for (var d : jpaController.findAll()) {
            if (d instanceof DocumentoInterno di) {
                items.add(di);
            }
        }
    }
/*
    // voy a traerme todos los documentos, luego le hago un filtro con select
    
    

     */
    public void loadItems() {
        items = jpaController.findAll();
    }

    public String create() {
        try {
            selected.setFecha(new Date());
            if (selected.getEstado() == null) {
                selected.setEstado("ACTIVO");
            }
            jpaController.create(selected);
            ApplicationManager.getInstance().registrarDocumentoCreado(selected.getNombre());
            this.selected = new DocumentoInterno();
            this.tipoDocumento = null;

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
        this.tipoDocumento = null;

        selected = new DocumentoInterno();
        // Es vital limpiar también los mensajes de la sesión anterior
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);
        return "manage?faces-redirect=true";
    }

    public String prepareEdit(DocumentoInterno doc) {
        selected = doc;
        return "manage?faces-redirect=true";
    }

    public void cambiarTipo() {
        // 1. Guardamos la referencia vieja para no perder los datos comunes
        AbstractDocument datosLlenados = this.selected;

        // 2. Creamos la nueva instancia según el tipo
        if ("EXTERNO".equals(tipoDocumento)) {
            this.selected = new DocumentoExterno();
        } else {
            this.selected = new DocumentoInterno();
        }

        //Mantener datos prellenados por si cambia de form
        if (datosLlenados != null) {
            this.selected.setNombre(datosLlenados.getNombre());
            this.selected.setEstado(datosLlenados.getEstado());
            this.selected.setFecha(datosLlenados.getFecha());

        }

        // 4. Limpieza de mensajes (Correcto como lo tenías)
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(false);
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

        if ("EXTERNO".equals(tipoDocumento) && !(selected instanceof DocumentoExterno)) {
            selected = new DocumentoExterno();
        } else if ("INTERNO".equals(tipoDocumento) && !(selected instanceof DocumentoInterno)) {
            selected = new DocumentoInterno();
        }
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

}
