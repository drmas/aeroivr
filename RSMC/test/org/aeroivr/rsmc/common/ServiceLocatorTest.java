/*
 * ServiceLocatorTest.java
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
 *
 * Copyright 2007 AeroIVR Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.aeroivr.rsmc.common;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import junit.framework.TestCase;
import javax.servlet.http.HttpSession;
import org.aeroivr.appserver.common.AppServerAdminConstants;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.LogonView;
import static org.easymock.classextension.EasyMock.createMock;
import junit.framework.*;
import org.aeroivr.rsmc.web.render.PageRenderer;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.MasterPageView;
import java.io.BufferedReader;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import org.aeroivr.rsmc.web.view.StartStopServerView;
import org.aeroivr.rsmc.web.view.ChangePasswordView;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.aeroivr.rsmc.web.view.SetWavFileView;
import org.aeroivr.rsmc.web.controller.SetWavFilePageController;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocatorTest extends TestCase {

    private ServiceLocator serviceLocator;

    public ServiceLocatorTest(final String testName) {
        super(testName);
        serviceLocator = ServiceLocator.getInstance();
    }

    public void testLoadAndGetInstance() {
        try {
            ServiceLocator serviceLocatorMock = createMock(
                    ServiceLocator.class);

            ServiceLocator.load(serviceLocatorMock);
            ServiceLocator currentServiceLocator = ServiceLocator.getInstance();
            assertTrue("References should be equal",
                    serviceLocatorMock == currentServiceLocator);
        } finally {
            ServiceLocator.load(serviceLocator);
        }
    }

    public void testGetLogonView() {
        assertNotNull("LogonView object should not be null",
                serviceLocator.getLogonView("temp"));
    }
    
    public void testGetLogonViewWithParameters() {
        assertNotNull("LogonView object should not be null",
                serviceLocator.getLogonView("temp", new HashMap()));
    }

    public void testGetWebSecurityManager() {
        HttpSession sessionMock = createMock(HttpSession.class);
        assertNotNull("LogonView object should not be null",
                serviceLocator.getWebSecurityManager(sessionMock));
    }

    public void testGetAppServerAdminClient() 
        throws RemoteException, NotBoundException {
        assertNotNull("AppServerAdminClient should not be null ",
                serviceLocator.getAppServerAdminClient());
    }

    public void testGetMasterPageView() {
        assertNotNull("MasterPageView should not be null",
                serviceLocator.getMasterPageView(TestConstants.VIEWS_FOLDER,
                "/"));
    }

    public void testGetPageRenderer() {
        assertNotNull("PageRenderer should not be null",
                serviceLocator.getPageRenderer(null, null));
    }

    public void testGetBufferedReaderForFile() throws IOException {
        File file = File.createTempFile("test", "test");
        try {
            BufferedReader buffReader = 
                    serviceLocator.getBufferedReaderForFile(file.getPath());
            assertNotNull("Buffered Reader for file should not be null", 
                    buffReader);
            buffReader.close();
        } finally {
            file.delete();
        }
    }

    public void testGetStartStopServerView() {
        assertNotNull("StartStopServerView should not be null",
                serviceLocator.getStartStopServerView(
                    TestConstants.VIEWS_FOLDER));
    }
    
    public void testGetStartStopServerViewWithParameters() {
        assertNotNull("StartStopServerView should not be null",
                serviceLocator.getStartStopServerView(
                    TestConstants.VIEWS_FOLDER, new HashMap()));
    }

    public void testGetChangePasswordView() {
        assertNotNull("ChangePasswordView should not be null",
                serviceLocator.getChangePasswordView(
                    TestConstants.VIEWS_FOLDER));
    }
    
    public void testGetChangePasswordWithParameters() {
        assertNotNull("ChangePasswordView should not be null",
                serviceLocator.getChangePasswordView(
                    TestConstants.VIEWS_FOLDER, new HashMap()));
    }

    public void testGetServletFileUpload() {
        assertNotNull("ServletFileUpload should not be null",
                serviceLocator.getServletFileUpload());
    }

    public void testGetFileWithUniqueName() throws IOException {
        
        final File tempFile = serviceLocator.getFileWithUniqueName(
                "/", "temp", "tmp");
        try {
            assertNotNull("ServletFileUpload should not be null",
                tempFile);
        } finally {
            if (null != tempFile) {
                tempFile.delete();
            }
        }
    }

    public void testGetSetWavFileView() {
        
        assertNotNull("SetWavFileView should not be null",
                serviceLocator.getSetWavFileView(
                    TestConstants.VIEWS_FOLDER));
    }

    public void testGetRmiRegistry() throws Exception {
        assertNotNull("Rmi Registry object should not be null",
                serviceLocator.getRmiRegistry(
                AppServerAdminConstants.APP_SERVER_ADMIN_RMI_PORT));
    }
}
