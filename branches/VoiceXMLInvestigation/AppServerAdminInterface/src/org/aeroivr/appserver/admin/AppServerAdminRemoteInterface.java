/*
 * ServerAdminRemoteInterface.java
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
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote access interface for Application Server Admin.
 *
 * @author Andriy Petlyovanyy
 */
public interface AppServerAdminRemoteInterface extends Remote {

    boolean areCredentialsValid(final String username, final String password)
        throws RemoteException;

    boolean isAppServerRunning() throws RemoteException;

    void startAppServer() throws RemoteException;

    void stopAppServer() throws RemoteException;

    void changeAdminPassword(final String newPassword) throws IOException;

    void setWavFileName(final String fileName) throws IOException;
}
