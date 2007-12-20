/*
 * ServiceLocator.java
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

package org.aeroivr.rsmc.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.web.render.PageRenderer;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.LogonView;
import org.aeroivr.rsmc.web.view.MasterPageView;
import org.aeroivr.rsmc.web.view.StartStopServerView;

/**
 * Service Locator for all classes.
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocator {

    private static volatile ServiceLocator instance =
            new ServiceLocator();

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        return instance;
    }

    public static void load(final ServiceLocator value) {
        instance = value;
    }

    public LogonView getLogonView(final String viewsFolder) {
        return new LogonView(viewsFolder);
    }

    public WebSecurityManager getWebSecurityManager(final HttpSession session) {
        return new WebSecurityManager(session);
    }

    public AppServerAdminClient getAppServerAdminClient() {
        return new AppServerAdminClient();
    }

    public static MasterPageView getMasterPageView(final String viewsFolder,
            final String rootDirUrl) {

        return new MasterPageView(viewsFolder, rootDirUrl);
    }

    public static PageRenderer getPageRenderer(
            final MasterPageView masterPageView,
            final AbstractView view) {

        return new PageRenderer(masterPageView, view);
    }

    public BufferedReader getBufferedReaderForFile(final String fileName)
        throws FileNotFoundException {

        return new BufferedReader(new FileReader(fileName));
    }

    public LogonView getLogonView(final String viewsFolder,
            final Map parameters) {

        return new LogonView(viewsFolder, parameters);
    }

    public StartStopServerView getStartStopServerView(
            final String viewsFolder) {

        return new StartStopServerView(viewsFolder);
    }

    public StartStopServerView getStartStopServerView(final String viewsFolder,
            final Map parameters) {

        return new StartStopServerView(viewsFolder, parameters);
    }
}
