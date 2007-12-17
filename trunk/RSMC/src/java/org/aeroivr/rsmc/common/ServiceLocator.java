/*
 * ServiceLocator.java
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

import javax.servlet.http.HttpSession;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.LogonView;

/**
 * Service Locator for all classes.
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocator {
    
    private static volatile ServiceLocator instance = 
            new ServiceLocator();
    
    private ServiceLocator() {
    }
    
    public static ServiceLocator getInstance() {
        return instance;
    }
    
    public static void load(final ServiceLocator value) {
        instance = value;
    }

    public LogonView getLogonView(final String viewsFolder) {
        return new LogonView(viewsFolder);
    }

    public WebSecurityManager getWebSecurityManager(HttpSession session) {
        return new WebSecurityManager(session);
    }
}
