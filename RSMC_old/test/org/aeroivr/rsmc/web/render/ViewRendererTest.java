/*
 * ViewRendererTest.java
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
import java.lang.reflect.Method;
import java.util.HashMap;
import junit.framework.TestCase;
import org.aeroivr.rsmc.common.ServiceLocator;
import static org.easymock.classextension.EasyMock.createStrictControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class ViewRendererTest extends TestCase {

    public ViewRendererTest(final String testName) {
        super(testName);
    }

    private void checkHtmlDocument(final String htmlDocument,
            final HashMap parameters, final String resultingHtmlDocument)
            throws NoSuchMethodException, IOException {

        final IMocksControl control = createStrictControl();
        final ServiceLocator serviceLocatorMock = control.createMock(
                ServiceLocator.class,
                new Method[] {
                    ServiceLocator.class.getMethod(
                            "getBufferedReaderForFile", String.class)});
        final BufferedReader bufferedReaderMock = control.createMock(
                BufferedReader.class);
        final String htmlFileName = "dummy.html";

        serviceLocatorMock.getBufferedReaderForFile(eq(htmlFileName));
        expectLastCall().andReturn(bufferedReaderMock).atLeastOnce();

        bufferedReaderMock.readLine();
        expectLastCall().andReturn(htmlDocument).once()
            .andReturn(null).atLeastOnce();

        control.replay();

        ServiceLocator savedServiceLocator = ServiceLocator.getInstance();
        ServiceLocator.load(serviceLocatorMock);
        try {
            ViewRenderer renderer = new ViewRenderer(parameters,
                htmlFileName);
            final String content = renderer.renderContent();

            assertNotNull("Content should be provided", content);
            assertEquals("Html document should be equal",
                    content, resultingHtmlDocument);
        } finally {
            ServiceLocator.load(savedServiceLocator);
        }

    }

    public void testRenderContentSenteces()
        throws NoSuchMethodException, IOException {

        final HashMap parameters = new HashMap();
        parameters.put("sentence", "test");

        final String htmlDocument = "<html> {sentence} hello  "
                + "{sentence} World  "
                + "{sentence} from test </html>";

        final String resultingHtmlDocument = "<html> test hello  "
                + "test World  "
                + "test from test </html>";

        checkHtmlDocument(htmlDocument, parameters, resultingHtmlDocument);
    }

    public void testRenderContentSenteceTrueFalseConditions()
        throws NoSuchMethodException, IOException {

        final HashMap parameters = new HashMap();
        parameters.put("sentence", "myTestSentence");
        parameters.put("trueCondition", true);
        parameters.put("falseCondition", false);

        final String htmlDocument = "<html> {sentence} [trueCondition] "
                + "trueCondition is here [/trueCondition] "
                + "[falseCondition] falseCondition should not be here "
                + "[/falseCondition] </html>";

        final String resultingHtmlDocument = "<html> myTestSentence  "
                + "trueCondition is here  "
                + " </html>";

        checkHtmlDocument(htmlDocument, parameters, resultingHtmlDocument);
    }

    public void testRenderContentInnerAndOuterConditions()
        throws NoSuchMethodException, IOException {

        final HashMap parameters = new HashMap();
        parameters.put("sentence", "myTestSentence");
        parameters.put("outerCondition", true);
        parameters.put("innerCondition", false);

        final String htmlDocument = "<html>  [outerCondition] "
                + "[innerCondition] {sentence} [/innerCondition] "
                + " [/outerCondition] </html>";

        final String resultingHtmlDocument = "<html>   "
                + " "
                + "  </html>";
        checkHtmlDocument(htmlDocument, parameters, resultingHtmlDocument);
    }

    public void testRenderContentWithOnlyClosingTag()
        throws NoSuchMethodException, IOException {

        final HashMap parameters = new HashMap();
        parameters.put("condition", true);

        final String htmlDocument = "<html>  test content "
                + " [/condition] </html>";

        final String resultingHtmlDocument = "<font color=\"Red\">There is "
                + "closing tag [/condition] without opening </font>";

        checkHtmlDocument(htmlDocument, parameters, resultingHtmlDocument);
    }
}
