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
public class LogonPageControllerTest extends TestCase {

    private final ServiceLocator serviceLocator;

    public LogonPageControllerTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    protected void tearDown() throws Exception {
        ServiceLocator.load(serviceLocator);
    }

    public void testPageGet() throws Exception {

        final IMocksControl control = createNiceControl();
        final HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        final HttpServletResponse responseMock = control.createMock(
                HttpServletResponse.class);
        final PrintWriter printWriterMock = control.createMock(
                PrintWriter.class);
        final LogonPageController logonPageControllerMock = control.createMock(
                LogonPageController.class,
                new Method[] {
                    BasePageController.class.getDeclaredMethod(
                            "getViewsFolder"),
                    BasePageController.class.getDeclaredMethod("clearErrors")});

        logonPageControllerMock.getViewsFolder();
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        requestMock.getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).atLeastOnce();

        responseMock.getWriter();
        expectLastCall().andReturn(printWriterMock).once();

        printWriterMock.print(and(and(and(contains("username"),
                contains("password")), and(contains("<form"),
                contains("<input"))), contains("Please provide credentials")));
        expectLastCall().once();

        control.replay();

        logonPageControllerMock.doGet(requestMock, responseMock);

        control.verify();
    }

    class ParametersForTestPagePost {

        final HashMap parameters = new HashMap();
        final IMocksControl control = createNiceControl();
        final HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        final HttpServletResponse responseMock = control.createMock(
                HttpServletResponse.class);
        final HttpSession sessionMock = control.createMock(HttpSession.class);
        final PrintWriter printWriterMock = control.createMock(
                PrintWriter.class);
        final LogonPageController logonPageControllerMock = control.createMock(
                LogonPageController.class,
                new Method[] {
                    BasePageController.class.getDeclaredMethod(
                            "getViewsFolder"),
                    BasePageController.class.getDeclaredMethod("clearErrors"),
                    BasePageController.class.getDeclaredMethod("setError",
                            String.class)});
        final ServiceLocator serviceLocatorMock;
        final AppServerAdminClient appServerClientAdminMock = control.createMock(
                AppServerAdminClient.class);

        public ParametersForTestPagePost() throws NoSuchMethodException {

            serviceLocatorMock = control.createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getAppServerAdminClient")});
        }
    }

    private void checkPagePost(final ParametersForTestPagePost testParams,
            final boolean validationResult) throws Exception {

        testParams.parameters.put(LogonView.USERNAME,
                AppServerAdminConstants.ADMIN_USERNAME);
        testParams.parameters.put(LogonView.PASSWORD,
                AppServerAdminConstants.ADMIN_USERNAME);
        testParams.parameters.put(LogonView.LOGON_BUTTON,
                LogonView.LOGON_BUTTON);

        testParams.logonPageControllerMock.getViewsFolder();
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        testParams.requestMock.getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).anyTimes();

        testParams.requestMock.getParameterMap();
        expectLastCall().andReturn(testParams.parameters).atLeastOnce();

        testParams.requestMock.getSession();
        expectLastCall().andReturn(testParams.sessionMock).anyTimes();

        testParams.serviceLocatorMock.getAppServerAdminClient();
        expectLastCall().andReturn(testParams.appServerClientAdminMock).once();

        testParams.appServerClientAdminMock.areCredentialsValid(
                eq(AppServerAdminConstants.ADMIN_USERNAME),
                eq(AppServerAdminConstants.ADMIN_USERNAME));
        expectLastCall().andReturn(validationResult).once();
    }

    public void testPagePostWithSuccessfulLogon() throws Exception {

        final ParametersForTestPagePost testParams =
                new ParametersForTestPagePost();
        checkPagePost(testParams, true);

        testParams.responseMock.sendRedirect(eq("startStopServer.html"));
        expectLastCall().once();

        testParams.control.replay();

        ServiceLocator.load(testParams.serviceLocatorMock);
        testParams.logonPageControllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }

    public void testPagePostWithUnsuccessfulLogon() throws Exception {

        final ParametersForTestPagePost testParams =
                new ParametersForTestPagePost();
        checkPagePost(testParams, false);

        testParams.logonPageControllerMock.setError(eq(
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
        testParams.logonPageControllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }
}
