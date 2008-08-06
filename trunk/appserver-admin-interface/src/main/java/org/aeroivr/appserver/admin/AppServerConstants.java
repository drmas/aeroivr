/*
 * AppServerAdminConstants.java
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

package org.aeroivr.appserver.admin;

/**
 * Constants of the application server administration. Shared between projects.
 *
 * @author Andriy Petlyovanyy
 */
public final class AppServerConstants {

    public static final int APP_SERVER_ADMIN_RMI_PORT = 3011;
    public static final String APP_SERVER_ADMIN_RMI_NAME =
        "AERO_IVR_APP_SERVER";
    public static final String ADMIN_USERNAME = "admin";

    private AppServerConstants() {
    }
}
