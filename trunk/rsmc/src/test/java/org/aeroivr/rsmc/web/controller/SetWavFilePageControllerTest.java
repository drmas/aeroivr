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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.controller.
        AbstractPageControllerTest.PageGetTestParameters;
import org.aeroivr.rsmc.web.controller.
        AbstractPageControllerTest.PagePostTestParameters;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import static org.easymock.classextension.EasyMock.contains;
import static org.easymock.classextension.EasyMock.and;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class SetWavFilePageControllerTest
        extends AbstractSecurePageControllerTest {

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

        testParams.getPrintWriterMock().print(and(and(
                contains("multipart/form-data"),
                contains("wavFile")),
                and(contains("upload"),
                contains("type=\"file\""))));
        expectLastCall().once();

        testParams.getControl().replay();

        testParams.getControllerMock().doGet(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithFile() throws Exception {

        PagePostTestParameters<SetWavFilePageController> testParams =
                new PagePostTestParameters<SetWavFilePageController>();
        pagePostInitTest(SetWavFilePageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        final ServletFileUpload servletFileUploadMock =
                testParams.getControl().createMock(ServletFileUpload.class);

        final List<FileItem> fileItems = new ArrayList<FileItem>();
        FileItem fileItemMock = testParams.getControl(
                ).createMock(FileItem.class);

        final File fileMock = testParams.getControl().createMock(File.class);

        fileItems.add(fileItemMock);

        testParams.getServiceLocatorMock().getServletFileUpload();
        expectLastCall().andReturn(servletFileUploadMock).once();

        servletFileUploadMock.parseRequest(testParams.getRequestMock());
        expectLastCall().andReturn(fileItems).once();

        fileItemMock.isFormField();
        expectLastCall().andReturn(false).once();

        fileItemMock.getContentType();
        expectLastCall().andReturn("audio/wav").once();

        testParams.getControllerMock().getServletContext();
        expectLastCall().andReturn(testParams.getServletContextMock()).once();

        testParams.getServletContextMock().getRealPath(eq("/WAV"));
        expectLastCall().andReturn(null).atLeastOnce();

        testParams.getServiceLocatorMock().getFileWithUniqueName(null,
                "play_", ".wav");
        expectLastCall().andReturn(fileMock).once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }

    public void testPagePostWithIncompleteData() throws Exception {

        PagePostTestParameters<SetWavFilePageController> testParams =
                new PagePostTestParameters<SetWavFilePageController>();
        pagePostInitTest(SetWavFilePageController.class, testParams);

        testParams.getResponseMock().getWriter();
        expectLastCall().andReturn(testParams.getPrintWriterMock()).once();

        final ServletFileUpload servletFileUploadMock =
                testParams.getControl().createMock(ServletFileUpload.class);

        final List<FileItem> fileItems = new ArrayList<FileItem>();

        testParams.getServiceLocatorMock().getServletFileUpload();
        expectLastCall().andReturn(servletFileUploadMock).once();

        servletFileUploadMock.parseRequest(testParams.getRequestMock());
        expectLastCall().andReturn(fileItems).once();

        testParams.getControllerMock().setError(
                eq("Wav file should be uploaded"));
        expectLastCall().once();

        testParams.getControl().replay();

        ServiceLocator.load(testParams.getServiceLocatorMock());
        testParams.getControllerMock().doPost(testParams.getRequestMock(),
                testParams.getResponseMock());

        testParams.getControl().verify();
    }
}
