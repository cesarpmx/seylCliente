package com.control.usuario;

import com.dao.util.HibernateUtil;
import com.util.GotoPage;
import java.io.IOException;
import java.util.Enumeration;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CtrlCerrarSesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        try {
            if (session != null && session.getAttribute("usuario") != null) {
                setBitacoraSalida(session);
                Enumeration<String> enume = session.getAttributeNames();
                while (enume.hasMoreElements()) {
                    session.removeAttribute(enume.nextElement());
                }
                session.invalidate();
            } else if (session != null) {
                session.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
            GotoPage.gotoPage("/jsp_general/cerrar.jsp", request, response, getServletContext());
        }
    }

    public void setBitacoraSalida(HttpSession session) {
        // CORRECCIÓN: Estructura Java limpia basada en el flujo del bytecode anterior
        if (session != null && session.getAttribute("idAcceso") != null) {
            try {
                // Aquí puedes agregar la lógica para actualizar la fecha de salida de la bitácora si lo requieres
                // de momento cerramos la sesión de JPA limpiamente.
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                HibernateUtil.closeSession();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}