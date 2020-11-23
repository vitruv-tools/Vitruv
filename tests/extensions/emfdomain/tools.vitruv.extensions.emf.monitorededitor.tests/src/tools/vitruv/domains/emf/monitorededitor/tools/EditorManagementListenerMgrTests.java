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

package tools.vitruv.domains.emf.monitorededitor.tools;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseWorkbenchMock;
import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecutedOnObj;

public class EditorManagementListenerMgrTests extends BasicTestCase {
    private EclipseMock eclipseMock;
    private EclipseWorkbenchMock workbenchMock;
    private EditorManagementListenerMgr editorMgr;
    private IWorkbench workbench;

    @BeforeEach
    public void setUp() {
        eclipseMock = new EclipseMock();
        workbenchMock = eclipseMock.getWorkbenchMockCtrl();
        EclipseAdapterProvider.getInstance().setProvidedEclipseAdapter(eclipseMock.getEclipseUtils());

        editorMgr = new EditorManagementListenerMgr();
        editorMgr.initialize();
        workbench = workbenchMock.getWorkbench();
    }

    @Test
    public void workbenchChangeListenersAreInstalledOnInit() {
        assert workbenchMock.getWindowListeners().size() == 1;
        assert workbenchMock.getPageListeners(workbench.getWorkbenchWindows()[0]).size() == 1;
        assert workbenchMock.getPartListeners(workbenchMock.getActiveWorkbenchPage()).isEmpty();
    }

    @Test
    public void windowListenerIsInstalledOnWindowCreate() {
        IWorkbenchWindow newWnd = workbenchMock.createWindow();
        assert workbenchMock.getPageListeners(newWnd).size() == 1;
    }

    @Test
    public void windowListenerIsRemovedOnWindowRemove() {
        IWorkbenchWindow newWnd = workbenchMock.createWindow();

        // dispose asserts that no listeners are installed anymore after
        // they are called.
        workbenchMock.disposeWindow(newWnd);
    }

    @Test
    public void editorManagementListenerIsCalledForPageCreateAfterInit() {
        final EnsureExecutedOnObj<IEditorPart> ensureExecuted = new EnsureExecutedOnObj<>();

        IEditorManagementListener listener = new IEditorManagementListener() {

            @Override
            public void onCreated(IEditorPart editorPart) {
                ensureExecuted.markExecuted(editorPart);
            }

            @Override
            public void onClosed(IEditorPart editorPart) {
            }
        };

        editorMgr.addEditorManagementListener(listener);

        IEditorPart eP = eclipseMock.openNewNonEMFEditorPart();

        assert !ensureExecuted.isIndicatingFail(eP);
    }

    @Test
    public void editorManagementListenerIsCalledForNewPage() {
        final EnsureExecutedOnObj<IEditorPart> ensureExecuted = new EnsureExecutedOnObj<>();

        IEditorManagementListener listener = new IEditorManagementListener() {

            @Override
            public void onCreated(IEditorPart editorPart) {
                ensureExecuted.markExecuted(editorPart);
            }

            @Override
            public void onClosed(IEditorPart editorPart) {
            }
        };

        editorMgr.addEditorManagementListener(listener);
        IWorkbenchWindow wnd = workbenchMock.createWindow();
        IWorkbenchPage page = workbenchMock.createPage(wnd);

        IEditorPart eP = eclipseMock.openNewNonEMFEditorPart(page);

        assert !ensureExecuted.isIndicatingFail(eP);
    }

    @Test
    public void disposeEditorTest() {
        final EnsureExecutedOnObj<IEditorPart> ensureCreateExecuted = new EnsureExecutedOnObj<>();
        final EnsureExecutedOnObj<IEditorPart> ensureRemoveExecuted = new EnsureExecutedOnObj<>();

        IEditorManagementListener listener = new IEditorManagementListener() {
            boolean created = false;

            @Override
            public void onCreated(IEditorPart editorPart) {
                ensureCreateExecuted.markExecuted(editorPart);
                created = true;
            }

            @Override
            public void onClosed(IEditorPart editorPart) {
                if (created) {
                    ensureRemoveExecuted.markExecuted(editorPart);
                }
            }
        };

        editorMgr.addEditorManagementListener(listener);
        IWorkbenchWindow wnd = workbenchMock.createWindow();
        IWorkbenchPage page = workbenchMock.createPage(wnd);

        IEditorPart eP = eclipseMock.openNewNonEMFEditorPart(page);
        eclipseMock.closeEditorPart(eP);

        assert !ensureCreateExecuted.isIndicatingFail(eP);
        assert !ensureRemoveExecuted.isIndicatingFail(eP);
    }

    @Test
    public void disposeListenerTest() {
        final EnsureExecutedOnObj<IEditorPart> ensureExecuted = new EnsureExecutedOnObj<>();

        IEditorManagementListener listener = new IEditorManagementListener() {

            @Override
            public void onCreated(IEditorPart editorPart) {
                ensureExecuted.markExecuted(editorPart);
            }

            @Override
            public void onClosed(IEditorPart editorPart) {
            }
        };

        editorMgr.addEditorManagementListener(listener);
        IWorkbenchWindow wnd = workbenchMock.createWindow();
        IWorkbenchPage page = workbenchMock.createPage(wnd);

        IEditorPart eP = eclipseMock.openNewNonEMFEditorPart(page);

        assert !ensureExecuted.isIndicatingFail(eP);

        editorMgr.dispose();

        assert workbenchMock.getPartListeners(page).isEmpty() : "Remaining part listeners after dispose detected";
        assert workbenchMock.getPageListeners(wnd).isEmpty() : "Remaining page listeners after dispose detected";
        assert workbenchMock.getWindowListeners().isEmpty() : "Remaining window listeners after dispose detected";
    }
}
