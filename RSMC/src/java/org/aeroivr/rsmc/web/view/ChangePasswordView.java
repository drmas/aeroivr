/*
 * ChangePasswordView.java
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
 *  View for change password page.
 *
 * @author Andriy Petlyovanyy
 */
public class ChangePasswordView extends AbstractView {
    
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String CONFIRM_PASSWORD = "confirmPassword";
    public static final String CHANGE_BUTTON = "change";
    
    public ChangePasswordView(final String viewsFolder) {
        super(viewsFolder);
    }
    
    public ChangePasswordView(final String viewsFolder, final Map parameters) {
        super(viewsFolder, parameters, OLD_PASSWORD, NEW_PASSWORD, 
                CONFIRM_PASSWORD, CHANGE_BUTTON);
    }

    protected String getHtmlFileName() {
        return "changePassword.html";
    }

    public boolean wasChangeButtonPressed() {
        return containsKey(CHANGE_BUTTON);
    }
    
    public String getOldPassword() {
        return (String) getValue(OLD_PASSWORD);
    }
    
    public String getNewPassword() {
        return (String) getValue(NEW_PASSWORD);
    }
    
   public String getConfirmPassword() {
       return (String) getValue(CONFIRM_PASSWORD);
   }
}
