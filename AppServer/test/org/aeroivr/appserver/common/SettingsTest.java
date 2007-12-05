/*
 * SettingsTest.java
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.easymock.classextension.IMocksControl;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expect;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class SettingsTest extends BaseTestWithServiceLocator {

    private IMocksControl control;
    private Properties propertiesMock;
    private Settings settingsMock;
    private ServiceLocator serviceLocatorMock;

    public SettingsTest(final String testName) {
        super(testName);
    }

    protected void setUp() throws NoSuchMethodException {
        control = createStrictControl();
        propertiesMock = control.createMock(Properties.class);

        settingsMock = control.createMock(Settings.class,
                new Method[] {Settings.class.getDeclaredMethod("getSettingsFileName")});
        serviceLocatorMock = control.createMock(
                ServiceLocator.class, new Method[] {
                    ServiceLocator.class.getMethod("getFileAsInputStream",
                            String.class),
                    ServiceLocator.class.getMethod("getFileAsOutputStream",
                            String.class),
                    ServiceLocator.class.getMethod("getProperties")});
    }

    private void loadSequence() throws IOException {
        final String fileName = "testSettings.properties";

        final InputStream inputStream = new ByteArrayInputStream(new byte[0]);

        settingsMock.getSettingsFileName();
        expectLastCall().andReturn(fileName).once();

        control.checkOrder(false);

        expect(serviceLocatorMock.getFileAsInputStream(eq(fileName))
            ).andReturn(inputStream).once();

        serviceLocatorMock.getProperties();
        expectLastCall().andReturn(propertiesMock).once();

        control.checkOrder(true);

        propertiesMock.load(eq(inputStream));
        expectLastCall().once();
    }

    public void testLoadSettings() throws IOException {

        loadSequence();
        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        settingsMock.loadSettings();

        control.verify();
    }

    public void testSaveSettings() throws IOException {

        final String fileName = "testSettings.properties";
        final OutputStream outputStream = new ByteArrayOutputStream();

        loadSequence();

        settingsMock.getSettingsFileName();
        expectLastCall().andReturn(fileName).once();

        control.checkOrder(false);

        expect(serviceLocatorMock.getFileAsOutputStream(eq(fileName))
            ).andReturn(outputStream).once();

        control.checkOrder(true);

        propertiesMock.store(eq(outputStream), eq(""));
        expectLastCall().once();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        settingsMock.loadSettings();
        settingsMock.saveSettings();

        control.verify();

    }

    public void testGetWavFileName() throws IOException {
        final String wavFileName = "test.wav";

        loadSequence();

        expect(propertiesMock.getProperty(eq(settingsMock.WAV_FILE_NAME),
                eq(""))).andReturn(wavFileName).once();

        control.replay();

        ServiceLocator.load(serviceLocatorMock);
        settingsMock.loadSettings();

        assertEquals("Wav filename ", settingsMock.getWavFileName(),
                wavFileName);

        control.verify();
    }

    public void testGetInstance() {
        assertTrue("Settings object should not be null",
                null != Settings.getInstance());
    }

}
