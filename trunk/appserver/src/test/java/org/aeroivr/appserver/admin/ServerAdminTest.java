/*
 * ServerAdminTest.java
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

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createNiceControl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.aeroivr.appserver.common.AbstractServiceLocatorTest;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.common.Settings;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServerAdminTest extends AbstractServiceLocatorTest {

    // private IMocksControl control;
    // private H323Application h323AppMock;
    // private ServiceLocator serviceLocator;
    // private ServiceLocator serviceLocatorMock;
    //

     public ServerAdminTest(final String testName) {
         super(testName);
     }

    // protected void setUp() throws Exception {
    // control = createStrictControl();
    // h323AppMock = control.createMock(H323Application.class);
    // serviceLocatorMock = control.createMock(
    // ServiceLocator.class, new Method[] {
    // ServiceLocator.class.getMethod("getH323Application")});
    // }
    //
    // private void startApplicationServerSequence() {
    //
    // serviceLocatorMock.getH323Application();
    // expectLastCall().andReturn(h323AppMock).once();
    //
    // h323AppMock.start();
    // expectLastCall().once();
    //
    // }
    //
    // public void testStartAppServer() throws RemoteException {
    //
    // startApplicationServerSequence();
    //
    // control.replay();
    //
    // ServiceLocator.load(serviceLocatorMock);
    // final ServerAdmin serverAdmin = new ServerAdmin();
    // serverAdmin.startAppServer();
    //
    // control.verify();
    //
    // assertTrue("Port number check failed", serverAdmin.toString().indexOf(
    // Integer.toString(
    // AppServerAdminConstants.APP_SERVER_ADMIN_RMI_PORT)) >= 0);
    // }
    //
    // public void testStartAppServer2() throws RemoteException {
    //
    // startApplicationServerSequence();
    //
    // control.replay();
    //
    // ServiceLocator.load(serviceLocatorMock);
    // final ServerAdmin serverAdmin = new ServerAdmin();
    // serverAdmin.startAppServer();
    // serverAdmin.startAppServer();
    //
    // control.verify();
    //
    // assertTrue("Port number check failed", serverAdmin.toString().indexOf(
    // Integer.toString(
    // AppServerAdminConstants.APP_SERVER_ADMIN_RMI_PORT)) >= 0);
    // }
    //
    // public void testStopAppServer() throws RemoteException {
    //
    // startApplicationServerSequence();
    //
    // h323AppMock.stop();
    // expectLastCall().once();
    //
    // control.replay();
    //
    // ServiceLocator.load(serviceLocatorMock);
    // final ServerAdmin serverAdmin = new ServerAdmin();
    // serverAdmin.startAppServer();
    // serverAdmin.stopAppServer();
    //
    // control.verify();
    // }
    //
    // public void testStopAppServer2() throws RemoteException {
    //
    // control.replay();
    //
    // ServiceLocator.load(serviceLocatorMock);
    // final ServerAdmin serverAdmin = new ServerAdmin();
    // serverAdmin.stopAppServer();
    //
    // control.verify();
    // }
    //
    public void testAreCredentialsValid() throws NoSuchMethodException,
            RemoteException {

        final IMocksControl niceControl = createNiceControl();
        final ServiceLocator srvLocatorMock = niceControl.createMock(
                ServiceLocator.class, new Method[] { ServiceLocator.class
                        .getMethod("getSettings") });
        final Settings settingsMock = niceControl.createMock(Settings.class);
        final String adminPassword = "PA55W0RD";

        srvLocatorMock.getSettings();
        expectLastCall().andReturn(settingsMock).atLeastOnce();

        settingsMock.getAdminPassword();
        expectLastCall().andReturn(adminPassword).atLeastOnce();

        niceControl.replay();

        ServiceLocator.load(srvLocatorMock);
        final ServerAdmin serverAdmin = new ServerAdmin();
        assertTrue("Credentials should be valid", serverAdmin
                .areCredentialsValid(AppServerConstants.ADMIN_USERNAME,
                        adminPassword));
        assertFalse("Credentials should not be valid", serverAdmin
                .areCredentialsValid(AppServerConstants.ADMIN_USERNAME + "b",
                        adminPassword));
        assertFalse("Credentials should not be valid", serverAdmin
                .areCredentialsValid(AppServerConstants.ADMIN_USERNAME,
                        adminPassword + "c"));

        niceControl.verify();
    }

    // public void testIsAppServerRunning() throws RemoteException {
    //
    // startApplicationServerSequence();
    //
    // h323AppMock.stop();
    // expectLastCall().once();
    //
    // control.replay();
    //
    // ServiceLocator.load(serviceLocatorMock);
    // final ServerAdmin serverAdmin = new ServerAdmin();
    //
    // serverAdmin.startAppServer();
    // assertTrue("Server should be running",
    // serverAdmin.isAppServerRunning());
    //
    // serverAdmin.stopAppServer();
    // assertFalse("Server should be stopped",
    // serverAdmin.isAppServerRunning());
    //
    // control.verify();
    // }
    //
    public void testChangeAdminPassword() throws NoSuchMethodException,
            IOException {

        final IMocksControl niceControl = createNiceControl();
        final ServiceLocator srvLocatorMock = niceControl.createMock(
                ServiceLocator.class, new Method[] { ServiceLocator.class
                        .getMethod("getSettings") });
        final Settings settingsMock = niceControl.createMock(Settings.class);
        final String newAdminPassword = "PA55W0RD";

        srvLocatorMock.getSettings();
        expectLastCall().andReturn(settingsMock).atLeastOnce();

        settingsMock.setAdminPassword(eq(newAdminPassword));
        expectLastCall().atLeastOnce();

        niceControl.replay();

        ServiceLocator.load(srvLocatorMock);
        final ServerAdmin serverAdmin = new ServerAdmin();
        serverAdmin.changeAdminPassword(newAdminPassword);

        niceControl.verify();
    }

    public void testSetVoiceXMLApplication() throws NoSuchMethodException,
            IOException {

        final File tempWarFile = File.createTempFile("test", "test");
        final File destWarFile = new File(System.getProperty("java.io.tmpdir"),
                AppServerConstants.VOICEXML_APP_NAME + ".war");
        if (destWarFile.exists()) {
            destWarFile.delete();
        }

        final ServerAdmin serverAdmin = new ServerAdmin();
        serverAdmin.setVoiceXMLApplication(destWarFile.getParent(),
                tempWarFile.getPath());

        assertFalse(tempWarFile.exists());
        assertTrue(destWarFile.exists());
        destWarFile.delete();
    }
}
