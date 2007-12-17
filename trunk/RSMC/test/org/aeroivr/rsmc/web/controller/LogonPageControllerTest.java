/*
 * LogonPageControllerTest.java
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

package org.aeroivr.rsmc.web.controller;

import java.lang.reflect.Method;
import java.util.Map;
import junit.framework.TestCase;
import javax.servlet.http.*;
import org.aeroivr.rsmc.admin.AppServerAdminClient;
import org.aeroivr.rsmc.common.ServiceLocator;
import org.aeroivr.rsmc.web.security.WebSecurityManager;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.LogonView;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import org.easymock.classextension.IMocksControl;

/**
 * @author Andriy Petlyovanyy
 */
public class LogonPageControllerTest extends TestCase {
    
    public LogonPageControllerTest(final String testName) {
        super(testName);
    }
    
    public void testPageGet() throws Exception {
        final IMocksControl control = createStrictControl();
        final ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getDeclaredMethod(
                        "getLogonView", String.class)});
        final HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        final HttpServletResponse responseMock = control.createMock(
                HttpServletResponse.class);
        final LogonPageController logonPageControllerMock = control.createMock(
                LogonPageController.class,
                new Method[] {LogonPageController.class.getDeclaredMethod(
                        "getViewsFolder"),
                LogonPageController.class.getDeclaredMethod("renderView",
                        HttpServletRequest.class, HttpServletResponse.class,
                        AbstractView.class)});
        final LogonView logonViewMock = control.createMock(LogonView.class,
                new Method[] {LogonView.class.getMethod("setUsername",
                        String.class)});
        final String viewsFolder = "views";
        final String adminUsername = "admin";
        
        logonPageControllerMock.getViewsFolder();
        expectLastCall().andReturn(viewsFolder).once();
        
        serviceLocatorMock.getLogonView(eq(viewsFolder));
        expectLastCall().andReturn(logonViewMock).once();
        
        logonViewMock.setUsername(adminUsername);
        expectLastCall().once();
        
        logonPageControllerMock.renderView(eq(requestMock), eq(responseMock),
                logonViewMock);
        expectLastCall().once();
        
        control.replay();
        
        logonPageControllerMock.pageGet(requestMock, responseMock);
        
        control.verify();
    }
    
    public void testPagePost() throws Exception {
        final IMocksControl control = createStrictControl();
        final ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getLogonView", String.class, Map.class),
                    ServiceLocator.class.getMethod("getWebSecurityManager",
                        HttpSession.class)});
        final HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        final HttpServletResponse responseMock = control.createMock(
                HttpServletResponse.class);
        final HttpSession sessionMock = control.createMock(
                HttpSession.class);
        final WebSecurityManager webSecurityManagerMock = control.createMock(
                WebSecurityManager.class);
        final LogonView logonViewMock = control.createMock(LogonView.class);
        final AppServerAdminClient appServerAdminClientMock = 
                control.createMock(AppServerAdminClient.class);
        final LogonPageController logonPageControllerMock = control.createMock(
                LogonPageController.class,
                new Method[] {LogonPageController.class.getDeclaredMethod(
                        "getViewsFolder"),
                LogonPageController.class.getDeclaredMethod("renderView",
                        HttpServletRequest.class, HttpServletResponse.class,
                        AbstractView.class)});
        final String username = "admin";
        final String password = "admin";
        
        logonViewMock.wasLogonButtonPressed();
        expectLastCall().andReturn(true).once();
        
        logonViewMock.getUsername();
        expectLastCall().andReturn(username).atLeastOnce();
        
        logonViewMock.getPassword();
        expectLastCall().andReturn(password).once();
        
        appServerAdminClientMock.areCredentialsValid(eq(username), 
                eq(password));
        expectLastCall().andReturn(true).once();
        
        serviceLocatorMock.getWebSecurityManager(sessionMock);
        expectLastCall().andReturn(webSecurityManagerMock).once();
        
        webSecurityManagerMock.setLoggedInUsername(eq(username));
        expectLastCall().once();
        
        responseMock.sendRedirect("startStopServer.html");
        control.replay();
        
        logonPageControllerMock.pagePost(requestMock, responseMock);
        
        control.verify();
    }
}
