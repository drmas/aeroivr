/*
 * AppServerShutdownThreadTest.java
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

import junit.framework.TestCase;
import org.easymock.IMocksControl;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expectLastCall;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class AppServerShutdownThreadTest extends TestCase {

    public AppServerShutdownThreadTest(final String testName) {
        super(testName);
    }

    public void testRun() throws InterruptedException {
        IMocksControl control = createStrictControl();
        ServerAdmin serverAdminMock = control.createMock(ServerAdmin.class);

        serverAdminMock.stopAppServer();
        expectLastCall().once();

        control.replay();

        AppServerShutdownThread appServerShutdownThread =
                new AppServerShutdownThread(serverAdminMock);
        appServerShutdownThread.start();
        appServerShutdownThread.join();

        control.verify();
    }

}
