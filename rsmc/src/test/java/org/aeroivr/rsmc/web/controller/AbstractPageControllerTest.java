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
public abstract class AbstractPageControllerTest extends TestCase {

    private final ServiceLocator serviceLocator;

    public AbstractPageControllerTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    protected void tearDown() throws Exception {
        ServiceLocator.load(serviceLocator);
    }

    /**
     * Inner class for page get testing.
     */
    public class PageGetTestParameters<T> {
        private IMocksControl control;
        private HttpServletRequest requestMock;
        private HttpServletResponse responseMock;
        private HttpSession sessionMock;
        private ServletContext servletContextMock;
        private PrintWriter printWriterMock;
        private T controllerMock;

        public IMocksControl getControl() {
            return control;
        }

        public void setControl(final IMocksControl value) {
            this.control = value;
        }

        public HttpServletRequest getRequestMock() {
            return requestMock;
        }

        public void setRequestMock(final HttpServletRequest value) {
            this.requestMock = value;
        }

        public HttpServletResponse getResponseMock() {
            return responseMock;
        }

        public HttpSession getSessionMock() {
            return sessionMock;
        }

        public ServletContext getServletContextMock() {
            return servletContextMock;
        }

        public PrintWriter getPrintWriterMock() {
            return printWriterMock;
        }

        public T getControllerMock() {
            return controllerMock;
        }

        public void setResponseMock(final HttpServletResponse value) {
            this.responseMock = value;
        }

        public void setSessionMock(final HttpSession value) {
            this.sessionMock = value;
        }

        public void setServletContextMock(final ServletContext value) {
            this.servletContextMock = value;
        }

        public void setPrintWriterMock(final PrintWriter value) {
            this.printWriterMock = value;
        }

        public void setControllerMock(final T value) {
            this.controllerMock = value;
        }
    }

    protected <T extends BasePageController> void pageGetInitTestParams(
            final Class<T> controllerClass,
            final PageGetTestParameters<T> testParams)
                throws NoSuchMethodException {

        testParams.setControl(createNiceControl());
        testParams.setRequestMock(testParams.getControl().createMock(
                HttpServletRequest.class));
        testParams.setResponseMock(testParams.getControl().createMock(
                HttpServletResponse.class));
        testParams.setSessionMock(testParams.getControl().createMock(
                HttpSession.class));

        testParams.setPrintWriterMock(testParams.getControl().createMock(
                PrintWriter.class));
        testParams.setControllerMock(testParams.getControl().createMock(
                    controllerClass,
                    new Method[] {
                GenericServlet.class.getDeclaredMethod(
                        "getServletContext"),
                BasePageController.class.getDeclaredMethod("clearErrors")}));
        testParams.setServletContextMock(testParams.getControl().createMock(
                ServletContext.class));
    }

    protected <T extends BasePageController> void pageGetInitCalls(
            final PageGetTestParameters<T> testParams) throws IOException {

        testParams.getControllerMock().getServletContext();
        expectLastCall().andReturn(testParams.getServletContextMock(
                )).atLeastOnce();

        testParams.getServletContextMock().getRealPath(eq("/"));
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        testParams.getRequestMock().getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).atLeastOnce();

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();
    }

    public <T extends BasePageController> void pageGetInitTest(
            final Class<T> controllerClass,
            final PageGetTestParameters<T> testParams) throws Exception {
        pageGetInitTestParams(controllerClass, testParams);
        pageGetInitCalls(testParams);
    }

    /**
     * Inner class for page post testing.
     */
    public class PagePostTestParameters<T> {
        private HashMap parameters;
        private IMocksControl control;
        private HttpServletRequest requestMock;
        private HttpServletResponse responseMock;
        private HttpSession sessionMock;
        private T controllerMock;
        private ServletContext servletContextMock;
        private PrintWriter printWriterMock;
        private ServiceLocator serviceLocatorMock;
        private AppServerAdminClient appServerClientAdminMock;

        public HashMap getParameters() {
            return parameters;
        }

        public void setParameters(final HashMap value) {
            this.parameters = value;
        }

        public IMocksControl getControl() {
            return control;
        }

        public void setControl(final IMocksControl value) {
            this.control = value;
        }

        public HttpServletRequest getRequestMock() {
            return requestMock;
        }

        public void setRequestMock(final HttpServletRequest value) {
            this.requestMock = value;
        }

        public HttpServletResponse getResponseMock() {
            return responseMock;
        }

        public void setResponseMock(final HttpServletResponse value) {
            this.responseMock = value;
        }

        public HttpSession getSessionMock() {
            return sessionMock;
        }

        public void setSessionMock(final HttpSession value) {
            this.sessionMock = value;
        }

        public T getControllerMock() {
            return controllerMock;
        }

        public void setControllerMock(final T value) {
            this.controllerMock = value;
        }

        public ServletContext getServletContextMock() {
            return servletContextMock;
        }

        public void setServletContextMock(final ServletContext value) {
            this.servletContextMock = value;
        }

        public PrintWriter getPrintWriterMock() {
            return printWriterMock;
        }

        public void setPrintWriterMock(final PrintWriter value) {
            this.printWriterMock = value;
        }

        public ServiceLocator getServiceLocatorMock() {
            return serviceLocatorMock;
        }

        public void setServiceLocatorMock(final ServiceLocator value) {
            this.serviceLocatorMock = value;
        }

        public AppServerAdminClient getAppServerClientAdminMock() {
            return appServerClientAdminMock;
        }

        public void setAppServerClientAdminMock(
                final AppServerAdminClient value) {

            this.appServerClientAdminMock = value;
        }
    }

    protected <T extends BasePageController> void pagePostInitCalls(
            final PagePostTestParameters<T> testParams)
                throws RemoteException, NotBoundException {

        testParams.getControllerMock().getViewsFolder();
        expectLastCall().andReturn(TestConstants.VIEWS_FOLDER).atLeastOnce();

        testParams.getRequestMock().getContextPath();
        expectLastCall().andReturn(
                TestConstants.SERVLET_CONTEXT_PATH).anyTimes();

        testParams.getRequestMock().getParameterMap();
        expectLastCall().andReturn(testParams.getParameters()).anyTimes();

        testParams.getRequestMock().getSession();
        expectLastCall().andReturn(testParams.getSessionMock()).anyTimes();

        testParams.getServiceLocatorMock().getAppServerAdminClient();
        expectLastCall().andReturn(
            testParams.getAppServerClientAdminMock()).anyTimes();
    }

    protected <T extends BasePageController> void pagePostInitTestParams(
            final Class<T> controllerClass,
            final PagePostTestParameters<T> testParams)
                throws NoSuchMethodException {

        testParams.setParameters(new HashMap());
        testParams.setControl(createNiceControl());
        testParams.setRequestMock(testParams.getControl().createMock(
                HttpServletRequest.class));
        testParams.setResponseMock(testParams.getControl().createMock(
                HttpServletResponse.class));
        testParams.setSessionMock(testParams.getControl().createMock(
                HttpSession.class));
        testParams.setPrintWriterMock(testParams.getControl().createMock(
                PrintWriter.class));
        testParams.setControllerMock(testParams.getControl().createMock(
                controllerClass,
                new Method[] {
                    BasePageController.class.getDeclaredMethod(
                            "getViewsFolder"),
                    GenericServlet.class.getDeclaredMethod(
                            "getServletContext"),
                    BasePageController.class.getDeclaredMethod("clearErrors"),
                    BasePageController.class.getDeclaredMethod("setError",
                            String.class)}));
        testParams.setServletContextMock(testParams.getControl().createMock(
                ServletContext.class));
        testParams.setServiceLocatorMock(testParams.getControl().createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getAppServerAdminClient"),
                    ServiceLocator.class.getMethod("getServletFileUpload"),
                    ServiceLocator.class.getMethod("getFileWithUniqueName",
                        String.class, String.class, String.class)}));
        testParams.setAppServerClientAdminMock(testParams.getControl(
                ).createMock(AppServerAdminClient.class));
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
