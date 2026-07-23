/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.util;

import com.dao.util.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Inicializa la fábrica de JPA al arrancar o recargar la aplicación
        HibernateUtil.getEntityManager();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // OBLIGATORIO: Libera los recursos de JPA y elimina las referencias
        // del ClassLoader viejo cuando GlassFish recarga la aplicación.
        HibernateUtil.closeSessionFactory();
    }
}