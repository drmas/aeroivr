/*
 * MasterPageView.java
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

import java.util.List;
import java.util.Map;

/**
 * View of the master page.
 *
 * @author Andriy Petlyovanyy
 */
public class MasterPageView extends AbstractView {

    private static final String SHOW_MENU = "showMenu";
    private static final String WERE_ERRORS = "wereErrors";
    private static final String ERRORS = "errors";
    private static final String INNER_CONTENT = "innerContent";
    private static final String ROOT_DIR_URL = "rootDirUrl";
    private static final String HEADER = "header";

    protected MasterPageView(final String viewsFolder, final Map parameters) {
        super(viewsFolder, parameters);
    }

    public MasterPageView(final String viewsFolder, final String rootDirUrl) {
        super(viewsFolder);
        setRootDirUrl(rootDirUrl);
    }

    private void setRootDirUrl(final String value) {
        setValue(ROOT_DIR_URL, value);
    }

    public void setHeader(final String value) {
        setValue(HEADER, value);
    }

    public void setInnerContent(final String value) {
        setValue(INNER_CONTENT, value);
    }

    public void setShowMenu(final boolean value) {
        setValue(SHOW_MENU, value);
    }

    protected String getHtmlFileName() {
        return "masterPage.html";
    }

    public void setErrors(List<String> errors) {
        StringBuilder html = new StringBuilder();
        if ((null != errors) && (0 < errors.size())) {
            setValue(WERE_ERRORS, true);
            for (String error : errors) {
                html.append("<li/> " + error);
            }
            setValue(ERRORS, html.toString());
        } else {
            setValue(WERE_ERRORS, false);
        }
    }

}
