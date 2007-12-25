/*
 * FileUtils.java
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

package org.aeroivr.common.utils;

import java.io.File;

/**
 * Utility functions for file system.
 *
 * @author Andriy Petlyovanyy
 */
public final class FileUtils {

    public static String concatenatePath(final String parentDir,
            final String child) {

        if ((null == parentDir) || parentDir.isEmpty()) {
            return child;
        } else {
            if (parentDir.endsWith(File.separator)) {
                return parentDir + child;
            } else {
                return parentDir + File.separator + child;
            }
        }
    }
}
