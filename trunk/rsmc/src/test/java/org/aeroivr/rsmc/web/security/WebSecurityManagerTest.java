/*
 * WebSecurityManagerTest.java
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

package org.aeroivr.rsmc.web.security;

import junit.framework.TestCase;
import javax.servlet.http.HttpSession;
import org.easymock.IMocksControl;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class WebSecurityManagerTest extends TestCase {

    public WebSecurityManagerTest(final String testName) {
        super(testName);
    }

    public void testSetLoggedInUsername() {
        final IMocksControl control = createNiceControl();
        final HttpSession sessionMock = control.createMock(HttpSession.class);
        final String username = "testUserName";

        sessionMock.setAttribute(eq(WebSecurityManager.USERNAME), eq(username));
        control.replay();

        WebSecurityManager securityManager = new WebSecurityManager(
                sessionMock);
        securityManager.setLoggedInUsername(username);

        control.verify();
    }

    public void testIsLoggedIn() {
        final IMocksControl control = createNiceControl();
        final HttpSession sessionMock = control.createMock(HttpSession.class);
        final String username = "testUserName";

        sessionMock.getAttribute(eq(WebSecurityManager.USERNAME));
        expectLastCall().andReturn(username).once()
            .andReturn(null).once();

        control.replay();

        WebSecurityManager securityManager = new WebSecurityManager(
                sessionMock);
        assertTrue("User should be logged in",
                securityManager.isLoggedIn());
        assertFalse("User should not be logged in",
                securityManager.isLoggedIn());

        control.verify();
    }

    public void testGetLoggedInUsername() {
        final IMocksControl control = createNiceControl();
        final HttpSession sessionMock = control.createMock(HttpSession.class);
        final String username = "testUserName";

        sessionMock.getAttribute(eq(WebSecurityManager.USERNAME));
        expectLastCall().andReturn(username).atLeastOnce();
        control.replay();

        WebSecurityManager securityManager = new WebSecurityManager(
                sessionMock);
        assertEquals("Username should be equal",
                securityManager.getLoggedInUsername(),
                username);

        control.verify();
    }
}
