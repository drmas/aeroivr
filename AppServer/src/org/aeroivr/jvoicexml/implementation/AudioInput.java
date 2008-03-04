/*
 * SpokenInputFactory.java
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

package org.aeroivr.jvoicexml.implementation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Collection;
import org.jvoicexml.GrammarImplementation;
import org.jvoicexml.RemoteClient;
import org.jvoicexml.SpokenInput;
import org.jvoicexml.event.error.BadFetchError;
import org.jvoicexml.event.error.NoresourceError;
import org.jvoicexml.event.error.UnsupportedFormatError;
import org.jvoicexml.event.error.UnsupportedLanguageError;
import org.jvoicexml.implementation.ObservableUserInput;
import org.jvoicexml.implementation.UserInputListener;
import org.jvoicexml.logging.Logger;
import org.jvoicexml.logging.LoggerFactory;
import org.jvoicexml.xml.srgs.GrammarType;
import org.jvoicexml.xml.vxml.BargeInType;

/**
 * Audio input class.
 *
 * @author Andriy Petlyovanyy
 */
public class AudioInput
        implements SpokenInput, ObservableUserInput {
    
    private static final Logger LOGGER =
        LoggerFactory.getLogger(AudioInput.class);
    

    public Collection<GrammarType> getSupportedGrammarTypes() {
        return null;
    }

    public void activateGrammars(
            Collection<GrammarImplementation<? extends Object>> grammars) 
            throws BadFetchError, UnsupportedLanguageError, NoresourceError {
    }

    public void deactivateGrammars(
            Collection<GrammarImplementation<? extends Object>> grammars) 
            throws NoresourceError, BadFetchError {
    }

    public GrammarImplementation<? extends Object> loadGrammar(
            Reader reader, GrammarType type) 
            throws NoresourceError, BadFetchError, UnsupportedFormatError {

        return null;
    }

    public GrammarImplementation<? extends Object> newGrammar(
            String name, GrammarType type) throws NoresourceError {
        
        return null;
    }

    public Collection<BargeInType> getSupportedBargeInTypes() {
        return null;
    }

    public void record(OutputStream out) throws NoresourceError {
    }

    public void activate() {
    }

    public void passivate() {
    }

    public String getType() {
        return "jsapi10";
    }

    public void open() throws NoresourceError {
    }

    public void close() {
    }

    public void connect(RemoteClient client) throws IOException {
    }

    public void startRecognition() throws NoresourceError, BadFetchError {
    }

    public void stopRecognition() {
    }

    public void setUserInputListener(UserInputListener listener) {
    }

}
