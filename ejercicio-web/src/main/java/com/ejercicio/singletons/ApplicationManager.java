package com.ejercicio.singletons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationManager implements ServletContextListener {

    protected static final String JPAPERSISTENCEUNITNAMEWEBXMLENVENTRYNAME = "jpa.persistenceUnitName";
    protected static final String JPANONJTADATASOURCEWEBXMLENVENTRYNAME = "jpa.non-jta-data-source";
    protected static final String JPAECLIPSELINKTARGETSERVERWEBXMLENVENTRYNAME = "jpa.eclipselink.target-server";
    protected static final String JPAECLIPSELINKTARGETDATABASEWEBXMLENVENTRYNAME = "jpa.eclipselink.target-database";

    protected static final String JPAPERSISTENCEUNITNAMEDEFAULTVALUE = "PU-Ejercicio";
    protected static final String JPANONJTADATASOURCEDEFAULTVALUE = "jdbc/EjercicioDocumentos";
    protected static final String JPAECLIPSELINKTARGETSERVERDEFAULTVALUE = "Glassfish";
    protected static final String JPAECLIPSELINKTARGETDATABASEDEFAULTVALUE = "SQLServer";

    private static ApplicationManager instance;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static String jpaPersistenceUnitName;
    private static String jpaNonJtaDataSource;
    private static String jpaEclipselinkTargetServer;
    private static String jpaEclipselinkTargetDatabase;

    private final AtomicInteger totalDocumentosCreados = new AtomicInteger(0);
    private final List<String> ultimasActividades = new LinkedList<>();
    private static final int MAX_ACTIVIDADES = 10;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        instance = new ApplicationManager();
        try {
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            getEnvironmentEntries(context);
            createObjects();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo inicializar JPA", e);
        }
    }

    protected void getEnvironmentEntries(Context context) throws NamingException {
        jpaPersistenceUnitName = (String) context.lookup(JPAPERSISTENCEUNITNAMEWEBXMLENVENTRYNAME);
        if (jpaPersistenceUnitName == null) {
            jpaPersistenceUnitName = JPAPERSISTENCEUNITNAMEDEFAULTVALUE;
        }
        jpaNonJtaDataSource = (String) context.lookup(JPANONJTADATASOURCEWEBXMLENVENTRYNAME);
        if (jpaNonJtaDataSource == null) {
            jpaNonJtaDataSource = JPANONJTADATASOURCEDEFAULTVALUE;
        }
        jpaEclipselinkTargetServer = (String) context.lookup(JPAECLIPSELINKTARGETSERVERWEBXMLENVENTRYNAME);
        if (jpaEclipselinkTargetServer == null) {
            jpaEclipselinkTargetServer = JPAECLIPSELINKTARGETSERVERDEFAULTVALUE;
        }
        jpaEclipselinkTargetDatabase = (String) context.lookup(JPAECLIPSELINKTARGETDATABASEWEBXMLENVENTRYNAME);
        if (jpaEclipselinkTargetDatabase == null) {
            jpaEclipselinkTargetDatabase = JPAECLIPSELINKTARGETDATABASEDEFAULTVALUE;
        }
    }

    protected void createObjects() {
        Map<String, Object> props = new HashMap<>();
        props.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, jpaNonJtaDataSource);
        props.put(PersistenceUnitProperties.TARGET_SERVER, jpaEclipselinkTargetServer);
        props.put(PersistenceUnitProperties.TARGET_DATABASE, jpaEclipselinkTargetDatabase);
        entityManagerFactory = Persistence.createEntityManagerFactory(jpaPersistenceUnitName, props);
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManager = null;
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
        instance = null;
    }

    public static ApplicationManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationManager no inicializado");
        }
        return instance;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationManager no inicializado");
        }
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        if (instance == null) {
            throw new IllegalStateException("ApplicationManager no inicializado");
        }
        return entityManager;
    }

    public synchronized void registrarDocumentoCreado(String nombre) {
        totalDocumentosCreados.incrementAndGet();
        String entry = java.time.Instant.now().toString() + " - " + nombre;
        ultimasActividades.add(0, entry);
        while (ultimasActividades.size() > MAX_ACTIVIDADES) {
            ultimasActividades.remove(ultimasActividades.size() - 1);
        }
    }

    public int getTotalDocumentosCreados() {
        return totalDocumentosCreados.get();
    }

    public List<String> getUltimasActividades() {
        return Collections.unmodifiableList(new ArrayList<>(ultimasActividades));
    }
}
