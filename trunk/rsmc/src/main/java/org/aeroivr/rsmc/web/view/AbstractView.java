/*
 * AbstractView.java
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

package org.aeroivr.rsmc.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.aeroivr.rsmc.web.render.ViewRenderer;
import org.aeroivr.utils.FileUtils;

/**
 * Base class for all views.
 *
 * @author Andriy Petlyovanyy
 */
public abstract class AbstractView {

    private final HashMap pageParameters = new HashMap();
    private final String viewsFolder;

    public AbstractView(final String vwsFolder) {
        this.viewsFolder = vwsFolder;
    }

    public AbstractView(final String vwsFolder, final Map parameters,
            final String... paramNames) {

        this.viewsFolder = vwsFolder;

        for (final String paramName : paramNames) {
            if (parameters.containsKey(paramName)) {
                final Object value = parameters.get(paramName);
                if (value instanceof String[]) {
                    pageParameters.put(paramName, ((String[]) value)[0]);
                } else {
                    pageParameters.put(paramName, value);
                }
            }
        }
    }

    protected Object getValue(final String key) {
        return pageParameters.get(key);
    }

    protected void setValue(final String key, final Object value) {
        pageParameters.put(key, value);
    }

    protected boolean containsKey(final String key) {
        return pageParameters.containsKey(key);
    }

    protected abstract String getHtmlFileName();

    public String getContent() throws IOException {

        final ViewRenderer renderer = new ViewRenderer(pageParameters,
                FileUtils.concatenatePath(viewsFolder, getHtmlFileName()));
        return renderer.renderContent();
    }
}
