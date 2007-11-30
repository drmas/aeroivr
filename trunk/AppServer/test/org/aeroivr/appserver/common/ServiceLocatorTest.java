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
 */

package org.aeroivr.appserver.common;

import junit.framework.*;
import org.aeroivr.appserver.admin.ServerAdmin;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.easymock.classextension.EasyMock.createStrictControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ServiceLocatorTest extends TestCase {
    
    public ServiceLocatorTest(String testName) {
        super(testName);
    }
    
    public void testLoadAndGetInstance() {
        ServiceLocator savedServiceLocator = ServiceLocator.getInstance();
        try {
            ServiceLocator serviceLocatorMock = createMock(
                    ServiceLocator.class);

            ServiceLocator.load(serviceLocatorMock);
            ServiceLocator currentServiceLocator = ServiceLocator.getInstance();
            assertTrue("References should be equal", 
                    serviceLocatorMock == currentServiceLocator);
        } finally {
            ServiceLocator.load(savedServiceLocator);
        }
    }

    public void testGetServerAdmin() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        assertTrue("ServerAdmin object should not be null",
                serviceLocator.getServerAdmin() != null);
        assertTrue("Wrong type of returned type",
                serviceLocator.getServerAdmin() instanceof ServerAdmin);
    }
}
