/*
 * H323Application.java
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

import java.net.URI;
import java.net.URISyntaxException;
import org.aeroivr.appserver.common.ServiceLocator;
import org.aeroivr.appserver.common.Settings;
import org.jvoicexml.JVoiceXmlMain;
import org.jvoicexml.Session;
import org.jvoicexml.event.ErrorEvent;

/**
 * H323 connections management class
 *
 * @author Andriy Petlyovanyy
 */
public class H323Application implements GetFileNameEventListener  {

    private OpenH323 openH323;
    private JVoiceXmlMain voiceXMLApp;

    public H323Application() {
    }

    public void start() {
        if (null == voiceXMLApp) {
            voiceXMLApp = new JVoiceXmlMain();
            voiceXMLApp.setName("JVoiceXMLMain");
            voiceXMLApp.start();

//            voiceXMLApp..waitShutdownComplete();
//            voiceXMLApp..postShutdown();
        }
        
        if (null == openH323) {
            openH323 = ServiceLocator.getInstance().getOpenH323();
            openH323.initialize();
            openH323.setGetFileNameEventListener(this);
            openH323.start();
        }
    }

    public void stop() {
        if (null != openH323) {
            openH323.stop();
            openH323 = null;
        }
    }

    public String getWavFileName() {
        Settings settings = ServiceLocator.getInstance().getSettings();
        Session voiceXmlSession = null;
        try {
            voiceXmlSession = voiceXMLApp.createSession(null);
        } catch (ErrorEvent ex) {
            ex.printStackTrace();
        }
        if (null != voiceXmlSession) {
            try {
                voiceXmlSession.call(new URI("file:///H:/Projects/" +
                        "AeroIVR/Investigation/VoiceXML/simple.vxml"));
                voiceXmlSession.waitSessionEnd();
                voiceXmlSession.close();
            } catch (ErrorEvent ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
        return settings.getWavFileName();
    }
}
