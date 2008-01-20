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
        System.loadLibrary("PTLib");
        System.loadLibrary("OpenH323");
        System.loadLibrary("OpenH323JNI");
    }

    private GetFileNameEventListener getFileNameEventListener;

    public OpenH323() {
    }

    protected native boolean init();
    protected native boolean startListening();
    protected native boolean shutdown();

    protected String getWavFileName()
        throws NotSetGetFileNameEventListenerException {

        if (null == getFileNameEventListener) {
            throw new NotSetGetFileNameEventListenerException();
        }
        return getFileNameEventListener.getWavFileName();
    }

    public void initialize() {
        this.init();
    }

    public void setGetFileNameEventListener(
            final GetFileNameEventListener evtListener) {
        getFileNameEventListener = evtListener;
    }

    public void start() {
        startListening();
    }

    public void stop() {
        shutdown();
    }
}
