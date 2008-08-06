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
package org.aeroivr.utils;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * 
 * @author Andriy Petlyovanyy
 */
public class FileUtilsTest extends TestCase {

    public FileUtilsTest(final String testName) {
        super(testName);
    }

    public void testConcatenatePath() {
        final String parent1 = "parent";
        final String parent2 = "parent" + File.separator;
        final String child = "child";

        final String result1 = FileUtils.concatenatePath(parent1, child);
        final String result2 = FileUtils.concatenatePath(parent2, child);
        final String result3 = FileUtils.concatenatePath("", child);

        assertEquals("Paths should be equal", parent1 + File.separator + child,
                result1);
        assertEquals("Paths should be equal", parent2 + child, result2);
        assertEquals("Paths should be equal", child, result3);
    }

    public void testMoveFile() throws IOException {

        File sourceTempFile = File.createTempFile("temp", "temp");
        final File destinationTempFile = File.createTempFile("temp", "temp");

        try {
            FileUtils.moveFile(sourceTempFile.getPath(), destinationTempFile
                    .getPath(), false);
            assertFalse("Exception should be thrown", false);
        } catch (final IOException ex) {
            assertEquals(ex.getMessage(), "Destination file already exists. "
                    + "Set forceOverride parameter to true to override it.");
        }

        FileUtils.moveFile(sourceTempFile.getPath(), destinationTempFile
                .getPath(), true);
        assertTrue("Destination file should exists", destinationTempFile
                .exists());
        assertFalse("Source file should be deleted", sourceTempFile.exists());

        sourceTempFile = File.createTempFile("temp", "temp");
        final String destinationFileName = destinationTempFile.getPath();
        destinationTempFile.delete();
        FileUtils
                .moveFile(sourceTempFile.getPath(), destinationFileName, false);
        assertTrue("Destination file should exists", destinationTempFile
                .exists());
        assertFalse("Source file should be deleted", sourceTempFile.exists());

        sourceTempFile = File.createTempFile("temp", "temp");
        destinationTempFile.delete();
        FileUtils.moveFile(sourceTempFile.getPath(), destinationFileName, true);
        assertTrue("Destination file should exists", destinationTempFile
                .exists());
        assertFalse("Source file should be deleted", sourceTempFile.exists());
    }
}
