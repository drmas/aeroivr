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

import org.jvoicexml.SpokenInput;
import org.jvoicexml.event.error.NoresourceError;
import org.jvoicexml.implementation.ResourceFactory;
import org.jvoicexml.logging.Logger;
import org.jvoicexml.logging.LoggerFactory;

/**
 * Factory for JVoiceXML SpokenInput.
 *
 * @author Andriy Petlyovanyy
 */
public class SpokenInputFactory 
    implements ResourceFactory<SpokenInput> {
    
    private static final Logger LOGGER =
        LoggerFactory.getLogger(SpokenInputFactory.class);

    private int instances;
    

    public SpokenInput createResource() throws NoresourceError {

        final AudioInput input = new AudioInput();

        return input;
    }

    public int getInstances() {
        
        return instances;
    }
    
    public void setInstances(final int value) {
        instances = value;
    }

    public String getType() {

        return "jsapi10";
    }
}
