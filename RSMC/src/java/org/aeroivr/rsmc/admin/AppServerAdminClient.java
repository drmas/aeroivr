/*
 * AppServerAdminClient.java
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

package org.aeroivr.rsmc.admin;

/**
 * Client of the application server.
 *
 * @author Andriy Petlyovanyy
 */
public class AppServerAdminClient {
    
    public AppServerAdminClient() {
    }

    public boolean areCredentialsValid(final String username, 
            final String password) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isAppServerRunning() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void startAppServer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void stopAppServer() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
