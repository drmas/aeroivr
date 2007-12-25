/*
 * BaseTestForPageController.java
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

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.common.TestConstants;
import org.easymock.classextension.IMocksControl;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class BaseTestForPageController extends TestCase {

    private final ServiceLocator serviceLocator;

    public BaseTestForPageController(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    protected void tearDown() throws Exception {
        ServiceLocator.load(serviceLocator);
    }

    public class PageGetTestParameters<T> {
        public IMocksControl control;
        public HttpServletRequest requestMock;
        public HttpServletResponse responseMock;
        public HttpSession sessionMock;
        public ServletContext servletContextMock;
        public PrintWriter printWriterMock;
        public T controllerMock;
    }

    protected <T extends BasePageController> void pageGetInitTestParams(
            final Class<T> controllerClass,
            final PageGetTestParameters<T> testParams)
                throws NoSuchMethodException {

        testParams.control = createNiceControl();
        testParams.requestMock = testParams.control.createMock(
                HttpServletRequest.class);
        testParams.responseMock = testParams.control.createMock(
                HttpServletResponse.class);
        testParams.sessionMock = testParams.control.createMock(
                HttpSession.class);

        testParams.printWriterMock = testParams.control.createMock(
                PrintWriter.class);
        testParams.controllerMock = testParams.control.createMock(
                    controllerClass,
                    new Method[] {
                GenericServlet.class.getDeclaredMethod(
                        "getServletContext"),
                BasePageController.class.getDeclaredMethod("clearErrors")});
        testParams.servletContextMock = testParams.control.createMock(
                ServletContext.class);
    }

    protected <T extends BasePageController> void pageGetInitCalls(
            final PageGetTestParameters<T> testParams) throws IOException {

        testParams.controllerMock.getServletContext();
        expectLastCall().andReturn(testParams.servletContextMock).atLeastOnce();

        testParams.servletContextMock.getRealPath(eq("/"));
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        testParams.requestMock.getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).atLeastOnce();

        testParams.responseMock.getWriter();
        expectLastCall().andReturn(testParams.printWriterMock).once();
    }

    public <T extends BasePageController> void pageGetInitTest(
            final Class<T> controllerClass,
            final PageGetTestParameters<T> testParams) throws Exception {
        pageGetInitTestParams(controllerClass, testParams);
        pageGetInitCalls(testParams);
    }

    public class PagePostTestParameters<T> {
        public HashMap parameters;
        public IMocksControl control;
        public HttpServletRequest requestMock;
        public HttpServletResponse responseMock;
        public HttpSession sessionMock;
        public T controllerMock;
        public ServletContext servletContextMock;
        public PrintWriter printWriterMock;
        public ServiceLocator serviceLocatorMock;
        public AppServerAdminClient appServerClientAdminMock;
    }

    protected <T extends BasePageController> void pagePostInitCalls(
            final PagePostTestParameters<T> testParams)
                throws RemoteException, NotBoundException {

        testParams.controllerMock.getViewsFolder();
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        testParams.requestMock.getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).anyTimes();

        testParams.requestMock.getParameterMap();
        expectLastCall().andReturn(testParams.parameters).anyTimes();

        testParams.requestMock.getSession();
        expectLastCall().andReturn(testParams.sessionMock).anyTimes();

        testParams.serviceLocatorMock.getAppServerAdminClient();
        expectLastCall().andReturn(
                testParams.appServerClientAdminMock).anyTimes();
    }

    protected <T extends BasePageController> void pagePostInitTestParams(
            final Class<T> controllerClass,
            final PagePostTestParameters<T> testParams)
                throws NoSuchMethodException {

        testParams.parameters = new HashMap();
        testParams.control = createNiceControl();
        testParams.requestMock = testParams.control.createMock(
                HttpServletRequest.class);
        testParams.responseMock = testParams.control.createMock(
                HttpServletResponse.class);
        testParams.sessionMock = testParams.control.createMock(
                HttpSession.class);
        testParams.printWriterMock = testParams.control.createMock(
                PrintWriter.class);
        testParams.controllerMock = testParams.control.createMock(
                controllerClass,
                new Method[] {
                    BasePageController.class.getDeclaredMethod(
                            "getViewsFolder"),
                    GenericServlet.class.getDeclaredMethod(
                            "getServletContext"),
                    BasePageController.class.getDeclaredMethod("clearErrors"),
                    BasePageController.class.getDeclaredMethod("setError",
                            String.class)});
        testParams.servletContextMock = testParams.control.createMock(
                ServletContext.class);
        testParams.serviceLocatorMock = testParams.control.createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getAppServerAdminClient"),
                    ServiceLocator.class.getMethod("getServletFileUpload"),
                    ServiceLocator.class.getMethod("getFileWithUniqueName",
                        String.class, String.class, String.class)});
        testParams.appServerClientAdminMock = testParams.control.createMock(
                AppServerAdminClient.class);
    }

    public <T extends BasePageController> void pagePostInitTest(
            final Class<T> controllerClass,
            final PagePostTestParameters<T> testParams)
                throws NoSuchMethodException, RemoteException,
                    NotBoundException {

        pagePostInitTestParams(controllerClass, testParams);
        pagePostInitCalls(testParams);
    }
}
