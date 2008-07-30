/*
 * ChangePasswordPageController.java
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.ChangePasswordView;

/**
 * Page controller for change administrator password view.
 *
 * @author Andriy Petlyovanyy
 */
public class ChangePasswordPageController extends BaseSecurePageController {

    protected String getHeader() {
        return "Change administrator's password";
    }

    protected void pageGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        final ChangePasswordView view = ServiceLocator.getInstance(
                ).getChangePasswordView(getViewsFolder());
        renderView(request, response, view);
    }

    protected void pagePost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        final ChangePasswordView view = ServiceLocator.getInstance(
                ).getChangePasswordView(getViewsFolder(),
                request.getParameterMap());
        if (view.wasChangeButtonPressed()) {
            WebSecurityManager securityManager = ServiceLocator.getInstance(
                    ).getWebSecurityManager(request.getSession());
            AppServerAdminClient client;
            try {
                client = ServiceLocator.getInstance().getAppServerAdminClient();
            } catch (Exception ex) {
                throw new ServletException("Error during connection to " +
                        "AppServer admin", ex);
            }

            if (((null == view.getNewPassword()) &&
                    (null == view.getConfirmPassword())) ||
                    ((null != view.getNewPassword()) &&
                        (0 == view.getNewPassword().compareTo(
                            view.getConfirmPassword())))) {

                if (client.areCredentialsValid(
                        securityManager.getLoggedInUsername(),
                        view.getOldPassword())) {
                    client.changeAdminPassword(view.getNewPassword());

                } else {
                    setError("Please provide correct old password");
                }
            } else {
                setError("Confirm password should match new password");
            }
        }
        renderView(request, response, view);
    }
}
