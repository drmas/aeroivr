/*
 * BaseTestForSecurePageController.java
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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.aeroivr.appserver.admin.AppServerConstants;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import static org.easymock.classextension.EasyMock.expectLastCall;

/**
 *
 * @author Andriy Petlyovanyy
 */
public abstract class AbstractSecurePageControllerTest extends AbstractPageControllerTest {

    public AbstractSecurePageControllerTest(final String testName) {
        super(testName);
    }

    @Override
    public <T extends BasePageController> void pageGetInitTest(
            final Class<T> controllerClass,
            final PageGetTestParameters<T> testParams)
                throws Exception {

        pageGetInitTestParams(controllerClass, testParams);

        testParams.getRequestMock().getSession();
        expectLastCall().andReturn(testParams.getSessionMock()).anyTimes();

        testParams.getSessionMock().getAttribute(WebSecurityManager.USERNAME);
        expectLastCall().andReturn(AppServerConstants.ADMIN_USERNAME
                ).anyTimes();

        pageGetInitCalls(testParams);
    }

    @Override
    public <T extends BasePageController> void pagePostInitTest(
            final Class<T> controllerClass,
            final PagePostTestParameters<T> testParams)
                throws NoSuchMethodException, RemoteException,
                    NotBoundException {

        pagePostInitTestParams(controllerClass, testParams);

        testParams.getSessionMock().getAttribute(WebSecurityManager.USERNAME);
        expectLastCall().andReturn(AppServerConstants.ADMIN_USERNAME
                ).anyTimes();

        pagePostInitCalls(testParams);
    }
}
