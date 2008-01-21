/*
 * File:    $HeadURL: https://svn.sourceforge.net/svnroot/jvoicexml/trunk/src/org/jvoicexml/JndiSupport.java $
 * Version: $LastChangedRevision: 222 $
 * Date:    $LastChangedDate: 2007-02-15 09:50:23 +0100 (Do, 15 Feb 2007) $
 * Author:  $LastChangedBy: schnelle $
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2007 JVoiceXML group - http://jvoicexml.sourceforge.net
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

package org.jvoicexml;

/**
 * JNDI support for remote client access to the VoiceXML interpreter.
 *
 * @author Dirk Schnelle
 * @version $LastChangedRevision: 222 $
 *
 * <p>
 * Copyright &copy; 2007 JVoiceXML group - <a
 * href="http://jvoicexml.sourceforge.net"> http://jvoicexml.sourceforge.net/
 * </a>
 * </p>
 *
 * @since 0.5.5
 */
public interface JndiSupport {
    /** Configuration key. */
    String CONFIG_KEY = "jndi";

    /**
     * Starts the service.
     */
    void startup();

    /**
     * Sets the reference to the interpreter.
     * @param jvxml the interpreter.
     */
    void setJVoiceXml(final JVoiceXml jvxml);

    /**
     * Stops the service.
     */
    void shutdown();

}
