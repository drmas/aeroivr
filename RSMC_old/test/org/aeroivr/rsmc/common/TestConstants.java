/*
 * TestConstants.java
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

import java.io.File;

/**
 * Constans for tests.
 *
 * @author Andriy Petlyovanyy
 */
public final class TestConstants {

    /* Value of the constant is tried to read from system property
     * (to support build from Ant tasks where paths are a bit different)
     * if there is no such system property then we are in IDE and
     * get Web folder path.
     */
    public static final String VIEWS_FOLDER = System.getProperty(
            "aeroivr.web.folder", new File("web").getAbsolutePath());
    public static final String SERVLET_CONTEXT_PATH = "/RSMC/";

    protected TestConstants() {
    }
}
