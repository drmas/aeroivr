/*
 * MasterPageViewTest.java
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
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.aeroivr.rsmc.common.TestConstants;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class MasterPageViewTest extends TestCase {
    
    public MasterPageViewTest(final String testName) {
        super(testName);
    }
    
    public void testSetShowMenu() throws FileNotFoundException, IOException {
       
        final MasterPageView masterPageView = new MasterPageView(
                TestConstants.VIEWS_FOLDER,
                TestConstants.SERVLET_CONTEXT_PATH);
        masterPageView.setShowMenu(true);
        String content = masterPageView.getContent();
        
        assertNotNull("Content should be present", content);
        assertTrue("Page should contain link on Start\\Stop Server page",
                content.indexOf("Start\\Stop Server") > -1);
        assertTrue("Page should contain link on Change Password page",
                content.indexOf("Change Password") > -1);
        assertTrue("Page should contain link on Set WAV file page",
                content.indexOf("Set WAV file") > -1);
        
        masterPageView.setShowMenu(false);
        content = masterPageView.getContent();
        
        assertNotNull("Content should be present", content);
        assertTrue("Page should not contain link on Start\\Stop Server page",
                content.indexOf("Start\\Stop Server") == -1);
        assertTrue("Page should not contain link on Change Password page",
                content.indexOf("Change Password") == -1);
        assertTrue("Page should not contain link on Set WAV file page",
                content.indexOf("Set WAV file") == -1);
    }

    public void testSetHeader() throws FileNotFoundException, IOException {
       
        final MasterPageView masterPageView = new MasterPageView(
                TestConstants.VIEWS_FOLDER,
                TestConstants.SERVLET_CONTEXT_PATH);
        final String testHeader = "Test Header";
        
        masterPageView.setHeader(testHeader);
        String content = masterPageView.getContent();
        
        assertNotNull("Content should be present", content);
        assertTrue("Page should contain header",
                content.indexOf(testHeader) > -1);
        
        masterPageView.setHeader("");
        content = masterPageView.getContent();
        assertNotNull("Content should be present", content);
        assertTrue("Page should not contain header",
                content.indexOf(testHeader) == -1);
    }

    public void testGetHtmlFileName() {

        final MasterPageView masterPageView = new MasterPageView(
                TestConstants.VIEWS_FOLDER,
                TestConstants.SERVLET_CONTEXT_PATH);
        assertEquals("File should be equal to masterPage.html",
                masterPageView.getHtmlFileName(), "masterPage.html");
    }

    public void testSetErrors() throws FileNotFoundException, IOException {
       
        final MasterPageView masterPageView = new MasterPageView(
                TestConstants.VIEWS_FOLDER,
                TestConstants.SERVLET_CONTEXT_PATH);
        final String error1 = "Error One";
        final String error2 = "Error Seconddd";
        final String error3 = "Error Number Three";
        
        final List<String> errors = new ArrayList<String>();
        errors.add(error1);
        errors.add(error2);
        errors.add(error3);
        
        masterPageView.setErrors(errors);
        String content = masterPageView.getContent();
        
        assertNotNull("Content should be present", content);
        assertTrue("Should contain error text",
                -1 < content.indexOf(error1));
        assertTrue("Should contain error text",
                -1 < content.indexOf(error2));
        assertTrue("Should contain error text",
                -1 < content.indexOf(error3));
        
        masterPageView.setErrors(new ArrayList<String>());
        content = masterPageView.getContent();
        
        assertNotNull("Content should be present", content);
        assertTrue("Should contain not error text",
                -1 == content.indexOf(error1));
        assertTrue("Should contain not error text",
                -1 == content.indexOf(error2));
        assertTrue("Should contain not error text",
                -1 == content.indexOf(error3));
    }
    
}
