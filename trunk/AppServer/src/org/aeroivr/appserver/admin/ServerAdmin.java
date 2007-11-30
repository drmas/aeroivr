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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.aeroivr.appserver.common.ApplicationConstants;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.h323.H323Application;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServerAdmin extends UnicastRemoteObject {
    
    private H323Application h323Application;
    
    public ServerAdmin() throws RemoteException {
        super(ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT);
    }
    
    void startApplicationServer() {
        if (null == h323Application) {
            h323Application = ServiceLocator.getInstance().getH323Application();
            h323Application.initialize();
            h323Application.start();
        }
    }
}
