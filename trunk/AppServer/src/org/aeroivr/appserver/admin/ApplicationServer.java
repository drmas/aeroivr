/*
 * ApplicationServer.java
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

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.common.ApplicationConstants;
import org.aeroivr.appserver.common.ServiceLocator;

/**
 * Entry point of application. RMI Server Admin object is created here and
 * application server is starting.
 *
 * @author Andriy Petlyovanyy
 */
public final class ApplicationServer {

    public static void main(final String[] args) throws RemoteException {
        final Registry registry = ServiceLocator.getInstance().getRmiRegistry(
                ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT);
        final ServerAdmin serverAdmin =
                ServiceLocator.getInstance().getServerAdmin();
        registry.rebind(ApplicationConstants.APP_SERVER_ADMIN_RMI_NAME,
                serverAdmin);
        serverAdmin.startApplicationServer();

        final Runtime runtime = ServiceLocator.getInstance(
                ).getRuntime();
        final AppServerShutdownThread appShutdownThread =
                ServiceLocator.getInstance().getAppServerShutdownThread(
                serverAdmin);
        runtime.addShutdownHook(appShutdownThread);
    }
}
