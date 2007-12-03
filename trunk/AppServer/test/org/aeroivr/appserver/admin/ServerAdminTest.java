/*
 * ServerAdminTest.java
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

package org.aeroivr.appserver.admin;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import junit.framework.TestCase;
import org.aeroivr.appserver.common.ApplicationConstants;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.h323.H323Application;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expect;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.eq;
import org.easymock.classextension.IMocksControl;


/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServerAdminTest extends TestCase {
    
    public ServerAdminTest(String testName) {
        super(testName);
    }
    
    public void testStartApplicationServer() throws NoSuchMethodException,
            RemoteException {
        
        IMocksControl control = createStrictControl();
        H323Application h323AppMock = control.createMock(H323Application.class);
        ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class, new Method[] {
            ServiceLocator.class.getMethod("getH323Application")});
        
        serviceLocatorMock.getH323Application();
        expectLastCall().andReturn(h323AppMock).once();
        
        h323AppMock.initialize();
        expectLastCall().once();
        
        h323AppMock.start();
        expectLastCall().once();
        
        control.replay();
        
        ServiceLocator.load(serviceLocatorMock);
        ServerAdmin serverAdmin = new ServerAdmin();
        serverAdmin.startApplicationServer();
        
        control.verify();
        
        assertTrue("Port number check failed", serverAdmin.toString().indexOf(
                Integer.toString(
                ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT)) >= 0);
    }
}
