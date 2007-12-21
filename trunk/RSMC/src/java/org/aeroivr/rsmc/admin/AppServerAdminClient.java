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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.admin.AppServerAdminRemoteInterface;
import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.rsmc.common.ServiceLocator;

/**
 * Client of the application server.
 *
 * @author Andriy Petlyovanyy
 */
public class AppServerAdminClient {
    
    private AppServerAdminRemoteInterface remoteObject;
    
    public AppServerAdminClient() throws RemoteException, NotBoundException {

        Registry registry = ServiceLocator.getInstance().getRmiRegistry(
                AppServerAdminConstants.APP_SERVER_ADMIN_RMI_PORT);
	remoteObject = (AppServerAdminRemoteInterface) registry.lookup(
                            AppServerAdminConstants.APP_SERVER_ADMIN_RMI_NAME);
    }

    public boolean areCredentialsValid(final String username, 
            final String password) {
        
        return remoteObject.areCredentialsValid(username, password);
    }

    public boolean isAppServerRunning() {
        
        return remoteObject.isAppServerRunning();
    }
    
    public void startAppServer() {
        remoteObject.startAppServer();
    }

    public void stopAppServer() {
        remoteObject.stopAppServer();
    }
    
    public void changeAdminPassword(final String newPassword) {
        remoteObject.changeAdminPassword(newPassword);
    }

    public void setWavFileName(final String fileName) {
        remoteObject.setWavFileName(fileName);
    }
}
