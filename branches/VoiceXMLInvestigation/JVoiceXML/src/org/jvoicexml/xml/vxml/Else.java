/*
 * File:    $HeadURL: https://svn.sourceforge.net/svnroot/jvoicexml/trunk/src/org/jvoicexml/xml/vxml/Else.java $
 * Version: $LastChangedRevision: 197 $
 * Date:    $Date: 2007-01-31 09:49:04 +0100 (Mi, 31 Jan 2007) $
 * Author:  $LastChangedBy: schnelle $
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

package org.jvoicexml.xml.vxml;

import org.jvoicexml.xml.VoiceXmlNode;
import org.w3c.dom.Node;

/**
 * The <code>&lt;else&gt;</code> element is used for conditional logic.
 *
 * @see org.jvoicexml.xml.vxml.If
 * @see org.jvoicexml.xml.vxml.Elseif
 *
 * @author Steve Doyle
 * @version $Revision: 197 $
 *
 * <p>
 * Copyright &copy; 2005-2007 JVoiceXML group -
 * <a href="http://jvoicexml.sourceforge.net">
 * http://jvoicexml.sourceforge.net/</a>
 * </p>
 */
public final class Else
        extends AbstractVoiceXmlNode {

    /** Name of the tag. */
    public static final String TAG_NAME = "else";

    /**
     * Construct a new else object without a node.
     * <p>
     * This is necessary for the node factory.
     * </p>
     *
     * @see org.jvoicexml.xml.vxml.VoiceXmlNodeFactory
     */
    public Else() {
        super(null);
    }

    /**
     * Construct a new else object.
     * @param node The encapsulated node.
     */
    Else(final Node node) {
        super(node);
    }

    /**
     * Get the name of the tag for the derived node.
     *
     * @return name of the tag.
     */
    public String getTagName() {
        return TAG_NAME;
    }

    /**
     * Create a new instance for the given node.
     * @param n The node to encapsulate.
     * @return The new instance.
     */
    public VoiceXmlNode newInstance(final Node n) {
        return new Else(n);
    }

    /**
     *{@inheritDoc}
     */
    protected boolean canContainChild(final String tagName) {
        return false;
    }
}
