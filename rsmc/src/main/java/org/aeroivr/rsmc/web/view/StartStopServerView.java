/*
 * StartStopServerView.java
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
 * View for Start \ Stop application server.
 *
 * @author Andriy Petlyovanyy
 */
public class StartStopServerView extends AbstractView {

    public static final String SERVER_STOPPED = "serverStopped";
    public static final String SERVER_STARTED = "serverStarted";

    public static final String START_SERVER_BUTTON = "startServer";
    public static final String STOP_SERVER_BUTTON = "stopServer";
    public static final String RESTART_SERVER_BUTTON = "restartServer";

    public StartStopServerView(final String vwsFolder) {
        super(vwsFolder);
    }

    public StartStopServerView(final String vwsFolder, final Map parameters) {
        super(vwsFolder, parameters, START_SERVER_BUTTON, STOP_SERVER_BUTTON,
                RESTART_SERVER_BUTTON);
    }

    public void setServerStarted(final boolean value) {
        setValue(SERVER_STARTED, value);
        setValue(SERVER_STOPPED, !value);
    }

    @Override
    protected String getHtmlFileName() {
        return "startStopServer.html";
    }

    public boolean wasStartButtonPressed() {
        return containsKey(START_SERVER_BUTTON);
    }

    public boolean wasStopButtonPressed() {
        return containsKey(STOP_SERVER_BUTTON);
    }

    public boolean wasRestartButtonPressed() {
        return containsKey(RESTART_SERVER_BUTTON);
    }
}
