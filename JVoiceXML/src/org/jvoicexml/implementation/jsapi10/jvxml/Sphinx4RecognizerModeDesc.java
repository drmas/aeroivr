/*
 * File:    $RCSfile: Sphinx4RecognizerModeDesc.java,v $
 * Version: $Revision: 182 $
 * Date:    $Date: 2006-12-22 09:19:28 +0100 (Fr, 22 Dez 2006) $
 * Author:  $Author: schnelle $
 * State:   $State: Exp $
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2005-2006 JVoiceXML group - http://jvoicexml.sourceforge.net
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


package org.jvoicexml.implementation.jsapi10.jvxml;

import java.util.Locale;

import javax.speech.Engine;
import javax.speech.EngineCreate;
import javax.speech.EngineException;
import javax.speech.recognition.RecognizerModeDesc;

/**
 * JSAPI wrapper for sphinx4.
 *
 * @author Dirk Schnelle
 * @version $Revision: 182 $
 *
 * <p>
 * Copyright &copy; 2005-2006 JVoiceXML group -
 * <a href="http://jvoicexml.sourceforge.net">
 * http://jvoicexml.sourceforge.net/</a>
 * </p>
 */
public final class Sphinx4RecognizerModeDesc
        extends RecognizerModeDesc implements EngineCreate {
    /**
     * Construct a new object.
     */
    public Sphinx4RecognizerModeDesc() {
        super("sphinx4", null, Locale.US, true, true, null);
    }

    /**
     * Create an engine with the properties specified by this object. A new
     * engine should be created in the <code>DEALLOCATED</code> state.
     *
     * @return The created engine.
     *
     * @throws EngineException
     *         The engine defined by this EngineModeDesc could not be properly
     *         created.
     */
    public Engine createEngine()
            throws EngineException {
        return new Sphinx4Recognizer();
    }
}
