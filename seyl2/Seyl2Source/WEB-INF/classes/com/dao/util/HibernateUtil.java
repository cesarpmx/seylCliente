package com.dao.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static final ThreadLocal<EntityManager> threadSession = new ThreadLocal<>();
    private static volatile EntityManagerFactory entityFactory;

    // Inicialización perezosa (Lazy) y segura para multihilo
    public static synchronized EntityManagerFactory getFactory() {
        if (entityFactory == null || !entityFactory.isOpen()) {
            entityFactory = Persistence.createEntityManagerFactory("Argo");
        }
        return entityFactory;
    }

    public static EntityManager getEntityManager() {
        EntityManager e = threadSession.get();
        if (e == null || !e.isOpen()) {
            e = getFactory().createEntityManager();
            threadSession.set(e);
        }
        return e;
    }

    public static synchronized void closeSessionFactory() {
        try {
            if (entityFactory != null && entityFactory.isOpen()) {
                entityFactory.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityFactory = null;
        }
    }

    public static void closeSession() {
        try {
            EntityManager e = threadSession.get();
            threadSession.remove();
            if (e != null) {
                if (e.getTransaction().isActive()) {
                    e.getTransaction().rollback();
                }
                e.clear();
                e.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}