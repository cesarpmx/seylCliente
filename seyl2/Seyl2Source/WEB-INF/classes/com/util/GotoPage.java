/*
 * GotoPage.java
 *
 * Created on 20 de agosto de 2007, 06:43 PM
 */
package com.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jmarquez
 * @version
 */
public class GotoPage {

    public static void gotoPage(String address, HttpServletRequest request, HttpServletResponse response, ServletContext sctx)
            throws ServletException, IOException {
        RequestDispatcher disp = sctx.getRequestDispatcher(address);
        disp.forward(request, response);
    }
}
