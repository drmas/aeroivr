/*
 * OpenH323.java
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

/**
 * OpenH323 library class in Java. Access library through JNI.
 *
 * @author Andriy Petlyovanyy
 */
public class OpenH323 {

    static {
        System.loadLibrary("PTLibd");
        System.loadLibrary("OpenH323d");
        System.loadLibrary("OpenH323JNI");
    }

    private H323EventsListener h323EventsListener;

    public OpenH323() {
    }

    protected native boolean init();
    protected native boolean startListening();
    protected native boolean shutdown();
    
    protected native void playAudioFileInChannel(final String token, 
            final String fileName);
    protected native void closeConnection(final String token);
    
    protected void onConnected(final String connectionId) {
        if (null != h323EventsListener) {
            h323EventsListener.onConnected(connectionId);
        } 
    }

    protected void onDisconnected(final String connectionId) {
        if (null != h323EventsListener) {
            h323EventsListener.onDisconnected(connectionId);
        } 
    }
    
    protected void onDtmf(final String connectionId, final char dtmf) {
        if (null != h323EventsListener) {
            h323EventsListener.onDtmf(connectionId, dtmf);
        } 
    }

    public void initialize() {
        this.init();
    }

    public void setH323EventsListener(
            final H323EventsListener evtListener) {
        h323EventsListener = evtListener;
    }

    public void start() {
        startListening();
    }

    public void stop() {
        shutdown();
    }
    
    public void playAudioFile(final String connectionId, 
            final String fileName) {
        
        playAudioFileInChannel(connectionId, fileName);
    }

    public void disconnect(final String connectionId) {
        closeConnection(connectionId);
    }
}
