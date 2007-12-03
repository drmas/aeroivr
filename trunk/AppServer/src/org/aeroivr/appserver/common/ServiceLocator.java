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

package org.aeroivr.appserver.common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.admin.ServerAdmin;
import org.aeroivr.appserver.h323.H323Application;

/**
 *
 * @author Andriy Petlyovanyy
 */
public final class ServiceLocator {

    private static volatile ServiceLocator serviceLocator =
            new ServiceLocator();

    private ServiceLocator() {
    }

    public static void load(final ServiceLocator srvLocator) {
        serviceLocator = srvLocator;
    }

    public static ServiceLocator getInstance() {
        return serviceLocator;
    }

    public ServerAdmin getServerAdmin() throws RemoteException {
        return new ServerAdmin();
    }

    public Registry getRmiRegistry(final int port) throws RemoteException {
        LocateRegistry.createRegistry(port);
        return LocateRegistry.getRegistry(port);
    }

    public H323Application getH323Application() {
        return new H323Application();
    }
}
