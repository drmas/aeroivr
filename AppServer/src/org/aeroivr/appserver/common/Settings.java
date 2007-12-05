/*
 * Settings.java
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

package org.aeroivr.appserver.common;

/**
 * Class provides persistense and access tp system wide settings from
 * the settings file.
 *
 * @author Andriy Petlyovanyy
 */
public class Settings {

    protected static final String WAV_FILE_NAME = "WavFileName";

    private Settings() {
    }

    public String getWavFileName() {
        return null;
    }

    public static Settings getInstance() {
        return null;
    }

    protected String getSettingsFileName() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected void loadSettings() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void saveSettings() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
