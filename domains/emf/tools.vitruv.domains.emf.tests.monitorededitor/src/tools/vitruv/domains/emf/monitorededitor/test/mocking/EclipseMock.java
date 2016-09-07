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

package tools.vitruv.domains.emf.monitorededitor.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;

import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;

public class EclipseMock {
    private final IEclipseAdapter eclipseUtils;
    private final Set<IEditorPart> currentEditors;
    private final EclipseWorkbenchMock workbenchMock;
    private IEditorPart activeEditorPart;

    private final Set<IExecutionListener> saveListeners = new HashSet<>();

    public EclipseMock() {
        eclipseUtils = createEclipseUtilsMock();
        workbenchMock = new EclipseWorkbenchMock();
        currentEditors = new HashSet<>();
    }

    private IEclipseAdapter createEclipseUtilsMock() {
        return new IEclipseAdapter() {
            @Override
            public IWorkbenchPage getActiveWorkbenchPage() {
                return workbenchMock.getActiveWorkbenchPage();
            }

            @Override
            public IEditorPart getActiveEditorPart() {
                return EclipseMock.this.activeEditorPart;
            }

            @Override
            public void addCommandServiceListener(IExecutionListener iel) {
                EclipseMock.this.saveListeners.add(iel);
            }

            @Override
            public Set<IEditorPart> getCurrentlyActiveEditors() {
                return EclipseMock.this.currentEditors;
            }

            @Override
            public void executeSynchronous(Runnable r) {
                r.run();
            }

            @Override
            public void removeCommandServiceListener(IExecutionListener iel) {
                EclipseMock.this.saveListeners.remove(iel);
            }

            @Override
            public IWorkbench getWorkbench() {
                return EclipseMock.this.workbenchMock.getWorkbench();
            }
        };
    }

    public IEditorPart openNewNonEMFEditorPart() {
        IEditorPart result = createNiceMock(IEditorPart.class);
        reset(result);
        replay(result);
        this.activeEditorPart = result;
        currentEditors.add(result);
        workbenchMock.changedEditorPart(result, EditorPartMgmtEvent.OPEN);
        return result;
    }

    public IEditorPart openNewNonEMFEditorPart(IWorkbenchPage page) {
        IEditorPart result = createNiceMock(IEditorPart.class);
        reset(result);
        replay(result);
        this.activeEditorPart = result;
        currentEditors.add(result);
        workbenchMock.changedEditorPart(result, EditorPartMgmtEvent.OPEN, page);
        return result;
    }

    public IEditorPart openNewEMFTreeEditorPart(URL modelURL) {
        MockEditingDomainFactory fact = new MockEditingDomainFactory();
        EditingDomain editingDomain = fact.createEditingDomain(modelURL);

        IEMFTreeEditor result = createNiceMock(IEMFTreeEditor.class);
        reset(result);
        expect(result.getEditingDomain()).andReturn(editingDomain).anyTimes();
        replay(result);

        this.activeEditorPart = result;
        currentEditors.add(result);
        workbenchMock.changedEditorPart(result, EditorPartMgmtEvent.OPEN);
        return result;
    }

    public IEditorPart openNewEMFDiagramEditorPart(URL modelURL, URL diagramURL) {
        MockEditingDomainFactory fact = new MockEditingDomainFactory();
        TransactionalEditingDomain editingDomain = fact.createEditingDomain(modelURL);

        // TODO: actually open diagram, this currently results in exception
        // Resource diagRes = Models.loadModel(diagramURL);
        // editingDomain.getResourceSet().getResources().add(diagRes);

        IGMFEditor result = createNiceMock(IGMFEditor.class);
        reset(result);
        expect(result.getEditingDomain()).andReturn(editingDomain).anyTimes();
        replay(result);

        this.activeEditorPart = result;
        currentEditors.add(result);
        workbenchMock.changedEditorPart(result, EditorPartMgmtEvent.OPEN);
        return result;
    }

    public void closeEditorPart(IEditorPart it) {
        currentEditors.remove(it);
        if (it == this.activeEditorPart) {
            if (!currentEditors.isEmpty()) {
                this.activeEditorPart = currentEditors.iterator().next();
            } else {
                this.activeEditorPart = null;
            }
        }
        workbenchMock.changedEditorPart(it, EditorPartMgmtEvent.CLOSE);
    }

    public void issueSaveEvent(SaveEventKind kind) {
        for (IExecutionListener listener : this.saveListeners) {
            listener.preExecute(kind.getEclipseActionID(), null);
            listener.postExecuteSuccess(kind.getEclipseActionID(), null);
        }
    }

    public enum SaveEventKind {
        SAVE(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE), SAVE_AS(
                org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE_AS), SAVE_ALL(
                        org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE_ALL);

        private final String eclipseActionID;

        private SaveEventKind(String eclipseActoinID) {
            this.eclipseActionID = eclipseActoinID;
        }

        public String getEclipseActionID() {
            return eclipseActionID;
        }
    }

    public enum EditorPartMgmtEvent {
        OPEN, CLOSE;
    }

    public IEclipseAdapter getEclipseUtils() {
        return this.eclipseUtils;
    }

    public boolean hasListeners() {
        return !workbenchMock.getPartListeners().isEmpty() || !saveListeners.isEmpty();
    }

    public EclipseWorkbenchMock getWorkbenchMockCtrl() {
        return this.workbenchMock;
    }
}
