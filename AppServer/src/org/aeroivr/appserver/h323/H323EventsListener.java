/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aeroivr.appserver.h323;

/**
 *
 * @author Andriy
 */
public interface H323EventsListener {
    
    void onConnected(final String connectionId);
    void onDisconnected(final String connectionId);
    
    void onDtmf(final String connectionId, final char dtmf);
}
