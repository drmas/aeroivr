/*
 * H323ApplicationTest.java
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
import org.aeroivr.appserver.common.BaseTestWithServiceLocator;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.common.Settings;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class H323ApplicationTest extends BaseTestWithServiceLocator {

    private IMocksControl control;
    private OpenH323 openH323Mock;
    private ServiceLocator serviceLocatorMock;

    public H323ApplicationTest(final String testName) {
        super(testName);
    }

    protected void setUp() throws NoSuchMethodException {
        control = createStrictControl();
        openH323Mock = control.createMock(OpenH323.class);
        serviceLocatorMock = control.createMock(ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getOpenH323")});
    }

    private void startOpenH323Sequence(final H323Application h323Application) {

        serviceLocatorMock.getOpenH323();
        expectLastCall().andReturn(openH323Mock).once();

        control.checkOrder(false);

        openH323Mock.initialize();
        expectLastCall().once();

        openH323Mock.setGetFileNameEventListener(h323Application);
        expectLastCall().once();

        control.checkOrder(true);

        openH323Mock.start();
        expectLastCall().once();
    }

    public void testStart() {

        final H323Application h323Application = ServiceLocator.getInstance(
                ).getH323Application();

        startOpenH323Sequence(h323Application);

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        h323Application.start();

        control.verify();
    }

    public void testStop() {

        final H323Application h323Application = ServiceLocator.getInstance(
                ).getH323Application();

        startOpenH323Sequence(h323Application);

        openH323Mock.stop();
        expectLastCall().once();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        h323Application.start();
        h323Application.stop();

        control.verify();
    }

    public void testGetWavFileName() throws NoSuchMethodException {

        final String wavFileName = "test.wav";
        final ServiceLocator serviceLocatorSettingsMock = control.createMock(
                ServiceLocator.class,
                new Method[] {ServiceLocator.class.getMethod(
                        "getSettings")});
        final Settings settingsMock = control.createMock(Settings.class);

        serviceLocatorSettingsMock.getSettings();
        expectLastCall().andReturn(settingsMock).once();

        settingsMock.getWavFileName();
        expectLastCall().andReturn(wavFileName).once();

        control.replay();

        ServiceLocator.load(serviceLocatorSettingsMock);
        H323Application h323Application = ServiceLocator.getInstance(
                ).getH323Application();
        final String result = h323Application.getWavFileName();
        assertEquals("Wav file name should be read from settings",
                wavFileName, result);

        control.verify();
    }

}
