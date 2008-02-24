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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aeroivr.appserver.common.ServiceLocator;
import org.jvoicexml.JVoiceXmlMain;
import org.jvoicexml.Session;
import org.jvoicexml.TelephonyApplication;
import org.jvoicexml.event.ErrorEvent;
import org.jvoicexml.event.error.NoresourceError;

/**
 * H323 connections management class
 *
 * @author Andriy Petlyovanyy
 */
public class H323Application implements H323EventsListener, 
        TelephonyApplication  {

    private OpenH323 openH323;
    private JVoiceXmlMain voiceXMLApp;
    private Map<String, Object> connectionsHash;

    public H323Application() {
        connectionsHash = Collections.synchronizedMap(
                new HashMap<String, Object>());
    }

    public void start() {
        if (null == voiceXMLApp) {
            try {
                voiceXMLApp = new JVoiceXmlMain();
                voiceXMLApp.setName("JVoiceXMLMain");
                voiceXMLApp.start();
                voiceXMLApp.waitStartupComplete();
            } catch (InterruptedException ex) {
                Logger.getLogger(H323Application.class.getName()
                        ).log(Level.SEVERE, null, ex);
            }
        }
        
        if (null == openH323) {
            openH323 = ServiceLocator.getInstance().getOpenH323();
            openH323.initialize();
            openH323.setH323EventsListener(this);
            openH323.start();
        }
    }

    public void stop() {
        if (null != openH323) {
            openH323.stop();
            openH323 = null;
        }
        
        if (null != voiceXMLApp) {
            voiceXMLApp.shutdown();
            voiceXMLApp.waitShutdownComplete();
        }
    }

    @Override
    public void onConnected(final String connectionId) {
        
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
                connectionsHash.put(connectionId, voiceXmlSession);
            } catch (ErrorEvent ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void onDtmf(final String connectionId, final char dtmf) {
        try {
            Session voiceXmlSession = getVoiceXmlSessionForConnectionId(
                    connectionId);
            voiceXmlSession.getCharacterInput().addCharacter(dtmf);
        } catch (NoresourceError ex) {
            Logger.getLogger(H323Application.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onDisconnected(final String connectionId) {
        try {
            Session voiceXmlSession = (Session) connectionsHash.remove(
                connectionId);
            voiceXmlSession.hangup();
            voiceXmlSession.waitSessionEnd();
            voiceXmlSession.close();
        } catch (ErrorEvent ex) {
            Logger.getLogger(
                    H323Application.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    @Override
    public void playNewAudioFile(final String connectionId, 
            final String fileName) {

        openH323.playAudioFile(connectionId, fileName);
    }
    
    private Session getVoiceXmlSessionForConnectionId(
            final String connectionId) {
        
            return (Session) connectionsHash.get(connectionId);
    }
}
