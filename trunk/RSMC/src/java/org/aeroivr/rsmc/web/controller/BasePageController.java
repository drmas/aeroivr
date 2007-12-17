/*
 * BasePageController.java
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.aeroivr.rsmc.web.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.aeroivr.rsmc.web.view.AbstractView;

/**
 * Base class for all controllers.
 *
 * @author Andriy Petlyovanyy
 */
public abstract class BasePageController extends HttpServlet {
    
    protected String getViewsFolder() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    protected void renderView(HttpServletRequest request, 
            HttpServletResponse response, AbstractView view) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected abstract void pageGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    protected abstract void pagePost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        pageGet(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        pagePost(request, response);
    }
}
