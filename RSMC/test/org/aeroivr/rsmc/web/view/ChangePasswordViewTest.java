/*
 * ChangePasswordViewTest.java
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

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ChangePasswordViewTest extends TestCase {

    public ChangePasswordViewTest(final String testName) {
        super(testName);
    }

    public void testWasChangeButtonPressed() {
        final Map parameters = new HashMap();
        parameters.put(ChangePasswordView.CHANGE_BUTTON,
                ChangePasswordView.CHANGE_BUTTON);
        ChangePasswordView view = new ChangePasswordView(
                TestConstants.VIEWS_FOLDER,
                parameters);

        assertTrue("Button was pressed", view.wasChangeButtonPressed());

        view = new ChangePasswordView(TestConstants.VIEWS_FOLDER,
                new HashMap());
        assertTrue("Button wasn\'t pressed",
                !view.wasChangeButtonPressed());
    }

    public void testGetNewPassword() {

        final String testPassword = "P@@#$RD";
        final Map parameters = new HashMap();
        parameters.put(ChangePasswordView.NEW_PASSWORD, testPassword);

        ChangePasswordView view = new ChangePasswordView(
                TestConstants.VIEWS_FOLDER,
                parameters);
        assertEquals("Password should be set", view.getNewPassword(),
                testPassword);

        view = new ChangePasswordView(TestConstants.VIEWS_FOLDER,
                new HashMap());
        assertNull("Password should be null", view.getNewPassword());
    }

    public void testGetConfirmPassword() {

        final String testPassword = "P@@#$RD";
        final Map parameters = new HashMap();
        parameters.put(ChangePasswordView.CONFIRM_PASSWORD, testPassword);

        ChangePasswordView view = new ChangePasswordView(
                TestConstants.VIEWS_FOLDER,
                parameters);
        assertEquals("Password should be set", view.getConfirmPassword(),
                testPassword);

        view = new ChangePasswordView(TestConstants.VIEWS_FOLDER,
                new HashMap());
        assertNull("Password should be null", view.getConfirmPassword());
    }

    public void testGetOldPassword() {

        final String testPassword = "P@@#$RD";
        final Map parameters = new HashMap();
        parameters.put(ChangePasswordView.OLD_PASSWORD, testPassword);

        ChangePasswordView view = new ChangePasswordView(
                TestConstants.VIEWS_FOLDER,
                parameters);
        assertEquals("Password should be set", view.getOldPassword(),
                testPassword);

        view = new ChangePasswordView(TestConstants.VIEWS_FOLDER,
                new HashMap());
        assertNull("Password should be null", view.getOldPassword());
    }

}
