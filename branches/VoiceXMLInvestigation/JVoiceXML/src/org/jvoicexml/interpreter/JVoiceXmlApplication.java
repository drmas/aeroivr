/*
 * File:    $RCSfile: JVoiceXmlApplication.java,v $
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

package org.jvoicexml.interpreter;

import java.net.URI;

import org.jvoicexml.Application;

/**
 * Implementation of the <code>Application</code>.
 *
 * @see org.jvoicexml.Application
 *
 * @author Dirk Schnelle
 * @version $Revision: 182 $
 *
 * <p>
 * Copyright &copy; 2005-2006 JVoiceXML group - <a
 * href="http://jvoicexml.sourceforge.net"> http://jvoicexml.sourceforge.net/
 * </a>
 * </p>
 */
public final class JVoiceXmlApplication
        implements Application {
    /** URI of the current document. */
    private URI uri;

    /**
     * Create a new object.
     *
     * @param initialUri
     *        URI of the initial doucument.
     */
    public JVoiceXmlApplication(final URI initialUri) {
        super();

        uri = initialUri;
    }

    /**
     * {@inheritDoc}
     */
    public URI getCurrentUri() {
        return uri;
    }
}
