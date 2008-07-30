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
import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.common.Settings;

/**
 * Remote administrative object to manage H323 application.
 *
 * @author Andriy Petlyovanyy
 */
public class ServerAdmin extends UnicastRemoteObject {
//        implements AppServerAdminRemoteInterface {
//
//    private H323Application h323Application;
//
    public ServerAdmin() throws RemoteException {
        super(AppServerConstants.APP_SERVER_ADMIN_RMI_PORT);
    }

    public void startAppServer() throws RemoteException {
//        if (null == h323Application) {
//            h323Application = ServiceLocator.getInstance().getH323Application();
//            h323Application.start();
        }
//    }
//
    public void stopAppServer() throws RemoteException {
//        if (null != h323Application) {
//            h323Application.stop();
//            h323Application = null;
//        }
    }
//
//    public boolean areCredentialsValid(final String username,
//            final String password) throws RemoteException {
//
//        if (0 == AppServerAdminConstants.ADMIN_USERNAME.compareTo(username)) {
//            final String adminPassword = ServiceLocator.getInstance(
//                    ).getSettings().getAdminPassword();
//
//            if (0 == adminPassword.compareTo(password)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isAppServerRunning() throws RemoteException {
//
//        return (null != h323Application);
//    }
//
//    public void changeAdminPassword(final String newPassword)
//        throws IOException {
//
//        final Settings settings = ServiceLocator.getInstance().getSettings();
//        settings.setAdminPassword(newPassword);
//        settings.saveSettings();
//   }
//
//    public void setWavFileName(final String fileName)
//        throws IOException {
//
//        final Settings settings = ServiceLocator.getInstance().getSettings();
//        settings.setWavFileName(fileName);
//        settings.saveSettings();
//    }
}
