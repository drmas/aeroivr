/*
 * SetWavFilePageControllerTest.java
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

package org.aeroivr.rsmc.web.controller;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import junit.framework.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class SetWavFilePageControllerTest 
        extends BaseTestForSecurePageController {
    
    public SetWavFilePageControllerTest(final String testName) {
        super(testName);
    }

    public void testGetHeader() {
        final SetWavFilePageController pageController = 
                new SetWavFilePageController();
        assertEquals("Header should contain appropriate text",
                pageController.getHeader(), 
                "Please select appropriate wav file");
    }

    public void testPageGet() throws Exception {

        PageGetTestParameters<SetWavFilePageController> testParams =
                new PageGetTestParameters<SetWavFilePageController>();
        
        pageGetInitTest(SetWavFilePageController.class, testParams);

        testParams.printWriterMock.print(and(and(
                contains("multipart/form-data"),
                contains("wavFile")), 
                and(contains("upload"), 
                contains("type=\"file\""))));
        expectLastCall().once();

        testParams.control.replay();

        testParams.controllerMock.doGet(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }

    public void testPagePostWithFile() throws Exception {
        
        PagePostTestParameters<SetWavFilePageController> testParams =
                new PagePostTestParameters<SetWavFilePageController>();
        pagePostInitTest(SetWavFilePageController.class, testParams);

        testParams.responseMock.getWriter();
        expectLastCall().andReturn(testParams.printWriterMock).once();
        
        final ServletFileUpload servletFileUploadMock = 
                testParams.control.createMock(ServletFileUpload.class);
        
        final List<FileItem> fileItems = new ArrayList<FileItem>();
        FileItem fileItemMock = testParams.control.createMock(FileItem.class);
        
        final File fileMock = testParams.control.createMock(File.class);
        
        fileItems.add(fileItemMock);
        
        testParams.serviceLocatorMock.getServletFileUpload();
        expectLastCall().andReturn(servletFileUploadMock).once();
        
        servletFileUploadMock.parseRequest(testParams.requestMock);
        expectLastCall().andReturn(fileItems).once();

        fileItemMock.isFormField();
        expectLastCall().andReturn(false).once();
        
        fileItemMock.getContentType();
        expectLastCall().andReturn("audio/wav").once();
        
        testParams.serviceLocatorMock.getFileWithUniqueName(null, 
                "play_", ".wav");
        expectLastCall().andReturn(fileMock).once();
        
        testParams.control.replay();

        ServiceLocator.load(testParams.serviceLocatorMock);
        testParams.controllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }
    
    public void testPagePostWithIncompleteData() throws Exception {

        PagePostTestParameters<SetWavFilePageController> testParams =
                new PagePostTestParameters<SetWavFilePageController>();
        pagePostInitTest(SetWavFilePageController.class, testParams);

        testParams.responseMock.getWriter();
        expectLastCall().andReturn(testParams.printWriterMock).once();
        
        final ServletFileUpload servletFileUploadMock = 
                testParams.control.createMock(ServletFileUpload.class);
        
        final List<FileItem> fileItems = new ArrayList<FileItem>();
        
        testParams.serviceLocatorMock.getServletFileUpload();
        expectLastCall().andReturn(servletFileUploadMock).once();
        
        servletFileUploadMock.parseRequest(testParams.requestMock);
        expectLastCall().andReturn(fileItems).once();
        
        testParams.controllerMock.setError(eq("Wav file should be uploaded"));
        expectLastCall().once();
        
        testParams.control.replay();

        ServiceLocator.load(testParams.serviceLocatorMock);
        testParams.controllerMock.doPost(testParams.requestMock,
                testParams.responseMock);

        testParams.control.verify();
    }
}
