/*
 * File:    $RCSfile: SpeakablePlainText.java,v $
 * Version: $Revision: 145 $
 * Date:    $Date: 2006-11-29 09:27:38 +0100 (Mi, 29 Nov 2006) $
 * Author:  $Author: schnelle $
 * State:   $State: Exp $
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2006 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation;

import org.jvoicexml.SpeakableText;

/**
 * Plain text to be passed to the TTS engine.
 *
 *
 * @author Dirk Schnelle
 * @version $Revision: 145 $
 *
 * <p>
 * Copyright &copy; 2006 JVoiceXML group -
 * <a href="http://jvoicexml.sourceforge.net">
 * http://jvoicexml.sourceforge.net/</a>
 * </p>
 *
 * @since 0.4
 */
public final class SpeakablePlainText
        implements SpeakableText {
    /** Plain text to be spoken. */
    private final StringBuilder text;

    /**
     * Constructs a new object.
     */
    public SpeakablePlainText() {
        text = new StringBuilder();
    }

    /**
     * Constructs a new object.
     * @param str The text to be spoken.
     */
    public SpeakablePlainText(final String str) {
        this();

        text.append(str);
    }

    /**
     * {@inheritDoc}
     */
    public String getSpeakableText() {
        return text.toString();
    }

    /**
     * {@inheritDoc}
     */
    public SpeakableText appendSpeakableText(final String str) {
        if (text != null) {
            text.append(str);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSpeakableTextEmpty() {
        return text.length() == 0;
    }
}