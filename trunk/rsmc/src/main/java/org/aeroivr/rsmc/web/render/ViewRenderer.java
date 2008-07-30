/*
 * ViewRenderer.java
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

package org.aeroivr.rsmc.web.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.aeroivr.rsmc.common.ServiceLocator;

/**
 * Render view according to its parameters.
 *
 * @author Andriy Petlyovanyy
 */
public class ViewRenderer {

    private Map pageParameters;
    private String htmlFileName;

    public ViewRenderer(final Map parameters, final String htmlFile) {
        pageParameters = parameters;
        htmlFileName = htmlFile;
    }

    public String renderContent() throws IOException {
        StringBuilder content = new StringBuilder();
        readHtmlFromFile(content);

        for (String key : (Set<String>) pageParameters.keySet()) {
            processTextLabels(key, content);
            if (!processConditionalBlock(key, content)) {
                break;
            }
        }

        return content.toString();
    }

    private boolean processConditionalBlock(final String key,
            final StringBuilder content) {

        if (pageParameters.get(key) instanceof Boolean) {
            String startConditionalBlockTag = "[" + key + "]";
            String endConditionalBlockTag = "[/" + key + "]";

            int startTagIndex, endTagIndex;

            for (startTagIndex = content.indexOf(startConditionalBlockTag),
                    endTagIndex = content.indexOf(endConditionalBlockTag);
                    (startTagIndex > -1) && (endTagIndex > -1)
                        && (endTagIndex > startTagIndex);
                    startTagIndex =
                    content.indexOf(startConditionalBlockTag),
                    endTagIndex = content.indexOf(endConditionalBlockTag)) {

                if (((Boolean) pageParameters.get(key)).booleanValue()) {
                    content.delete(endTagIndex,
                            endTagIndex
                            + endConditionalBlockTag.length());
                    content.delete(startTagIndex,
                            startTagIndex
                            + startConditionalBlockTag.length());
                } else {
                    content.delete(startTagIndex, endTagIndex
                            + endConditionalBlockTag.length());
                }
            }

            return validateConditionalBlockPositions(endConditionalBlockTag,
                    endTagIndex, startTagIndex, startConditionalBlockTag,
                    content);
        } else {
            return true;
        }
    }

    private void processTextLabels(final String key,
            final StringBuilder content) {

        String insertTag = "{" + key + "}";

        for (int tagIndex = content.indexOf(insertTag); tagIndex > -1;
            tagIndex = content.indexOf(insertTag)) {

            content.replace(tagIndex, tagIndex + insertTag.length(),
                    (String) pageParameters.get(key));
        }
    }

    private void readHtmlFromFile(final StringBuilder content)
        throws IOException {

        BufferedReader htmlFile = ServiceLocator.getInstance(
                ).getBufferedReaderForFile(htmlFileName);
        for (String line = htmlFile.readLine(); null != line;
            line = htmlFile.readLine()) {

            content.append(line);
        }
    }

    private boolean validateConditionalBlockPositions(
            final String endConditionalBlockTag, final int endTagIndex,
            final int startTagIndex, final String startConditionalBlockTag,
            final StringBuilder content) {

        if ((startTagIndex == -1) && (endTagIndex != -1)) {
             addTemplateParsingError(content, "There is closing tag "
                     + endConditionalBlockTag + " without opening ");
            return false;
        }  else {
            if ((startTagIndex != -1) && (endTagIndex == -1)) {
                addTemplateParsingError(content,
                        "There is opening tag "
                        + startConditionalBlockTag + " without closing ");
                return false;
            }  else {
                if (startTagIndex > endTagIndex) {
                    addTemplateParsingError(content,
                            "The closing opening tag "
                            + endConditionalBlockTag
                            + " is before opening tag ");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private void addTemplateParsingError(final StringBuilder content,
            final String errorMessage) {

        content.delete(0, content.length());
        content.append("<font color=\"Red\">" + errorMessage + "</font>");
    }
}
