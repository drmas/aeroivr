/*
 * StartStopServerPageController.java
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.view.StartStopServerView;

/**
 * Start \ Stop application server page controller.
 *
 * @author Andriy Petlyovanyy
 */
public class StartStopServerPageController extends BaseSecurePageController {

    public StartStopServerPageController() {
    }

    @Override
    protected String getHeader() {
        return "Manage application server";
    }

    @Override
    protected void pageGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        final StartStopServerView view = ServiceLocator.getInstance()
                .getStartStopServerView(getViewsFolder());
        AppServerAdminClient client;
        try {
            client = ServiceLocator.getInstance().getAppServerAdminClient();
        } catch (final Exception ex) {
            throw new ServletException("Error during connection to "
                    + "AppServer admin", ex);
        }
        view.setServerStarted(client.isAppServerRunning());
        renderView(request, response, view);
    }

    @Override
    protected void pagePost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        final StartStopServerView view = ServiceLocator.getInstance()
                .getStartStopServerView(getViewsFolder(),
                        request.getParameterMap());
        AppServerAdminClient client;
        try {
            client = ServiceLocator.getInstance().getAppServerAdminClient();
        } catch (final Exception ex) {
            throw new ServletException("Error during connection to "
                    + "AppServer admin", ex);
        }
        if (view.wasStartButtonPressed()) {
            client.startAppServer();
            view.setServerStarted(client.isAppServerRunning());
        } else {
            if (view.wasStopButtonPressed()) {
                client.stopAppServer();
                view.setServerStarted(client.isAppServerRunning());
            } else {
                if (view.wasRestartButtonPressed()) {
                    client.stopAppServer();
                    client.startAppServer();
                    view.setServerStarted(client.isAppServerRunning());
                }
            }
        }
        renderView(request, response, view);
    }

}
