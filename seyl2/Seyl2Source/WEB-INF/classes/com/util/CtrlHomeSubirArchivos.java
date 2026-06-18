/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.dao.EventManager;
import com.dao.util.HibernateUtil;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Andrade
 */
public class CtrlHomeSubirArchivos extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            String strIdCliente=(request.getParameter("idCln")==null?(String)request.getAttribute("idCln"):request.getParameter("idCln"));
            List lstTipoArchivo = EventManager.getArray("SELECT c.coIdComboOpcion, c.coNombreComboOpc FROM ComboOpciones c WHERE c.coIdCombo.ccIdCombo=40 and c.coHabilitado=1 ");

            List lstArchPac = EventManager.getArray("Select a,c from ArchivoEscaneo a,ComboOpciones c where a.aTipoArchivo.coIdComboOpcion=c.coIdComboOpcion and a.aStatus=true AND a.aIdCliente="+strIdCliente +" order by a.aTipoArchivo.coIdComboOpcion,a.aIdArchivo desc");
            request.setAttribute("lstTipos", lstTipoArchivo);
            request.setAttribute("lstArchPac", lstArchPac);
            request.setAttribute("str_h", "1");
            request.setAttribute("idCln", strIdCliente);
            request.setAttribute("tipoArch", request.getParameter("tipoArch")==null?(String)request.getAttribute("tipoArch"):request.getParameter("tipoArch"));
            request.setAttribute("idPrd", request.getParameter("idPrd")==null?(String)request.getAttribute("idPrd"):request.getParameter("idPrd"));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            HibernateUtil.closeSession();
        }
        GotoPage.gotoPage("/jsp_general/subirArchivos.jsp", request, response, this.getServletContext());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
