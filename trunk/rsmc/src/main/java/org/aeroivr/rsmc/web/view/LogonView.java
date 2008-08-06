/*
 * LogonView.java
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

import java.util.Map;

/**
 * View for logon page.
 *
 * @author Andriy Petlyovanyy
 */
public class LogonView extends AbstractView {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGON_BUTTON = "logon";

    public LogonView(final String viewsFolder) {
        super(viewsFolder);
    }

    public LogonView(final String viewsFolder, final Map parameters) {
        super(viewsFolder, parameters, USERNAME, PASSWORD, LOGON_BUTTON);
    }

    public void setUsername(final String value) {
        setValue(USERNAME, value);
    }

    public boolean wasLogonButtonPressed() {
        return containsKey(LOGON_BUTTON);
    }

    public String getUsername() {
        return (String) getValue(USERNAME);
    }

    public String getPassword() {
        return (String) getValue(PASSWORD);
    }

    @Override
    protected String getHtmlFileName() {
        return "logon.html";
    }
}
