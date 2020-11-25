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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruv.domains.emf.monitorededitor.IMonitoringDecider;
import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock.SaveEventKind;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;
import tools.vitruv.domains.emf.monitorededitor.test.utils.DefaultImplementations;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureNotExecuted;
import tools.vitruv.domains.emf.monitorededitor.tools.EclipseAdapterProvider;
import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.util.datatypes.VURI;

public class SynchronizingMonitoredEmfEditorImplTests extends BasicTestCase {
    private EclipseMock eclipseCtrl;
    private IEclipseAdapter mockedEclipseUtils;

    private IEditorPart editorPart;
    private IEditorPartAdapter editorPartAdapter;
    private IEditorPartAdapterFactory adapterFactory;

    private static final EObject DUMMY_EOBJECT = EcoreFactory.eINSTANCE.createEClass();

    @BeforeEach
    public void setUp() {
        this.eclipseCtrl = new EclipseMock();
        this.mockedEclipseUtils = eclipseCtrl.getEclipseUtils();
        EclipseAdapterProvider.getInstance().setProvidedEclipseAdapter(mockedEclipseUtils);
        adapterFactory = new DefaultEditorPartAdapterFactoryImpl(Files.ECORE_FILE_EXTENSION);
        editorPart = eclipseCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        editorPartAdapter = adapterFactory.createAdapter(editorPart);
    }

    @AfterEach
    public void tearDown() {
        assert !eclipseCtrl.hasListeners() : "Listeners were not fully removed from Eclipse";
    }

    @Test
    public void initializeCompletesWithoutFailures() {
        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null,
                DefaultImplementations.EFFECTLESS_CHANGESYNC, adapterFactory, IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();
        monitor.dispose();
    }

    @Test
    public void emfEditorsCreatedAfterInitializeAreMonitored() {
        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null,
                DefaultImplementations.EFFECTLESS_CHANGESYNC, adapterFactory, IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        IEditorPart emfEditor2 = eclipseCtrl.openNewEMFTreeEditorPart(Files.EMPTY_ECORE);
        assert monitor.isMonitoringEditor(emfEditor2);

        monitor.dispose();
    }

    @Test
    public void nonEmfEditorsAreNotMonitored() {
        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null,
                DefaultImplementations.EFFECTLESS_CHANGESYNC, adapterFactory, IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        IEditorPart nonEmfEditor = eclipseCtrl.openNewNonEMFEditorPart();
        assert !monitor.isMonitoringEditor(nonEmfEditor);

        monitor.dispose();
    }

    @Test
    public void noEditorsAreMonitoredWhenMonitoringDeciderSaysNo() {
        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null,
                DefaultImplementations.EFFECTLESS_CHANGESYNC, adapterFactory, new IMonitoringDecider() {
                    @Override
                    public boolean isMonitoringEnabled(IEditorPartAdapter editor) {
                        return false;
                    }
                });
        monitor.initialize();

        IEditorPart emfEditor2 = eclipseCtrl.openNewEMFTreeEditorPart(Files.EMPTY_ECORE);
        assert !monitor.isMonitoringEditor(emfEditor2);

        monitor.dispose();
    }

    @Test
    public void correctAdapterIsPassedToTheMonitoringDecider() {
        final EnsureExecuted ensureExecuted = new EnsureExecuted();

        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null,
                DefaultImplementations.EFFECTLESS_CHANGESYNC, adapterFactory, new IMonitoringDecider() {
                    @Override
                    public boolean isMonitoringEnabled(IEditorPartAdapter editor) {
                        assert editor.getEditorPart() == SynchronizingMonitoredEmfEditorImplTests.this.editorPart;
                        ensureExecuted.markExecuted();
                        return true;
                    }
                });

        monitor.initialize();

        monitor.dispose();

        assert !ensureExecuted.isIndicatingFail() : "The monitoring decider was not queried.";
    }

    @Test
    public void saveEventsCauseSyncForCurrentEditor() {
        Resource res = this.editorPartAdapter.getEditedModelResource();
        final VURI resVURI = VURI.getInstance(res);

        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        ResourceChangeSynchronizing cs = new ResourceChangeSynchronizing() {

            @Override
            public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
                ensureExecuted.markExecuted();
                assert sourceModelURI == resVURI;
            }
        };

        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null, cs, adapterFactory,
                IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        EPackage rootObj = (EPackage) this.editorPartAdapter.getEditingDomain().getRoot(DUMMY_EOBJECT);
        rootObj.setName(rootObj.getName() + "!");

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        monitor.dispose();
        assert !ensureExecuted.isIndicatingFail() : "Synchronization was not triggered.";
    }

    @Test
    public void saveEventsCauseNoSyncWhenNoChangesOccured() {
        final EnsureNotExecuted ensureNotExecuted = new EnsureNotExecuted();
        ResourceChangeSynchronizing cs = new ResourceChangeSynchronizing() {

            @Override
            public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
                ensureNotExecuted.markExecuted();
            }
        };

        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null, cs, adapterFactory,
                IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        monitor.dispose();
        assert !ensureNotExecuted.isIndicatingFail() : "Synchronization was erroneously triggered.";
    }

    @Test
    public void saveEventsCauseNoSyncWhenOtherEditorIsSaved() {
        Resource res = this.editorPartAdapter.getEditedModelResource();
        final VURI resVURI = VURI.getInstance(res);

        final EnsureNotExecuted ensureNotExecuted = new EnsureNotExecuted();
        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        ResourceChangeSynchronizing cs = new ResourceChangeSynchronizing() {

            @Override
            public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
                if (sourceModelURI == resVURI) {
                    ensureNotExecuted.markExecuted();
                } else {
                    ensureExecuted.markExecuted();
                }
            }
        };

        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null, cs, adapterFactory,
                IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        IEditorPart otherEditor = eclipseCtrl.openNewEMFTreeEditorPart(Files.EMPTY_ECORE);
        // now, otherEditor is the current editor.
        assert eclipseCtrl.getEclipseUtils().getActiveEditorPart() == otherEditor;

        EditingDomain dom = adapterFactory.createAdapter(otherEditor).getEditingDomain();

        EPackage rootObj = (EPackage) this.editorPartAdapter.getEditingDomain().getRoot(DUMMY_EOBJECT);
        rootObj.setName(rootObj.getName() + "!");

        EPackage rootObj2 = (EPackage) dom.getRoot(DUMMY_EOBJECT);
        rootObj2.setName(rootObj2.getName() + "!");

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        monitor.dispose();

        assert !ensureNotExecuted.isIndicatingFail() : "Synchronization was erroneously triggered for unsaved model.";
        assert !ensureExecuted.isIndicatingFail() : "Synchronization was not triggered for saved model.";
    }

    @Test
    public void resourceReloadCausesChangesToBeCleared() throws IOException {
        Resource res = this.editorPartAdapter.getEditedModelResource();
        final VURI resVURI = VURI.getInstance(res);

        final EnsureExecuted ensureExecuted = new EnsureExecuted();
        ResourceChangeSynchronizing cs = new ResourceChangeSynchronizing() {

            @Override
            public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
                ensureExecuted.markExecuted();
                assert sourceModelURI == resVURI;

                assert changes.size() == 1;
                VitruviusChange emfChange = changes.get(0);
                assert emfChange.getEChanges().get(0) instanceof FeatureEChange<?, ?>;
                FeatureEChange<?, ?> updateAttr = (FeatureEChange<?, ?>) emfChange.getEChanges().get(0);

                assert updateAttr.getAffectedFeature().getName().equals("nsPrefix") : "Unexpected feature change on "
                        + updateAttr.getAffectedFeature().getName();
            }
        };

        SynchronizingMonitoredEmfEditorImpl monitor = new SynchronizingMonitoredEmfEditorImpl(null, cs, adapterFactory,
                IMonitoringDecider.MONITOR_ALL);
        monitor.initialize();

        EPackage rootObj = (EPackage) this.editorPartAdapter.getEditingDomain().getRoot(DUMMY_EOBJECT);
        rootObj.setName(rootObj.getName() + "!");

        this.editorPartAdapter.getEditedModelResource().unload();
        this.editorPartAdapter.getEditedModelResource().load(Collections.EMPTY_MAP);

        System.out.println(".");
        rootObj = (EPackage) this.editorPartAdapter.getEditedModelResource().getContents().get(0);
        rootObj.setNsPrefix("!" + rootObj.getNsPrefix());

        eclipseCtrl.issueSaveEvent(SaveEventKind.SAVE);

        monitor.dispose();
        assert !ensureExecuted.isIndicatingFail() : "Synchronization was not triggered.";
    }

}
