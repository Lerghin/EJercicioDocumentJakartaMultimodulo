package com.ejercicio.web;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String localeCode = "en_US";
    private Locale locale = Locale.US;
    private final Map<String, String> locales = new LinkedHashMap<>();

    public SessionController() {
        locales.put("en_US", "English");
        locales.put("es_PR", "Espanol");
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
        if ("es_PR".equals(localeCode)) {
            locale = new Locale("es", "PR");
        } else {
            locale = Locale.US;
        }
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public Map<String, String> getLocales() {
        return locales;
    }

    public void changeLocale(String newLocaleCode) throws IOException {
        if (locales.containsKey(newLocaleCode)) {
            localeCode = newLocaleCode;
            if ("es_PR".equals(localeCode)) {
                locale = new Locale("es", "PR");
            } else {
                locale = Locale.US;
            }
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
    }

    public String getJsfSessionId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session != null ? session.getId() : "";
    }
}
