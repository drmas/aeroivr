/*
 * PageRendererTest.java
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

import java.io.IOException;
import junit.framework.TestCase;
import org.aeroivr.rsmc.web.view.AbstractView;
import org.aeroivr.rsmc.web.view.MasterPageView;
import static org.easymock.classextension.EasyMock.createNiceControl;
import static org.easymock.classextension.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.eq;
import org.easymock.classextension.IMocksControl;

/**
 *
 * @author Andriy Petlyovanyy
 */
public class PageRendererTest extends TestCase {

    public PageRendererTest(final String testName) {
        super(testName);
    }

    public void testRenderContent() throws IOException {

        final IMocksControl control = createNiceControl();
        final MasterPageView masterPageViewMock = control.createMock(
                MasterPageView.class);
        final AbstractView viewMock = control.createMock(AbstractView.class);
        final String innerContent = "test inner content";
        final String content = "out " + innerContent;

        control.checkOrder(true);

        viewMock.getContent();
        expectLastCall().andReturn(innerContent).atLeastOnce();

        masterPageViewMock.setInnerContent(eq(innerContent));
        expectLastCall().atLeastOnce();

        masterPageViewMock.getContent();
        expectLastCall().andReturn(content).atLeastOnce();

        control.replay();

        final PageRenderer pageRenderer = new PageRenderer(masterPageViewMock,
                viewMock);
        final String renderResult = pageRenderer.renderContent();
        assertEquals("Rendering result should be equal",
                renderResult, content);

        control.verify();
    }

}
