/*
 * LogonViewTest.java
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

package org.aeroivr.rsmc.web.view;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.aeroivr.rsmc.common.TestConstants;
import org.easymock.classextension.IMocksControl;
import static org.easymock.classextension.EasyMock.createStrictControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class LogonViewTest extends TestCase {
    
    public LogonViewTest(final String testName) {
        super(testName);
    }

    public void testSetUsername() {
        final LogonView logonView = new LogonView(TestConstants.VIEWS_FOLDER);
        final String testUsername1 = "testUserOne";
        final String testUsername2 = "testUserTwo";
        logonView.setUsername(testUsername1);
        assertEquals("Username should be saved", logonView.getUsername(), 
                testUsername1);
        logonView.setUsername(testUsername2);
        assertEquals("Username should be saved", logonView.getUsername(), 
                testUsername2);
    }

    public void testWasLogonButtonPressed() {
        final Map parameters = new HashMap();
        parameters.put(LogonView.LOGON_BUTTON, LogonView.LOGON_BUTTON);
        LogonView logonView = new LogonView(TestConstants.VIEWS_FOLDER,
                parameters);
        
        assertTrue("Button was pressed", logonView.wasLogonButtonPressed());
        
        logonView = new LogonView(TestConstants.VIEWS_FOLDER, new HashMap());
        assertTrue("Button wasn\'t pressed", 
                !logonView.wasLogonButtonPressed());
    }

    public void testGetUsername() {
        final String testUsername = "testU";
        final Map parameters = new HashMap();
        parameters.put(LogonView.USERNAME, testUsername);
        
        LogonView logonView = new LogonView(TestConstants.VIEWS_FOLDER, 
                parameters);
        assertEquals("Username should be set", logonView.getUsername(), 
                testUsername);
        
        logonView = new LogonView(TestConstants.VIEWS_FOLDER, new HashMap());
        assertNull("Username should be null", logonView.getUsername());
    }

    public void testGetPassword() {
        final String testPassword = "testPasndsjgknd";
        final Map parameters = new HashMap();
        parameters.put(LogonView.PASSWORD, testPassword);
        
        LogonView logonView = new LogonView(TestConstants.VIEWS_FOLDER, 
                parameters);
        assertEquals("Password should be set", logonView.getPassword(), 
                testPassword);
        
        logonView = new LogonView(TestConstants.VIEWS_FOLDER, new HashMap());
        assertNull("Password should be null", logonView.getPassword());
    }
    
}
