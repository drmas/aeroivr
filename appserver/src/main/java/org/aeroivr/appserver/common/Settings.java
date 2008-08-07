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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Class provides persistense and access to system wide settings from the
 * settings file.
 *
 * @author Andriy Petlyovanyy
 */
public class Settings {

    private Properties properties;

    private static Settings instance = new Settings();
    protected static final String SETTINGS_FILE_NAME = "settings.properties";
    protected static final String WEB_APP_FOLDER = "web.app.folder";
    protected static final String ADMIN_PASSWORD = "admin.password";

    protected Settings() {
        try {
            this.loadSettings();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Settings getInstance() {
        return instance;
    }

    protected String getSettingsFileName() {
        return SETTINGS_FILE_NAME;
    }

    protected void loadSettings() throws IOException {
        properties = ServiceLocator.getInstance().getProperties();
        final File settingsFile = ServiceLocator.getInstance().getFile(
                getSettingsFileName());
        if (settingsFile.exists()) {
            final InputStream fileStream = ServiceLocator.getInstance()
                    .getFileAsInputStream(getSettingsFileName());
            properties.load(fileStream);
        }
    }

    public void saveSettings() throws IOException {
        final File settingsFile = ServiceLocator.getInstance().getFile(
                getSettingsFileName());
        if (!settingsFile.exists()) {
            settingsFile.createNewFile();
        }
        final OutputStream outputFile = ServiceLocator.getInstance()
                .getFileAsOutputStream(getSettingsFileName());
        if (null != properties) {
            properties.store(outputFile, "");
        }
    }

    public String getAdminPassword() {
        return properties.getProperty(ADMIN_PASSWORD, "");
    }

    public void setAdminPassword(final String newPassword) {
        properties.setProperty(ADMIN_PASSWORD, newPassword);
    }
}
