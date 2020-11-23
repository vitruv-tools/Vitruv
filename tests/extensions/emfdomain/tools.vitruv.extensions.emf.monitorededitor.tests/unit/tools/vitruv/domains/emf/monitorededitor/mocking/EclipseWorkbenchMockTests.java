/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.mocking;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseWorkbenchMock;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecutedOnObj;

public class EclipseWorkbenchMockTests {
    private EclipseWorkbenchMock eclipseWorkbenchMock;
    private IWorkbench workbench;

    @BeforeEach
    public void setUp() {
        eclipseWorkbenchMock = new EclipseWorkbenchMock();
        workbench = eclipseWorkbenchMock.getWorkbench();
    }

    @Test
    public void initiallyHasAPage() {
        IWorkbench workbench = eclipseWorkbenchMock.getWorkbench();
        IWorkbenchWindow[] wnds = workbench.getWorkbenchWindows();

        assert wnds != null;
        assert wnds.length == 1;

        IWorkbenchPage[] pages = wnds[0].getPages();

        assert pages != null;
        assert pages.length == 1;
        assert pages[0] != null;

        assert pages[0] == eclipseWorkbenchMock.getActiveWorkbenchPage();
    }

    @Test
    public void addWindowCausesListenersToBeFired() {
        final EnsureExecutedOnObj<IWorkbenchWindow> ensureExecuted = new EnsureExecutedOnObj<>();

        workbench.addWindowListener(new IWindowListener() {
            @Override
            public void windowOpened(IWorkbenchWindow window) {
                assert window != null;
                ensureExecuted.markExecuted(window);
            }

            @Override
            public void windowDeactivated(IWorkbenchWindow window) {
            }

            @Override
            public void windowClosed(IWorkbenchWindow window) {
            }

            @Override
            public void windowActivated(IWorkbenchWindow window) {
            }
        });

        IWorkbenchWindow newWnd = eclipseWorkbenchMock.createWindow();

        assert !ensureExecuted.isIndicatingFail(newWnd) : "Window open listener has not been fired or got wrong object";
    }

    @Test
    public void removeWindowCausesListenersToBeFired() {
        final EnsureExecutedOnObj<IWorkbenchWindow> ensureExecuted = new EnsureExecutedOnObj<>();

        workbench.addWindowListener(new IWindowListener() {
            @Override
            public void windowOpened(IWorkbenchWindow window) {
            }

            @Override
            public void windowDeactivated(IWorkbenchWindow window) {
            }

            @Override
            public void windowClosed(IWorkbenchWindow window) {
                assert window != null;
                ensureExecuted.markExecuted(window);
            }

            @Override
            public void windowActivated(IWorkbenchWindow window) {
            }
        });

        IWorkbenchWindow newWnd = eclipseWorkbenchMock.createWindow();
        eclipseWorkbenchMock.disposeWindow(newWnd);

        assert !ensureExecuted
                .isIndicatingFail(newWnd) : "Window close listener has not been fired or got wrong object";
    }

    @Test
    public void addPageCausesListenersToBeFired() {
        final EnsureExecutedOnObj<IWorkbenchPage> ensureExecuted = new EnsureExecutedOnObj<>();

        IWorkbenchWindow wnd = workbench.getWorkbenchWindows()[0];
        wnd.addPageListener(new IPageListener() {

            @Override
            public void pageOpened(IWorkbenchPage page) {
                assert page != null;
                ensureExecuted.markExecuted(page);
            }

            @Override
            public void pageClosed(IWorkbenchPage page) {
            }

            @Override
            public void pageActivated(IWorkbenchPage page) {
            }
        });

        IWorkbenchPage newPage = eclipseWorkbenchMock.createPage(wnd);
        assert !ensureExecuted.isIndicatingFail(newPage) : "Page open listener has not been fired or got wrong object";
    }

    @Test
    public void removePageCausesListenersToBeFired() {
        final EnsureExecutedOnObj<IWorkbenchPage> ensureExecuted = new EnsureExecutedOnObj<>();

        IWorkbenchWindow wnd = workbench.getWorkbenchWindows()[0];
        wnd.addPageListener(new IPageListener() {

            @Override
            public void pageOpened(IWorkbenchPage page) {
            }

            @Override
            public void pageClosed(IWorkbenchPage page) {
                assert page != null;
                ensureExecuted.markExecuted(page);
            }

            @Override
            public void pageActivated(IWorkbenchPage page) {
            }
        });

        IWorkbenchPage newPage = eclipseWorkbenchMock.createPage(wnd);
        eclipseWorkbenchMock.disposePage(wnd, newPage);
        assert !ensureExecuted.isIndicatingFail(newPage) : "Page open listener has not been fired or got wrong object";
    }

    @Test
    public void addWorkbenchObjectsCausesListenerSetsToBeCreated() {
        IWorkbenchWindow newWnd = eclipseWorkbenchMock.createWindow();
        IWorkbenchPage newPage = eclipseWorkbenchMock.createPage(newWnd);

        assert eclipseWorkbenchMock.getWindowListeners() != null;
        assert eclipseWorkbenchMock.getWindowListeners().isEmpty();

        assert eclipseWorkbenchMock.getPageListeners(newWnd) != null;
        assert eclipseWorkbenchMock.getPageListeners(newWnd).isEmpty();

        assert eclipseWorkbenchMock.getPartListeners(newPage) != null;
        assert eclipseWorkbenchMock.getPartListeners(newPage).isEmpty();
    }
}
