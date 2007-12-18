/*
 * ServiceLocatorTest.java
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

package org.aeroivr.rsmc.common;

import junit.framework.TestCase;
import javax.servlet.http.HttpSession;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.LogonView;
import static org.easymock.classextension.EasyMock.createMock;
import junit.framework.*;
import org.aeroivr.rsmc.web.render.PageRenderer;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.MasterPageView;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocatorTest extends TestCase {

    private ServiceLocator serviceLocator;

    public ServiceLocatorTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    public void testLoadAndGetInstance() {
        try {
            ServiceLocator serviceLocatorMock = createMock(
                    ServiceLocator.class);

            ServiceLocator.load(serviceLocatorMock);
            ServiceLocator currentServiceLocator = ServiceLocator.getInstance();
            assertTrue("References should be equal",
                    serviceLocatorMock == currentServiceLocator);
        } finally {
            ServiceLocator.load(serviceLocator);
        }
    }

    public void testGetLogonView() {
        assertNotNull("LogonView object should not be null",
                serviceLocator.getLogonView("temp"));
    }

    public void testGetWebSecurityManager() {
        HttpSession sessionMock = createMock(HttpSession.class);
        assertNotNull("LogonView object should not be null",
                serviceLocator.getWebSecurityManager(sessionMock));
    }

    public void testGetAppServerAdminClient() {
        assertNotNull("AppServerAdminClient should not be null ",
                serviceLocator.getAppServerAdminClient());
    }

    public void testGetMasterPageView() {
        assertNotNull("MasterPageView should not be null",
                serviceLocator.getMasterPageView(TestConstants.VIEWS_FOLDER,
                "/"));
    }

    public void testGetPageRenderer() {
        assertNotNull("PageRenderer should not be null",
                serviceLocator.getPageRenderer(null, null));
    }
}
