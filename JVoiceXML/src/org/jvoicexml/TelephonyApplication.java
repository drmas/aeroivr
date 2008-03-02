/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jvoicexml;

/**
 *
 * @author Andriy
 */
public interface TelephonyApplication {
    
    void playNewAudioFileFromVoiceXml(final String connectionId, 
            final String fileName);
    
    void onVoiceXmlSessionFinished(final String connectionId);
}
