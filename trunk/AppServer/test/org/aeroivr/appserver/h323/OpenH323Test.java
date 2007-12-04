/*
 * OpenH323Test.java
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

package org.aeroivr.appserver.h323;

import java.lang.reflect.Method;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class OpenH323Test extends TestCase {

    public OpenH323Test(final String testName) {
        super(testName);
    }

    public void testInitialize() throws NoSuchMethodException {

        final OpenH323 openH323Mock = createMock(OpenH323.class,
                new Method[] {OpenH323.class.getDeclaredMethod("init")});

        openH323Mock.init();
        expectLastCall().once();

        replay(openH323Mock);

        openH323Mock.initialize();

        verify(openH323Mock);
    }

    public void testSetGetFileNameEventsListener() throws NoSuchMethodException,
            NotSetGetFileNameEventListenerException {

        final String fileName = "test.wav";
        final IMocksControl control = createStrictControl();
        final GetFileNameEventListener eventListenerMock =
                control.createMock(GetFileNameEventListener.class);
        final OpenH323 openH323Mock = control.createMock(OpenH323.class,
                new Method[] {OpenH323.class.getMethod("initialize")});

        eventListenerMock.getWavFileName();
        expectLastCall().andReturn(fileName).once();

        control.replay();

        openH323Mock.setGetFileNameEventListener(eventListenerMock);
        final String result;
        result = openH323Mock.getWavFileName();
        assertTrue("File name do not matched passed one", result == fileName);

        openH323Mock.setGetFileNameEventListener(null);
        boolean wasError = false;
        try {
            openH323Mock.getWavFileName();
        } catch (NotSetGetFileNameEventListenerException ex) {
            wasError = true;
        }

        assertTrue("Should throw exeption", wasError);

        control.verify();
    }

    public void testStart() throws NoSuchMethodException {

        final OpenH323 openH323Mock = createMock(OpenH323.class,
                new Method[] {OpenH323.class.getDeclaredMethod(
                        "startListening")});

        openH323Mock.startListening();
        expectLastCall().once();

        replay(openH323Mock);

        openH323Mock.start();

        verify(openH323Mock);
    }

    public void testStop() throws NoSuchMethodException {

        final OpenH323 openH323Mock = createMock(OpenH323.class,
                new Method[] {OpenH323.class.getDeclaredMethod("shutdown")});

        openH323Mock.shutdown();
        expectLastCall().once();

        replay(openH323Mock);

        openH323Mock.stop();

        verify(openH323Mock);
    }

}
