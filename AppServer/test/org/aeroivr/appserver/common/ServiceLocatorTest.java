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

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.createMock;
import org.aeroivr.appserver.admin.ServerAdmin;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.admin.AppServerShutdownThread;
import org.aeroivr.appserver.h323.H323Application;
import org.aeroivr.appserver.h323.OpenH323;
import junit.framework.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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
        assertNotNull("ServerAdmin object should not be null",
                serviceLocator.getServerAdmin());
    }

    public void testGetRmiRegistry() throws RemoteException {
        assertNotNull("Rmi Registry object should not be null",
                serviceLocator.getRmiRegistry(
                ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT));
    }

    public void testGetH323Application() {
        assertNotNull("H323 Application object should not be null",
                serviceLocator.getH323Application());
    }

    public void testGetRuntime() {
        assertNotNull("Runtime object should not be null",
                serviceLocator.getRuntime());
    }

    public void testGetAppServerShutdownThread() {
        ServerAdmin serverAdminMock = createMock(ServerAdmin.class);
        assertNotNull("AppServerShutdownThread should not be null",
                serviceLocator.getAppServerShutdownThread(
                serverAdminMock));
    }

    public void testGetOpenH323() {
        assertNotNull("OpenH323 should not be null",
                serviceLocator.getOpenH323());
    }

    public void testGetSettings() {
        assertNotNull("Settings should not be null",
                serviceLocator.getSettings());
    }

    public void testGetFileAsInputStream() throws IOException {
        File tempFile = File.createTempFile("tmp", ".tmp");
        try {
            assertNotNull("FileStream should be created",
                    serviceLocator.getFileAsInputStream(
                        tempFile.getAbsolutePath()));
        } finally {
            tempFile.delete();
        }
    }

    public void testGetProperties() {
        assertNotNull("Properties object could not be null",
                serviceLocator.getProperties());
    }

    public void testGetFileAsOutputStream() throws IOException {
        File tempFile = File.createTempFile("tmp", ".tmp");
        try {
            assertNotNull("FileStream should be created",
                    serviceLocator.getFileAsOutputStream(
                        tempFile.getAbsolutePath()));
        } finally {
            tempFile.delete();
        }
    }
}
