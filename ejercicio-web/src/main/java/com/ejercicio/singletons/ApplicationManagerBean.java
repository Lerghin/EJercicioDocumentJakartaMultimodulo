package com.ejercicio.singletons;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;

@Named("applicationManager")
@ApplicationScoped
public class ApplicationManagerBean {

    public int getTotalDocumentosCreados() {
        return ApplicationManager.getInstance().getTotalDocumentosCreados();
    }

    public List<String> getUltimasActividades() {
        return ApplicationManager.getInstance().getUltimasActividades();
    }
}
