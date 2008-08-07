/*
 * ServiceLocator.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.web.render.PageRenderer;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.ChangePasswordView;
import org.aeroivr.rsmc.web.view.LogonView;
import org.aeroivr.rsmc.web.view.MasterPageView;
import org.aeroivr.rsmc.web.view.SetVoiceXMLApplicationView;
import org.aeroivr.rsmc.web.view.StartStopServerView;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Service Locator for all classes.
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocator {

    private static volatile ServiceLocator instance = new ServiceLocator();

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        return instance;
    }

    public static void load(final ServiceLocator value) {
        instance = value;
    }

    public LogonView getLogonView(final String viewsFolder) {
        return new LogonView(viewsFolder);
    }

    public WebSecurityManager getWebSecurityManager(
            final HttpSession session) {
        return new WebSecurityManager(session);
    }

    public AppServerAdminClient getAppServerAdminClient()
            throws RemoteException, NotBoundException {
        return new AppServerAdminClient();
    }

    public MasterPageView getMasterPageView(final String viewsFolder,
            final String rootDirUrl) {

        return new MasterPageView(viewsFolder, rootDirUrl);
    }

    public PageRenderer getPageRenderer(final MasterPageView masterPageView,
            final AbstractView view) {

        return new PageRenderer(masterPageView, view);
    }

    public BufferedReader getBufferedReaderForFile(final String fileName)
            throws FileNotFoundException {

        return new BufferedReader(new FileReader(fileName));
    }

    public LogonView getLogonView(final String viewsFolder,
            final Map parameters) {

        return new LogonView(viewsFolder, parameters);
    }

    public StartStopServerView getStartStopServerView(
            final String viewsFolder) {

        return new StartStopServerView(viewsFolder);
    }

    public StartStopServerView getStartStopServerView(final String viewsFolder,
            final Map parameters) {

        return new StartStopServerView(viewsFolder, parameters);
    }

    public ChangePasswordView getChangePasswordView(final String viewsFolder) {

        return new ChangePasswordView(viewsFolder);
    }

    public ChangePasswordView getChangePasswordView(final String viewsFolder,
            final Map parameters) {

        return new ChangePasswordView(viewsFolder, parameters);
    }

    public ServletFileUpload getServletFileUpload() {

        return new ServletFileUpload(new DiskFileItemFactory());
    }

    public File getTempFileWithUniqueName(final String prefix,
            final String suffix) throws IOException {

        return File.createTempFile(prefix, suffix);
    }

    public SetVoiceXMLApplicationView getSetVoiceXMLApplicationView(
            final String viewsFolder) {

        return new SetVoiceXMLApplicationView(viewsFolder);
    }

    public Registry getRmiRegistry(final int port) throws RemoteException {
        return LocateRegistry.getRegistry(port);
    }

    public File getFile(final String fileName) {
        return new File(fileName);
    }
}
