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

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.HashSet;

import org.easymock.IAnswer;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock.SaveEventKind;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;
import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;

public class EclipseMockTests {

    private EclipseMock eclipseMockCtrl;
    private IEclipseAdapter eclipse;

    private static final IAnswer<Object> IS_ARG_IEDITORPART_ANSWER = new IAnswer<Object>() {

        @Override
        public Object answer() throws Throwable {
            Object[] args = getCurrentArguments();
            assert args.length == 1;
            assert args[0] != null;
            assert args[0] instanceof IWorkbenchPartReference;

            IWorkbenchPartReference ref = (IWorkbenchPartReference) args[0];
            assert ref.getPart(false) != null;
            assert ref.getPart(false) instanceof IEditorPart;

            return null;
        }
    };

    @BeforeEach
    public void setUp() {
        eclipseMockCtrl = new EclipseMock();
        eclipse = eclipseMockCtrl.getEclipseUtils();
        assert eclipse != null;
    }

    @Test
    public void returnsEclipseObjects() {
        eclipseMockCtrl.openNewNonEMFEditorPart();
        assert eclipse.getCurrentlyActiveEditors() != null;
        assert eclipse.getActiveWorkbenchPage() != null;
        assert eclipse.getActiveEditorPart() != null;
    }

    @Test
    public void activeEditorPartIsLastAdded() {
        IEditorPart currentPart;
        currentPart = eclipseMockCtrl.openNewNonEMFEditorPart();
        assert eclipse.getActiveEditorPart() == currentPart;

        currentPart = eclipseMockCtrl.openNewNonEMFEditorPart();
        assert eclipse.getActiveEditorPart() == currentPart;

        currentPart = eclipseMockCtrl.openNewNonEMFEditorPart();
        assert eclipse.getActiveEditorPart() == currentPart;
    }

    @Test
    public void activeEditorIsNullAfterLastRemoval() {
        eclipseMockCtrl.openNewNonEMFEditorPart();
        eclipseMockCtrl.openNewNonEMFEditorPart();

        for (IEditorPart part : new HashSet<>(eclipse.getCurrentlyActiveEditors())) {
            eclipseMockCtrl.closeEditorPart(part);
        }

        assert eclipse.getActiveEditorPart() == null;
    }

    @Test
    public void executionListenerIsFiredOnEditorOpen() {
        IPartListener2 createEditorListener = createStrictMock(IPartListener2.class);
        reset(createEditorListener);
        createEditorListener.partOpened(isA(IWorkbenchPartReference.class));
        expectLastCall().andAnswer(IS_ARG_IEDITORPART_ANSWER).times(1);
        replay(createEditorListener);

        eclipse.getActiveWorkbenchPage().addPartListener(createEditorListener);
        eclipseMockCtrl.openNewNonEMFEditorPart();

        verify(createEditorListener);
    }

    @Test
    public void executionListenerIsFiredOnEMFTreeEditorOpen() {
        IPartListener2 createEditorListener = createStrictMock(IPartListener2.class);
        reset(createEditorListener);
        createEditorListener.partOpened(isA(IWorkbenchPartReference.class));
        expectLastCall().andAnswer(IS_ARG_IEDITORPART_ANSWER).times(1);
        replay(createEditorListener);

        eclipse.getActiveWorkbenchPage().addPartListener(createEditorListener);
        eclipseMockCtrl.openNewEMFTreeEditorPart(Files.DATATYPE_ECORE);

        verify(createEditorListener);
    }

    @Test
    public void executionListenerIsFiredOnGMFEditorOpen() {
        IPartListener2 createEditorListener = createStrictMock(IPartListener2.class);
        reset(createEditorListener);
        createEditorListener.partOpened(isA(IWorkbenchPartReference.class));
        expectLastCall().andAnswer(IS_ARG_IEDITORPART_ANSWER).times(1);
        replay(createEditorListener);

        eclipse.getActiveWorkbenchPage().addPartListener(createEditorListener);
        eclipseMockCtrl.openNewEMFDiagramEditorPart(Files.EXAMPLEMODEL_ECORE, Files.EXAMPLEMODEL_ECOREDIAG);

        verify(createEditorListener);
    }

    @Test
    public void executionListenerIsFiredOnMultipleEditorOpen() {
        IPartListener2 createEditorListener = createStrictMock(IPartListener2.class);
        reset(createEditorListener);
        createEditorListener.partOpened(isA(IWorkbenchPartReference.class));
        expectLastCall().andAnswer(IS_ARG_IEDITORPART_ANSWER).times(3);
        replay(createEditorListener);

        eclipse.getActiveWorkbenchPage().addPartListener(createEditorListener);
        eclipseMockCtrl.openNewNonEMFEditorPart();
        eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EMPTY_ECORE);
        eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EMPTY_ECORE);
        verify(createEditorListener);
    }

    @Test
    public void executionListenerIsFiredOnEditorClose() {
        IPartListener2 createEditorListener = createStrictMock(IPartListener2.class);
        reset(createEditorListener);
        createEditorListener.partClosed(isA(IWorkbenchPartReference.class));
        expectLastCall().times(1);
        replay(createEditorListener);

        IEditorPart editor = eclipseMockCtrl.openNewNonEMFEditorPart();
        eclipse.getActiveWorkbenchPage().addPartListener(createEditorListener);
        eclipseMockCtrl.closeEditorPart(editor);

        verify(createEditorListener);
    }

    @Test
    public void saveListenersAreFiredOnSave() {
        IExecutionListener saveListener = createStrictMock(IExecutionListener.class);
        reset(saveListener);
        saveListener.preExecute(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE, null);
        expectLastCall().times(1);
        saveListener.postExecuteSuccess(org.eclipse.ui.IWorkbenchCommandConstants.FILE_SAVE, null);
        expectLastCall().times(1);
        replay(saveListener);

        eclipse.addCommandServiceListener(saveListener);
        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);

        verify(saveListener);
    }

    @Test
    public void synchronousExecute() {
        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        eclipse.executeSynchronous(new Runnable() {

            @Override
            public void run() {
                ensureExecuted.markExecuted();
            }

        });

        assert !ensureExecuted.isIndicatingFail() : "No execution took place.";
    }
}
