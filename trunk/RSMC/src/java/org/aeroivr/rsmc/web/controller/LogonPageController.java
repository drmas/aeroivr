/*
 * LogonPageController.java
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.*;
import javax.servlet.http.*;
import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.LogonView;

/**
 * Logon page controller.
 *
 * @author Andriy Petlyovanyy
 */
public class LogonPageController extends BasePageController {

    protected void pageGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        final LogonView view = ServiceLocator.getInstance().getLogonView(
                getViewsFolder());
        view.setUsername(AppServerAdminConstants.ADMIN_USERNAME);
        renderView(request, response, view);
    }

    protected void pagePost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        final LogonView logonView = ServiceLocator.getInstance().getLogonView(
                getViewsFolder(), request.getParameterMap());
        boolean areCredentialsValid = false;
        AppServerAdminClient appServerClient;
        try {
            appServerClient = ServiceLocator.getInstance().getAppServerAdminClient();
        } catch (Exception ex) {
            throw new ServletException("Error occured durring connection to " +
                    "AppServer admin", ex);
        }

        if (logonView.wasLogonButtonPressed()) {
            if (appServerClient.areCredentialsValid(logonView.getUsername(),
                    logonView.getPassword())) {

                areCredentialsValid = true;
                WebSecurityManager webSecurity = ServiceLocator.getInstance(
                        ).getWebSecurityManager(request.getSession());
                webSecurity.setLoggedInUsername(logonView.getUsername());

                response.sendRedirect("startStopServer.html");
            } else {
                setError("Invalid credentials supplied");
            }
        }

        if (!areCredentialsValid) {
            renderView(request, response, logonView);
        }
    }

    protected String getHeader() {
        return "Please provide credentials";
    }
}
