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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    protected void configureMasterPage(final MasterPageView masterPageView) {
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

    protected void renderView(final HttpServletRequest request,
            final HttpServletResponse response, final AbstractView view)
            throws IOException {

        MasterPageView masterPageView = ServiceLocator.getInstance(
                ).getMasterPageView(getViewsFolder(), request.getContextPath());
        configureMasterPage(masterPageView);
        masterPageView.setErrors(errors);

        PageRenderer renderer = ServiceLocator.getInstance().getPageRenderer(
                masterPageView, view);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(renderer.renderContent());
        out.close();
        clearErrors();
    }

    protected void setError(final String errorMessage) {
        errors.add(errorMessage);
    }

    protected void clearErrors() {
        errors.clear();
    }

    protected abstract void pageGet(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException;

    protected abstract void pagePost(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        pageGet(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        pagePost(request, response);
    }
}
