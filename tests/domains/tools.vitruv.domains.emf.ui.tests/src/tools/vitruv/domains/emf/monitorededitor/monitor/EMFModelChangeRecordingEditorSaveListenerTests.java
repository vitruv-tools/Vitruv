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

package tools.vitruv.domains.emf.monitorededitor.monitor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.ui.IEditorPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock.SaveEventKind;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;
import tools.vitruv.domains.emf.monitorededitor.test.utils.DefaultImplementations.TestVirtualModel;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureNotExecuted;
import tools.vitruv.domains.emf.monitorededitor.tools.EclipseAdapterProvider;
import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;
import tools.vitruv.framework.change.description.TransactionalChange;

public class EMFModelChangeRecordingEditorSaveListenerTests extends BasicTestCase {
    private EclipseMock eclipseCtrl;
    private IEclipseAdapter mockedEclipseUtils;

    private IEditorPart editorPart;
    private IEditorPartAdapter editorPartAdapter;

    private final EObject DUMMY_EOBJECT = EcoreFactory.eINSTANCE.createEClass();
    private TestVirtualModel virtualModel;
    
    @BeforeEach
    public void setUp() {
        this.eclipseCtrl = new EclipseMock();
        this.mockedEclipseUtils = eclipseCtrl.getEclipseUtils();
        EclipseAdapterProvider.getInstance().setProvidedEclipseAdapter(mockedEclipseUtils);
        DefaultEditorPartAdapterFactoryImpl adapterFactory = new DefaultEditorPartAdapterFactoryImpl(
                Files.ECORE_FILE_EXTENSION);
        editorPart = eclipseCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        editorPartAdapter = adapterFactory.createAdapter(editorPart);
        this.virtualModel = TestVirtualModel.createInstance();
        this.virtualModel.registerExistingModel(Files.EXAMPLEMODEL_ECORE);
    }

    @AfterEach
    public void tearDown() {
        assert !eclipseCtrl.hasListeners() : "Listeners were not fully removed from Eclipse";
    }

    @Test
    public void savesAreDetected() {
        final EnsureExecuted ensureExecuted = new EnsureExecuted();

        EMFModelChangeRecordingEditorSaveListener listener = new EMFModelChangeRecordingEditorSaveListener(
                editorPartAdapter) {
            @Override
            protected void onSavedResource(List<? extends TransactionalChange> changeDescriptions) {
                assert changeDescriptions != null;
                changeDescriptions.forEach((TransactionalChange descr) -> assertTrue(descr.getEChanges().isEmpty()));
                ensureExecuted.markExecuted();
            }
        };
        listener.initialize(virtualModel);

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        assert !ensureExecuted.isIndicatingFail() : "The save listenener was not executed.";
        listener.dispose();
    }

    @Test
    public void changesArePassedToTheListener() {
        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        final String newRootObjName = "Foobar";
        final EPackage rootObj = (EPackage) editorPartAdapter.getEditingDomain().getRoot(DUMMY_EOBJECT);

        // Set up the listener.
        EMFModelChangeRecordingEditorSaveListener listener = new EMFModelChangeRecordingEditorSaveListener(
                editorPartAdapter) {
            @Override
            protected void onSavedResource(List<? extends TransactionalChange> changeDescriptions) {
                assert changeDescriptions != null;
                // assert changeDescriptions.size() == 1;
                int counter = 0;
                for (TransactionalChange descr : changeDescriptions) {
                    counter += descr.getEChanges().size();
                }
                assert counter == 1;

                // assert
                // changeDescriptions.get(0).getChangeDescription().getObjectChanges().containsKey(rootObj);

                ensureExecuted.markExecuted();
            }
        };
        listener.initialize(virtualModel);

        // Change the model.
        rootObj.setName(newRootObjName);

        // Simulate a save event.
        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        assert !ensureExecuted.isIndicatingFail() : "The save listenener was not executed.";
        listener.dispose();
    }

    @Test
    public void savesForOtherEditorsAreIgnored() {
        eclipseCtrl.openNewEMFTreeEditorPart(Files.DATATYPE_ECORE);
        // Now, the currently active editor part in the mocked eclipse environment is ep

        final EnsureNotExecuted ensureNotExecuted = new EnsureNotExecuted();

        EMFModelChangeRecordingEditorSaveListener listener = new EMFModelChangeRecordingEditorSaveListener(
                editorPartAdapter) {
            @Override
            protected void onSavedResource(List<? extends TransactionalChange> changeDescriptions) {
                ensureNotExecuted.markExecuted();
            }
        };
        listener.initialize(virtualModel);

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        assert !ensureNotExecuted
                .isIndicatingFail() : "The save listener was executed though another editor was active.";
        listener.dispose();
    }
}
