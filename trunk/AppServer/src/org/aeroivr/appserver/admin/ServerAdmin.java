/*
 * ServerAdmin.java
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

package org.aeroivr.appserver.admin;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.common.Settings;
import org.aeroivr.appserver.h323.H323Application;

/**
 * Remote administrative object to manage H323 application.
 *
 * @author Andriy Petlyovanyy
 */
public class ServerAdmin extends UnicastRemoteObject
        implements AppServerAdminRemoteInterface {

    private H323Application h323Application;

    public ServerAdmin() throws RemoteException {
        super(AppServerAdminConstants.APP_SERVER_ADMIN_RMI_PORT);
    }

    public void startAppServer() {
        if (null == h323Application) {
            h323Application = ServiceLocator.getInstance().getH323Application();
            h323Application.start();
        }
    }

    public void stopAppServer() {
        if (null != h323Application) {
            h323Application.stop();
            h323Application = null;
        }
    }

    public boolean areCredentialsValid(final String username,
            final String password) {

        if (0 == AppServerAdminConstants.ADMIN_USERNAME.compareTo(username)) {
            final String adminPassword = ServiceLocator.getInstance(
                    ).getSettings().getAdminPassword();

            if (0 == adminPassword.compareTo(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAppServerRunning() {

        return (null != h323Application);
    }

    public void changeAdminPassword(final String newPassword)
        throws IOException {

        final Settings settings = ServiceLocator.getInstance().getSettings();
        settings.setAdminPassword(newPassword);
        settings.saveSettings();
   }

    public void setWavFileName(final String fileName)
        throws IOException {

        final Settings settings = ServiceLocator.getInstance().getSettings();
        settings.setWavFileName(fileName);
        settings.saveSettings();
    }
}
