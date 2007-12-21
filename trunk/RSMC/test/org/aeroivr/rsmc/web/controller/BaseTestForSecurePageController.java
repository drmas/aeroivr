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

import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.easymock.classextension.IMocksControl;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class BaseTestForSecurePageController extends BaseTestForPageController {

    public BaseTestForSecurePageController(final String testName) {
        super(testName);
    }

    public <T extends BasePageController> void pageGetInitTest(
            Class<T> controllerClass, PageGetTestParameters<T> testParams)
                throws Exception {

        pageGetInitTestParams(controllerClass, testParams);

        testParams.requestMock.getSession();
        expectLastCall().andReturn(testParams.sessionMock).anyTimes();

        testParams.sessionMock.getAttribute(WebSecurityManager.USERNAME);
        expectLastCall().andReturn(AppServerAdminConstants.ADMIN_USERNAME
                ).anyTimes();

        pageGetInitCalls(testParams);
    }

    public <T extends BasePageController> void pagePostInitTest(
            Class<T> controllerClass, PagePostTestParameters<T> testParams)
                throws NoSuchMethodException {

        pagePostInitTestParams(controllerClass, testParams);

        testParams.sessionMock.getAttribute(WebSecurityManager.USERNAME);
        expectLastCall().andReturn(AppServerAdminConstants.ADMIN_USERNAME
                ).anyTimes();

        pagePostInitCalls(testParams);
    }
}
