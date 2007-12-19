/*
 * LogonPageControllerTest.java
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

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import junit.framework.TestCase;
import javax.servlet.http.*;
import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.common.TestConstants;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.LogonView;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;
import org.easymock.classextension.IMocksControl;

/**
 * @author Andriy Petlyovanyy
 */
public class LogonPageControllerTest extends BaseTestForController {

    private final ServiceLocator serviceLocator;

    public LogonPageControllerTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    protected void tearDown() throws Exception {
        ServiceLocator.load(serviceLocator);
    }

    public void testPageGet() throws Exception {

        PageGetTestParameters<LogonPageController> testParams = 
                new PageGetTestParameters<LogonPageController>();
        pageGetInitTest(LogonPageController.class, testParams);

        testParams.printWriterMock.print(and(and(and(contains("username"),
                contains("password")), and(contains("<form"),
                contains("<input"))), contains("Please provide credentials")));
        expectLastCall().once();

        testParams.control.replay();

        testParams.controllerMock.doGet(testParams.requestMock, 
                testParams.responseMock);

        testParams.control.verify();
    }

    private void checkPagePost(
            final PagePostTestParameters<LogonPageController> testParams,
            final boolean validationResult) throws Exception {
        
        pagePostInitTest(LogonPageController.class, testParams);

        testParams.parameters.put(LogonView.USERNAME,
                AppServerAdminConstants.ADMIN_USERNAME);
        testParams.parameters.put(LogonView.PASSWORD,
                AppServerAdminConstants.ADMIN_USERNAME);
        testParams.parameters.put(LogonView.LOGON_BUTTON,
                LogonView.LOGON_BUTTON);

        testParams.appServerClientAdminMock.areCredentialsValid(
                eq(AppServerAdminConstants.ADMIN_USERNAME),
                eq(AppServerAdminConstants.ADMIN_USERNAME));
        expectLastCall().andReturn(validationResult).once();
    }

    public void testPagePostWithSuccessfulLogon() throws Exception {

        PagePostTestParameters<LogonPageController> testParams =
                new PagePostTestParameters<LogonPageController>();
        checkPagePost(testParams, true);

        testParams.responseMock.sendRedirect(eq("startStopServer.html"));
        expectLastCall().once();

        testParams.control.replay();

        ServiceLocator.load(testParams.serviceLocatorMock);
        testParams.controllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }

    public void testPagePostWithUnsuccessfulLogon() throws Exception {

        PagePostTestParameters<LogonPageController> testParams =
                new PagePostTestParameters<LogonPageController>();
        checkPagePost(testParams, false);

        testParams.controllerMock.setError(eq(
                "Invalid credentials supplied"));
        expectLastCall().once();

        testParams.responseMock.getWriter();
        expectLastCall().andReturn(testParams.printWriterMock).once();

        testParams.printWriterMock.print(and(and(contains("username"),
                contains("password")), and(contains("<form"),
                contains("<input"))));
        expectLastCall().once();

        testParams.control.replay();

        ServiceLocator.load(testParams.serviceLocatorMock);
        testParams.controllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }
}
