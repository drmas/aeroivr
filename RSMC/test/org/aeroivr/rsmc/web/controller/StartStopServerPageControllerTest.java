/*
 * StartStopServerPageControllerTest.java
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

package org.aeroivr.rsmc.web.controller;

import junit.framework.TestCase;
import junit.framework.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class StartStopServerPageControllerTest extends BaseTestForController {

    public StartStopServerPageControllerTest(final String testName) {
        super(testName);
    }

    public void testGetHeader() {
        StartStopServerPageController pageController =
                new StartStopServerPageController();
        assertEquals("Header should contain this text",
                pageController.getHeader(), "Manage application server");
    }

    private void pageGetTestWithServerState(final boolean running,
            final String shouldContainOne, final String shouldContainTwo)
                throws Exception {

        PageGetTestParameters<StartStopServerPageController> testParams =
                new PageGetTestParameters<StartStopServerPageController>();

        pageGetInitTest(StartStopServerPageController.class, testParams);

        AppServerAdminClient appServerClientMock =
                testParams.control.createMock(AppServerAdminClient.class);

        appServerClientMock.isAppServerRunning();
        expectLastCall().andReturn(running).once();

        testParams.printWriterMock.print(and(contains(shouldContainOne),
                contains(shouldContainTwo)));
        expectLastCall().once();

        testParams.control.replay();

        testParams.controllerMock.doGet(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }

    public void testPageGetWithStoppedServer() throws Exception {
        pageGetTestWithServerState(false, "startServer", "<form");
    }

    public void testPageGetWithRunningServer() throws Exception {
        pageGetTestWithServerState(true, "stopServer", "restartServer");
    }

    public void testPagePost() throws Exception {
        fail();
    }

}
