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

import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.controller.
        BaseTestForPageController.PageGetTestParameters;
import org.aeroivr.rsmc.web.controller.
        BaseTestForPageController.PagePostTestParameters;
import org.aeroivr.rsmc.web.view.LogonView;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 * @author Andriy Petlyovanyy
 */
public class LogonPageControllerTest extends BaseTestForPageController {

    public LogonPageControllerTest(final String testName) {
        super(testName);
    }

    public void testPageGet() throws Exception {

        PageGetTestParameters<LogonPageController> testParams =
                new PageGetTestParameters<LogonPageController>();
        pageGetInitTest(LogonPageController.class, testParams);

        testParams.getPrintWriterMock().print(and(and(and(contains("username"),
                contains("password")), and(contains("<form"),
                contains("<input"))), contains("Please provide credentials")));
        expectLastCall().once();

        testParams.getControl().replay();

        testParams.getControllerMock().doGet(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    private void checkPagePost(
            final PagePostTestParameters<LogonPageController> testParams,
            final boolean validationResult) throws Exception {

        pagePostInitTest(LogonPageController.class, testParams);

        testParams.getParameters().put(LogonView.USERNAME,
                AppServerConstants.ADMIN_USERNAME);
        testParams.getParameters().put(LogonView.PASSWORD,
                AppServerConstants.ADMIN_USERNAME);
        testParams.getParameters().put(LogonView.LOGON_BUTTON,
                LogonView.LOGON_BUTTON);

        testParams.getAppServerClientAdminMock().areCredentialsValid(
                eq(AppServerConstants.ADMIN_USERNAME),
                eq(AppServerConstants.ADMIN_USERNAME));
        expectLastCall().andReturn(validationResult).once();
    }

    public void testPagePostWithSuccessfulLogon() throws Exception {

        PagePostTestParameters<LogonPageController> testParams =
                new PagePostTestParameters<LogonPageController>();
        checkPagePost(testParams, true);

        testParams.getResponseMock().sendRedirect(eq("startStopServer.html"));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithUnsuccessfulLogon() throws Exception {

        PagePostTestParameters<LogonPageController> testParams =
                new PagePostTestParameters<LogonPageController>();
        checkPagePost(testParams, false);

        testParams.getControllerMock().setError(eq(
                "Invalid credentials supplied"));
        expectLastCall().once();

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        testParams.getPrintWriterMock().print(and(and(contains("username"),
                contains("password")), and(contains("<form"),
                contains("<input"))));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }
}
