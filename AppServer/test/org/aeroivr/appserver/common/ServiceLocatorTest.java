/*
 * ServiceLocatorTest.java
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
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.createMock;
import org.aeroivr.appserver.admin.ServerAdmin;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.admin.AppServerShutdownThread;
import org.aeroivr.appserver.h323.H323Application;
import org.aeroivr.appserver.h323.OpenH323;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocatorTest extends TestCase {

    private ServiceLocator serviceLocator;

    public ServiceLocatorTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    public void testLoadAndGetInstance() {
        try {
            ServiceLocator serviceLocatorMock = createMock(
                    ServiceLocator.class);

            ServiceLocator.load(serviceLocatorMock);
            ServiceLocator currentServiceLocator = ServiceLocator.getInstance();
            assertTrue("References should be equal",
                    serviceLocatorMock == currentServiceLocator);
        } finally {
            ServiceLocator.load(serviceLocator);
        }
    }

    public void testGetServerAdmin() throws RemoteException {
        assertTrue("ServerAdmin object should not be null",
                serviceLocator.getServerAdmin() != null);
    }

    public void testGetRmiRegistry() throws RemoteException {
        assertTrue("Rmi Registry object should not be null",
                serviceLocator.getRmiRegistry(
                ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT) != null);
    }

    public void testGetH323Application() {
        assertTrue("H323 Application object should not be null",
                serviceLocator.getH323Application() != null);
    }

    public void testGetRuntime() {
        assertTrue("Runtime object should not be null",
                serviceLocator.getRuntime() != null);
    }

    public void testGetAppServerShutdownThread() {
        ServerAdmin serverAdminMock = createMock(ServerAdmin.class);
        assertTrue("AppServerShutdownThread should not be null",
                serviceLocator.getAppServerShutdownThread(
                serverAdminMock) != null);
    }

    public void testGetOpenH323() {
        assertTrue("OpenH323 should not be null",
                serviceLocator.getOpenH323() != null);
    }

    public void testGetSettings() {
        assertTrue("Settings should not be null",
                serviceLocator.getSettings() != null);
    }
}
