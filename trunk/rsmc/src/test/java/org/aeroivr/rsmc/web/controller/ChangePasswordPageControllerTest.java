/*
 * ChangePasswordPageControllerTest.java
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
import org.aeroivr.rsmc.web.view.ChangePasswordView;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ChangePasswordPageControllerTest
        extends BaseTestForSecurePageController {

    public ChangePasswordPageControllerTest(final String testName) {
        super(testName);
    }

    public void testGetHeader() {

        ChangePasswordPageController pageController =
                new ChangePasswordPageController();

        assertEquals("Header should contain appropriate text",
                pageController.getHeader(), "Change administrator's password");
    }

    public void testPageGet() throws Exception {

        PageGetTestParameters<ChangePasswordPageController> testParams =
                new PageGetTestParameters<ChangePasswordPageController>();

        pageGetInitTest(ChangePasswordPageController.class, testParams);

        testParams.getPrintWriterMock().print(and(and(contains("oldPassword"),
                contains("newPassword")), contains("confirmPassword")));
        expectLastCall().once();

        testParams.getControl().replay();

        testParams.getControllerMock().doGet(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithCorrectInputData() throws Exception {

        PagePostTestParameters<ChangePasswordPageController> testParams =
                new PagePostTestParameters<ChangePasswordPageController>();
        pagePostInitTest(ChangePasswordPageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        final String oldPassword = "oldPWD";
        final String newPassword = "P@WD";
        testParams.getParameters().put(ChangePasswordView.OLD_PASSWORD,
                oldPassword);
        testParams.getParameters().put(ChangePasswordView.NEW_PASSWORD,
                newPassword);
        testParams.getParameters().put(ChangePasswordView.CONFIRM_PASSWORD,
                newPassword);
        testParams.getParameters().put(ChangePasswordView.CHANGE_BUTTON,
                ChangePasswordView.CHANGE_BUTTON);

        testParams.getAppServerClientAdminMock().areCredentialsValid(
                eq(AppServerConstants.ADMIN_USERNAME), eq(oldPassword));
        expectLastCall().andReturn(true).once();

        testParams.getAppServerClientAdminMock().changeAdminPassword(
                eq(newPassword));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithNotMatchingConfirmPassord() throws Exception {

        PagePostTestParameters<ChangePasswordPageController> testParams =
                new PagePostTestParameters<ChangePasswordPageController>();
        pagePostInitTest(ChangePasswordPageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        final String oldPassword = "oldPWD";
        final String newPassword = "P@WD";
        final String confirmPassword = "P2WD";
        testParams.getParameters().put(ChangePasswordView.OLD_PASSWORD,
                oldPassword);
        testParams.getParameters().put(ChangePasswordView.NEW_PASSWORD,
                newPassword);
        testParams.getParameters().put(ChangePasswordView.CONFIRM_PASSWORD,
                confirmPassword);
        testParams.getParameters().put(ChangePasswordView.CHANGE_BUTTON,
                ChangePasswordView.CHANGE_BUTTON);

        testParams.getControllerMock().setError(eq("Confirm password should "
                + "match new password"));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }
}
