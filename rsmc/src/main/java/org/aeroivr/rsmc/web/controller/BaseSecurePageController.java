/*
 * BaseSecurePageController.java
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
import java.net.HttpURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.MasterPageView;

/**
 * Base class for all secure page controllers.
 *
 * @author Andriy Petlyovanyy
 */
public abstract class BaseSecurePageController extends BasePageController {

    @Override
    protected void configureMasterPage(final MasterPageView masterPageView) {
        super.configureMasterPage(masterPageView);
        masterPageView.setShowMenu(true);
    }

    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        WebSecurityManager securityManager = ServiceLocator.getInstance(
                ).getWebSecurityManager(request.getSession());
        if (securityManager.isLoggedIn()) {
            pageGet(request, response);
        } else {
            response.sendError(HttpURLConnection.HTTP_FORBIDDEN);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {

        WebSecurityManager securityManager = ServiceLocator.getInstance(
                ).getWebSecurityManager(request.getSession());
        if (securityManager.isLoggedIn()) {
            pagePost(request, response);
        } else {
            response.sendError(HttpURLConnection.HTTP_FORBIDDEN);
        }
    }
}
