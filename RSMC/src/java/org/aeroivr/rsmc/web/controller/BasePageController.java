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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.render.PageRenderer;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.MasterPageView;

/**
 * Base class for all controllers.
 *
 * @author Andriy Petlyovanyy
 */
public abstract class BasePageController extends HttpServlet {
    
    private List<String> errors = new ArrayList<String>();

    protected void configureMasterPage(MasterPageView masterPageView) {
        masterPageView.setShowMenu(false);
        masterPageView.setHeader(getHeader());
    }

    protected abstract String getHeader();

    protected String getViewsFolder() {
        return getServletContext().getRealPath("/");
    }

    protected String getWavFilesFolder() {
        return getServletContext().getRealPath("/WAV");
    }

    protected void renderView(HttpServletRequest request,
            HttpServletResponse response, AbstractView view)
            throws IOException {
        
        MasterPageView masterPageView = ServiceLocator.getMasterPageView(
                getViewsFolder(), request.getContextPath());
        configureMasterPage(masterPageView);
        masterPageView.setErrors(errors);

        PageRenderer renderer = ServiceLocator.getPageRenderer(masterPageView,
                view);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(renderer.renderContent());
        out.close();
        clearErrors();
    }

    protected void setError(String errorMessage) {
        errors.add(errorMessage);
    }

    protected void clearErrors() {
        errors.clear();
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
