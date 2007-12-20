/*
 * StartStopServerViewTest.java
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import junit.framework.TestCase;
import junit.framework.*;
import java.util.Map;
import junit.runner.TestCollector;
import org.aeroivr.rsmc.common.TestConstants;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class StartStopServerViewTest extends TestCase {
    
    public StartStopServerViewTest(final String testName) {
        super(testName);
    }

    public void testSetServerStarted() throws FileNotFoundException, IOException {
        final StartStopServerView view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER);
        
        view.setServerStarted(true);
        String content = view.getContent();
        assertNotNull("Content should not be null",
                content);
        assertTrue("Should contain started status",
                -1 < content.indexOf("Started"));
        assertTrue("Should not contain stopped status",
                -1 == content.indexOf("Stopped"));
        
        view.setServerStarted(false);
        content = view.getContent();
        assertNotNull("Content should not be null",
                content);
        assertTrue("Should contain stopped status",
                -1 < content.indexOf("Stopped"));
        assertTrue("Should not contain started status",
                -1 == content.indexOf("Started"));
    }

    public void testWasStartButtonPressed() {

        final HashMap parameters = new HashMap();
        parameters.put(StartStopServerView.START_SERVER_BUTTON, 
                StartStopServerView.START_SERVER_BUTTON);
        
        StartStopServerView view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, parameters);
        
        assertTrue("Button should be pressed", 
                view.wasStartButtonPressed());
        
        view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, new HashMap());
        
        assertFalse("Button should not be pressed",
                view.wasStartButtonPressed());
    }

    public void testWasStopButtonPressed() {
        
        final HashMap parameters = new HashMap();
        parameters.put(StartStopServerView.STOP_SERVER_BUTTON, 
                StartStopServerView.STOP_SERVER_BUTTON);
        
        StartStopServerView view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, parameters);
        
        assertTrue("Button should be pressed", 
                view.wasStopButtonPressed());
        
        view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, new HashMap());
        
        assertFalse("Button should not be pressed",
                view.wasStopButtonPressed());
    }

    public void testWasRestartButtonPressed() {
        
        final HashMap parameters = new HashMap();
        parameters.put(StartStopServerView.RESTART_SERVER_BUTTON, 
                StartStopServerView.RESTART_SERVER_BUTTON);
        
        StartStopServerView view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, parameters);
        
        assertTrue("Button should be pressed", 
                view.wasRestartButtonPressed());
        
        view = new StartStopServerView(
                TestConstants.VIEWS_FOLDER, new HashMap());
        
        assertFalse("Button should not be pressed",
                view.wasRestartButtonPressed());
    }
    
}
