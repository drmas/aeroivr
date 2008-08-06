/*
 * SetVoiceXMLApplicationPageController.java
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
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.view.SetVoiceXMLApplicationView;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Page controller for upload WAR file with VoiceXML page.
 *
 * @author Andriy Petlyovanyy
 */
public class SetVoiceXMLApplicationPageController extends
        BaseSecurePageController {

    @Override
    protected String getHeader() {
        return "Please select appropriate WAR file";
    }

    @Override
    protected void pageGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        final SetVoiceXMLApplicationView view = ServiceLocator.getInstance()
                .getSetVoiceXMLApplicationView(getViewsFolder());
        renderView(request, response, view);
    }

    @Override
    protected void pagePost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        final SetVoiceXMLApplicationView view = ServiceLocator.getInstance()
                .getSetVoiceXMLApplicationView(getViewsFolder());

        try {
            final ServletFileUpload upload = ServiceLocator.getInstance()
                    .getServletFileUpload();
            final List<FileItem> items = upload.parseRequest(request);
            FileItem fileItem = null;
            for (final FileItem item : items) {
                if (!item.isFormField()) {
                    fileItem = item;
                    break;
                }
            }

            if (null == fileItem) {
                setError("WAR file should be uploaded");
            } else {
                if (0 == fileItem.getContentType().compareTo(
                        "application/x-zip-compressed")) {
                    try {
                        processUploadedFile(fileItem);
                    } catch (final Exception ex) {
                        throw new ServletException("Error occured during "
                                + "file upload", ex);
                    }
                } else {
                    setError("You should upload WAR type of file");
                }
            }

        } catch (final FileUploadException ex) {
            setError("File upload error: " + ex.getMessage());
        }
        renderView(request, response, view);
    }

    private void processUploadedFile(final FileItem item) throws Exception {

        final File warFile = ServiceLocator.getInstance()
                .getTempFileWithUniqueName("temp_", ".war");
        item.write(warFile);
        item.delete();
        final AppServerAdminClient client = ServiceLocator.getInstance()
                .getAppServerAdminClient();
        client.setVoiceXMLApplication(warFile.getPath());
    }
}
