/*
 * ApplicationServerTest.java
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

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import org.aeroivr.appserver.common.ApplicationConstants;
import org.aeroivr.appserver.common.ServiceLocator;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expect;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.eq;
import java.lang.reflect.Method;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ApplicationServerTest extends TestCase {
    
    public ApplicationServerTest(final String testName) {
        super(testName);
    }
    
    public void testMain() throws NoSuchMethodException, RemoteException {
        
        IMocksControl control = createStrictControl();
        ServerAdmin serverAdminMock = control.createMock(ServerAdmin.class);
        Registry rmiRegistryMock = control.createMock(Registry.class);
        ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class,
                new Method[]{ServiceLocator.class.getMethod("getServerAdmin"),
                ServiceLocator.class.getMethod("getRmiRegistry", 
                        Integer.TYPE)});
        
        control.checkOrder(false);
        
        serviceLocatorMock.getServerAdmin();
        expectLastCall().andReturn(serverAdminMock).once();
        
        expect(serviceLocatorMock.getRmiRegistry(
                eq(ApplicationConstants.APP_SERVER_ADMIN_RMI_PORT))).andReturn(
                    rmiRegistryMock).once();
        
        control.checkOrder(true);

        rmiRegistryMock.rebind(
                eq(ApplicationConstants.APP_SERVER_ADMIN_RMI_NAME),
                eq(serverAdminMock));
        expectLastCall().once();
        
        serverAdminMock.startApplicationServer();
        expectLastCall().once();
        
        control.replay();
        
        ServiceLocator.load(serviceLocatorMock);
        ApplicationServer.main(new String[0]);
        
        control.verify();
    }
    
}
