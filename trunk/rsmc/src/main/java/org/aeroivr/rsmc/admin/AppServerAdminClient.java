/*
 * AppServerAdminClient.java
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

package org.aeroivr.rsmc.admin;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.appserver.admin.AppServerInterface;
import org.aeroivr.rsmc.common.ServiceLocator;

/**
 * Client of the application server.
 *
 * @author Andriy Petlyovanyy
 */
public class AppServerAdminClient {

    private final AppServerInterface remoteObject;

    public AppServerAdminClient() throws RemoteException, NotBoundException {

        final Registry registry = ServiceLocator.getInstance().getRmiRegistry(
                AppServerConstants.APP_SERVER_ADMIN_RMI_PORT);
        remoteObject = (AppServerInterface) registry
                .lookup(AppServerConstants.APP_SERVER_ADMIN_RMI_NAME);
    }

    public boolean areCredentialsValid(final String username,
            final String password) throws RemoteException {

        return remoteObject.areCredentialsValid(username, password);
    }

    public boolean isAppServerRunning() throws RemoteException {

        return remoteObject.isAppServerRunning();
    }

    public void startAppServer() throws RemoteException {
        remoteObject.startAppServer();
    }

    public void stopAppServer() throws RemoteException {
        remoteObject.stopAppServer();
    }

    public void changeAdminPassword(final String newPassword)
            throws IOException {
        remoteObject.changeAdminPassword(newPassword);
    }

    public void setVoiceXMLApplication(final String tempWarFileName)
            throws IOException {

        remoteObject.setVoiceXMLApplication(tempWarFileName);
    }
}
