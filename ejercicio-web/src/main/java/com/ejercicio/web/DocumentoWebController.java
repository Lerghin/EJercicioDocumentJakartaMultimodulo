package com.ejercicio.web;

import com.ejercicio.controller.DocumentoJpaController;
import com.ejercicio.model.DocumentoInterno;
import com.ejercicio.singletons.ApplicationManager;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
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
    private List<DocumentoInterno> items = new ArrayList<>();
    private DocumentoInterno selected = new DocumentoInterno();

    @PostConstruct
    public void init() {
        jpaController = new DocumentoJpaController(ApplicationManager.getEntityManager());
        loadItems();
    }

    public void loadItems() {
        items = new ArrayList<>();
        for (var d : jpaController.findAll()) {
            if (d instanceof DocumentoInterno di) {
                items.add(di);
            }
        }
    }

    public String create() {
        try {
            selected.setFecha(new Date());
            if (selected.getEstado() == null) selected.setEstado("ACTIVO");
            jpaController.create(selected);
            ApplicationManager.getInstance().registrarDocumentoCreado(selected.getNombre());
            selected = new DocumentoInterno();
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
        selected = new DocumentoInterno();
        return "manage?faces-redirect=true";
    }

    public String prepareEdit(DocumentoInterno doc) {
        selected = doc;
        return "manage?faces-redirect=true";
    }

    public List<DocumentoInterno> getItems() {
        return items;
    }

    public DocumentoInterno getSelected() {
        return selected;
    }

    public void setSelected(DocumentoInterno selected) {
        this.selected = selected;
    }
}
