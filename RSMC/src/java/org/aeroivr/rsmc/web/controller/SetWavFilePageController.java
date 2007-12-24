/*
 * SetWavFilePageController.java
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

import java.io.*;
import java.net.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.view.SetWavFileView;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Page controller for upload WAV file page.
 *
 * @author Andriy Petlyovanyy
 */
public class SetWavFilePageController extends BaseSecurePageController {

    protected String getHeader() {
        return "Please select appropriate wav file";
    }

    protected void pageGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        SetWavFileView view = ServiceLocator.getInstance(
                ).getSetWavFileView(getViewsFolder());
        renderView(request, response, view);
    }

    protected void pagePost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        SetWavFileView view = ServiceLocator.getInstance().getSetWavFileView(
                getViewsFolder());

        try {
            ServletFileUpload upload = ServiceLocator.getInstance(
                    ).getServletFileUpload();
            List<FileItem> items = upload.parseRequest(request);
            FileItem fileItem = null;
            for (FileItem item: items) {
                if (!item.isFormField()) {
                    fileItem = item;
                    break;
                }
            }

            if (null == fileItem) {
                setError("Wav file should be uploaded");
            } else {
                if (0 == fileItem.getContentType().compareTo("audio/wav")) {
                    try {
                        processUploadedFile(fileItem);
                    } catch (Exception ex) {
                        throw new ServletException("Error occured during " +
                                "file upload", ex);
                    }
                } else {
                    setError("You should upload WAV type of file");
                }
            }

        } catch (FileUploadException ex) {
            setError("File upload error: " + ex.getMessage());
        }
        renderView(request, response, view);
    }

    private void processUploadedFile(FileItem item) throws Exception {

        File wavFile = ServiceLocator.getInstance().getFileWithUniqueName(
                getWavFilesFolder(), "play_", ".wav");
        item.write(wavFile);
        AppServerAdminClient client = ServiceLocator.getInstance(
                ).getAppServerAdminClient();
        client.setWavFileName(wavFile.getPath());
    }
}
