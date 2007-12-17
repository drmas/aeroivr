/*
 * BasePageControllerTest.java
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
import junit.framework.TestCase;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.easymock.classextension.IMocksControl;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;

/**
 * @author Andriy Petlyovanyy
 */
public class BasePageControllerTest extends TestCase {

    public BasePageControllerTest(final String testName) {
        super(testName);
    }

    public void testDoGet() throws Exception {

        IMocksControl control = createStrictControl();
        BasePageController basePageControllerMock = control.createMock(
                BasePageController.class,
                new Method[] {BasePageController.class.getDeclaredMethod(
                        "pageGet", HttpServletRequest.class,
                            HttpServletResponse.class)});
        HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        HttpServletResponse responseMock = control.createMock(
                HttpServletResponse.class);

        basePageControllerMock.pageGet(eq(requestMock), eq(responseMock));
        expectLastCall().once();

        control.replay();

        basePageControllerMock.doGet(requestMock, responseMock);

        control.verify();
    }

    public void testDoPost() throws Exception {

        IMocksControl control = createStrictControl();
        BasePageController basePageControllerMock = control.createMock(
                BasePageController.class,
                new Method[] {BasePageController.class.getDeclaredMethod(
                        "pagePost", HttpServletRequest.class,
                            HttpServletResponse.class)});
        HttpServletRequest requestMock = control.createMock(
                HttpServletRequest.class);
        HttpServletResponse responseMock = control. createMock(
                HttpServletResponse.class);

        basePageControllerMock.pagePost(eq(requestMock), eq(responseMock));
        expectLastCall().once();

        control.replay();

        basePageControllerMock.doPost(requestMock, responseMock);

        control.verify();
    }

}
