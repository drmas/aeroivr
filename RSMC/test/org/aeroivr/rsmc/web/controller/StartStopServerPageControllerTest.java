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

import java.lang.reflect.Method;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.controller.
        BaseTestForPageController.PageGetTestParameters;
import org.aeroivr.rsmc.web.controller.
        BaseTestForPageController.PagePostTestParameters;
import org.aeroivr.rsmc.web.view.StartStopServerView;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class StartStopServerPageControllerTest
        extends BaseTestForSecurePageController {

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

        ServiceLocator serviceLocatorMock =
                testParams.getControl().createMock(ServiceLocator.class,
                    new Method[] {ServiceLocator.class.getMethod(
                            "getAppServerAdminClient")});

        AppServerAdminClient appServerClientMock =
                testParams.getControl().createMock(AppServerAdminClient.class);

        serviceLocatorMock.getAppServerAdminClient();
        expectLastCall().andReturn(appServerClientMock).once();

        appServerClientMock.isAppServerRunning();
        expectLastCall().andReturn(running).once();

        testParams.getPrintWriterMock().print(and(contains(shouldContainOne),
                contains(shouldContainTwo)));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(serviceLocatorMock);
        testParams.getControllerMock().doGet(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPageGetWithStoppedServer() throws Exception {
        pageGetTestWithServerState(false, "startServer", "<form");
    }

    public void testPageGetWithRunningServer() throws Exception {
        pageGetTestWithServerState(true, "stopServer", "restartServer");
    }

    public void testPagePostWithStartServerCommand() throws Exception {

        PagePostTestParameters<StartStopServerPageController> testParams =
                new PagePostTestParameters<StartStopServerPageController>();
        pagePostInitTest(StartStopServerPageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        testParams.getParameters().put(StartStopServerView.START_SERVER_BUTTON,
                StartStopServerView.START_SERVER_BUTTON);

        testParams.getAppServerClientAdminMock().startAppServer();
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithStopServerCommand() throws Exception {

        PagePostTestParameters<StartStopServerPageController> testParams =
                new PagePostTestParameters<StartStopServerPageController>();
        pagePostInitTest(StartStopServerPageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        testParams.getParameters().put(StartStopServerView.STOP_SERVER_BUTTON,
                StartStopServerView.START_SERVER_BUTTON);

        testParams.getAppServerClientAdminMock().stopAppServer();
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithRestartServerCommand() throws Exception {

        PagePostTestParameters<StartStopServerPageController> testParams =
                new PagePostTestParameters<StartStopServerPageController>();
        pagePostInitTest(StartStopServerPageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        testParams.getParameters().put(
                StartStopServerView.RESTART_SERVER_BUTTON,
                StartStopServerView.START_SERVER_BUTTON);

        testParams.getAppServerClientAdminMock().stopAppServer();
        expectLastCall().once();

        testParams.getAppServerClientAdminMock().startAppServer();
        expectLastCall().once();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControl().replay();

        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

}
