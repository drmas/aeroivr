    /*
 * File:    $HeadURL: https://svn.sourceforge.net/svnroot/jvoicexml/trunk/src/org/jvoicexml/implementation/ObservableUserInput.java $
 * Version: $LastChangedRevision: 214 $
 * Date:    $Date: 2007-02-13 09:19:18 +0100 (Di, 13 Feb 2007) $
 * Author:  $LastChangedBy: schnelle $
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2007 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation;


/**
 * A {@link org.jvoicexml.UserInput} that can be monitored by
 * {@link UserInputListener}s.
 *
 * <p>
 * Implementations must implement this interface to propagate input events
 * to the interpreter.
 * </p>
 *
 * @author Dirk Schnelle
 * @version $Revision: 214 $
 *
 * <p>
 * Copyright &copy; 2007 JVoiceXML group - <a
 * href="http://jvoicexml.sourceforge.net"> http://jvoicexml.sourceforge.net/
 * </a>
 * </p>
 *
 * @since 0.5.5
 */
public interface ObservableUserInput {
    /**
     * Sets the listener for user input events.
     *
     * <p>
     * The implementation of this interface must notify the listener
     * about all events.
     * </p>
     *
     * <p>
     * <b>Note:</b> This method might not be called, e.g. if there is
     * no <code>UserInput</code>.
     * </p>
     *
     * @param listener The listener.
     * @since 0.5
     */
    void setUserInputListener(final UserInputListener listener);
}