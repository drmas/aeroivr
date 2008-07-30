/*
 * AppServerAdminClientTest.java
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

import java.rmi.Remote;
import junit.framework.TestCase;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.admin.AppServerInterface;
import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.rsmc.common.ServiceLocator;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.expectLastCall;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class AppServerAdminClientTest extends TestCase {

    private ServiceLocator serviceLocator = ServiceLocator.getInstance();
    private IMocksControl control = createNiceControl();
    private ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class);
    private Registry registryMock = control.createMock(Registry.class);
    private AppServerInterface appServerInterfaceMock =
                control.createMock(AppServerInterface.class);

    public AppServerAdminClientTest(final String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        serviceLocatorMock.getRmiRegistry(eq(
                AppServerConstants.APP_SERVER_ADMIN_RMI_PORT));
        expectLastCall().andReturn(registryMock).atLeastOnce();

        registryMock.lookup(eq(
                AppServerConstants.APP_SERVER_ADMIN_RMI_NAME));
        expectLastCall().andReturn((Remote) appServerInterfaceMock
                ).atLeastOnce();
    }

    public void testAreCredentialsValid()
        throws RemoteException, NotBoundException {

        final String username = "UsrNM";
        final String password = "pwD";

        appServerInterfaceMock.areCredentialsValid(eq(username),
                eq(password));
        expectLastCall().andReturn(true).atLeastOnce();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.areCredentialsValid(username, password);
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }

    public void testIsAppServerRunning()
        throws RemoteException, NotBoundException {

        appServerInterfaceMock.isAppServerRunning();
        expectLastCall().andReturn(true).atLeastOnce();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.isAppServerRunning();
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }

    public void testStartAppServer()
        throws RemoteException, NotBoundException {

        appServerInterfaceMock.startAppServer();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.startAppServer();
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }

    public void testStopAppServer()
        throws RemoteException, NotBoundException {

        appServerInterfaceMock.stopAppServer();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.stopAppServer();
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }

    public void testChangeAdminPassword() throws Exception {

        final String newAdminPassword = "newPWWD";

        appServerInterfaceMock.changeAdminPassword(eq(newAdminPassword));

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.changeAdminPassword(newAdminPassword);
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }

    public void testSetWavFileName() throws Exception {

        final String wavFileName = "tteemp.wav";
        appServerInterfaceMock.setWavFileName(eq(wavFileName));

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        try {
            AppServerAdminClient client = new AppServerAdminClient();
            client.setWavFileName(wavFileName);
        } finally {
            ServiceLocator.load(serviceLocator);
        }

        control.verify();
    }
}
