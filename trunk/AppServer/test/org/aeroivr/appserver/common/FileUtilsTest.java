/*
 * FileUtilsTest.java
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
import junit.framework.TestCase;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class FileUtilsTest extends TestCase {

    public FileUtilsTest(final String testName) {
        super(testName);
    }

    public void testGetApplicationDirectory() {
        final File file = new File(FileUtils.getApplicationDirectory());
        assertTrue("Directory should exists", file.exists());
        assertTrue("It should be directory", file.isDirectory());
        assertTrue("Directory path should be absolute", file.isAbsolute());
    }

    public void testConcatenatePath() {
        final String parent1 = "parent";
        final String parent2 = "parent" + File.separator;
        final String child = "child";

        final String result1 = FileUtils.concatenatePath(parent1, child);
        final String result2 = FileUtils.concatenatePath(parent2, child);
        final String result3 = FileUtils.concatenatePath("", child);

        assertEquals("Paths should be equal",
                parent1 + File.separator + child, result1);
        assertEquals("Paths should be equal",
                parent2 + child, result2);
        assertEquals("Paths should be equal",
                child, result3);
    }
}
