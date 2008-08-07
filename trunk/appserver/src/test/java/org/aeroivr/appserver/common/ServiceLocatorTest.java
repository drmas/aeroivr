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

import static org.easymock.classextension.EasyMock.createMock;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.appserver.admin.ServerAdmin;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocatorTest extends TestCase {

    private final ServiceLocator serviceLocator;

    public ServiceLocatorTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    public void testLoadAndGetInstance() {
        try {
            final ServiceLocator serviceLocatorMock = createMock(
                    ServiceLocator.class);

            ServiceLocator.load(serviceLocatorMock);
            final ServiceLocator currentServiceLocator = ServiceLocator
                    .getInstance();
            assertTrue("References should be equal",
                    serviceLocatorMock == currentServiceLocator);
        } finally {
            ServiceLocator.load(serviceLocator);
        }
    }

    public void testGetServerAdmin() throws RemoteException {
        assertNotNull("ServerAdmin object should not be null", serviceLocator
                .getServerAdmin());
    }

    public void testGetRmiRegistry() throws RemoteException {
        assertNotNull("Rmi Registry object should not be null", serviceLocator
                .getRmiRegistry(AppServerConstants.APP_SERVER_ADMIN_RMI_PORT));
    }

    public void testGetRuntime() {
        assertNotNull("Runtime object should not be null", serviceLocator
                .getRuntime());
    }

    public void testGetAppServerShutdownThread() {
        final ServerAdmin serverAdminMock = createMock(ServerAdmin.class);
        assertNotNull("AppServerShutdownThread should not be null",
                serviceLocator.getAppServerShutdownThread(serverAdminMock));
    }

    public void testGetSettings() {
        assertNotNull("Settings should not be null", serviceLocator
                .getSettings());
    }

      public void testGetFileAsInputStream() throws IOException {
        File tempFile = File.createTempFile("tmp", ".tmp");
        try {
            assertNotNull("FileStream should be created", serviceLocator
                    .getFileAsInputStream(tempFile.getAbsolutePath()));
        } finally {
            tempFile.delete();
        }
    }

    public void testGetProperties() {
        assertNotNull("Properties object could not be null", serviceLocator
                .getProperties());
    }

    public void testGetFileAsOutputStream() throws IOException {
        File tempFile = File.createTempFile("tmp", ".tmp");
        try {
            assertNotNull("FileStream should be created", serviceLocator
                    .getFileAsOutputStream(tempFile.getAbsolutePath()));
        } finally {
            tempFile.delete();
        }
    }

    public void testGetFile() throws IOException {
        File tempFile = File.createTempFile("tmp", ".tmp");
        try {
            assertNotNull("FileStream should be created", serviceLocator
                    .getFile(tempFile.getAbsolutePath()));
        } finally {
            tempFile.delete();
        }
    }
}
