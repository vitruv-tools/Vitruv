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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.ui.IEditorPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory;
import tools.vitruv.domains.emf.monitorededitor.IEditorPartAdapterFactory.IEditorPartAdapter;
import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor;
import tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock;
import tools.vitruv.domains.emf.monitorededitor.test.mocking.EclipseMock.SaveEventKind;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Files;
import tools.vitruv.domains.emf.monitorededitor.test.utils.BasicTestCase;
import tools.vitruv.domains.emf.monitorededitor.test.utils.ChangeAssert;
import tools.vitruv.domains.emf.monitorededitor.test.utils.DefaultImplementations;
import tools.vitruv.domains.emf.monitorededitor.test.utils.DefaultImplementations.TestVirtualModel;
import tools.vitruv.domains.emf.monitorededitor.test.utils.EnsureExecuted;
import tools.vitruv.domains.emf.monitorededitor.tools.EclipseAdapterProvider;
import tools.vitruv.domains.emf.monitorededitor.tools.IEclipseAdapter;
import tools.vitruv.framework.change.description.CompositeContainerChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.framework.util.datatypes.VURI;

public class VitruviusEMFEditorMonitorImplTests extends BasicTestCase {
    private EclipseMock eclipseMockCtrl;
    private IEclipseAdapter eclipseUtils;
    private IEditorPartAdapterFactory factory;

    @BeforeEach
    public void setUp() {
        this.eclipseMockCtrl = new EclipseMock();
        this.eclipseUtils = eclipseMockCtrl.getEclipseUtils();
        EclipseAdapterProvider.getInstance().setProvidedEclipseAdapter(eclipseUtils);
        this.factory = new DefaultEditorPartAdapterFactoryImpl(Files.ECORE_FILE_EXTENSION);
    }

    @Test
    public void newEditorsAreRegistered() {
        final URL modelURL = Files.EXAMPLEMODEL_ECORE;
        final EnsureExecuted ensureExecuted = new EnsureExecuted();

        IVitruviusAccessor va = new IVitruviusAccessor() {
            @Override
            public boolean isModelMonitored(VURI modelUri) {
                assert modelUri == VURI.getInstance(getURI(modelURL));
                ensureExecuted.markExecuted();
                return true;
            }
        };

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(
                DefaultImplementations.EFFECTLESS_VIRTUAL_MODEL, va);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        eclipseMockCtrl.openNewEMFTreeEditorPart(modelURL);

        syncMgr.dispose();
    }

    private EObject getRootObject(IEditorPart adaptableEditor) {
        IEditorPartAdapter adapter = this.factory.createAdapter(adaptableEditor);
        return (EObject) adapter.getEditingDomain().getRoot(EcoreFactory.eINSTANCE.createEClass());
    }

    @Test
    public void changesToAutomaticallyRegisteredEditorsAreSynchronized() {
        TestVirtualModel cs = TestVirtualModel.createInstance();

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory, cs,
                DefaultImplementations.ALL_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        IEditorPart editorPart = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        EPackage root = (EPackage) getRootObject(editorPart);
        root.setName(root.getName() + "!");

        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);
        syncMgr.triggerSynchronisation(VURI.getInstance(root.eResource()));

        assert cs.getExecutionCount() == 1;
        assert !cs.getLastChanges().isEmpty();

        for (VitruviusChange change : cs.getLastChanges()) {
            assert change.getChangedVURIs().iterator().next() == VURI.getInstance(getURI(Files.EXAMPLEMODEL_ECORE));
        }
    }

    @Test
    public void changesToRefusedEditorsAreNotSynchronized() {
        TestVirtualModel cs = TestVirtualModel.createInstance();

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory, cs,
                DefaultImplementations.NONE_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        IEditorPart editorPart = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        EPackage root = (EPackage) getRootObject(editorPart);
        root.setName(root.getName() + "!");

        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);
        syncMgr.triggerSynchronisation(VURI.getInstance(root.eResource()));

        assert !cs.hasBeenExecuted();
    }

    @Test
    public void changeListsAreJoinedWhenUserSavesMultipleTimesBeforeBuilding() {
        TestVirtualModel cs = TestVirtualModel.createInstance();

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory, cs,
                DefaultImplementations.ALL_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        IEditorPart editorPart = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        EPackage root = (EPackage) getRootObject(editorPart);
        root.setName("!" + root.getName());

        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);

        root.setNsPrefix("!" + root.getNsPrefix());

        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);

        syncMgr.triggerSynchronisation(VURI.getInstance(root.eResource()));

        assert cs.getExecutionCount() == 1 : "Got " + cs.getExecutionCount() + " syncs instead of 1.";
        assert !cs.getLastChanges().isEmpty();
        assert cs.hasBeenExecuted();
        assert cs.getLastChanges().size() == 1;
        assert cs.getLastChanges().get(0) instanceof CompositeContainerChange;
        int changeCount = ((CompositeContainerChange) cs.getLastChanges().get(0)).getChanges().size();
        assert changeCount == 2 : "Got " + changeCount + " changes instead of 2.";
    }

    @Test
    public void changeListsAreJoinedWhenMultipleModelInstancesAreEdited() {
        TestVirtualModel cs = TestVirtualModel.createInstance();

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory, cs,
                DefaultImplementations.ALL_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        IEditorPart editorPart1 = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        EPackage root1 = (EPackage) getRootObject(editorPart1);
        String root1NewName = "!" + root1.getName();
        root1.setName(root1NewName);
        String root1NewPrefix = "!" + root1.getNsPrefix();
        root1.setNsPrefix(root1NewPrefix);
        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);

        IEditorPart editorPart2 = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        EPackage root2 = (EPackage) getRootObject(editorPart2);
        String root2NewName = "?" + root2.getName();
        root2.setName(root2NewName);
        eclipseMockCtrl.issueSaveEvent(SaveEventKind.SAVE);

        syncMgr.triggerSynchronisation(VURI.getInstance(root1.eResource()));

        assert cs.getExecutionCount() == 1 : "Got " + cs.getExecutionCount() + " syncs instead of 1.";
        assert !cs.getLastChanges().isEmpty();
        assert cs.hasBeenExecuted();
        assert cs.getLastChanges().size() == 1;
        assert cs.getLastChanges().get(0) instanceof CompositeContainerChange;
        int changeCount = ((CompositeContainerChange) cs.getLastChanges().get(0)).getChanges().size();
        assert changeCount == 3 : "Got " + changeCount + " changes instead of 3.";

        FeatureEChange<?, ?> attrChange = (FeatureEChange<?, ?>) (cs.getLastChanges().get(0).getEChanges().get(0));
        EObject root = attrChange.getAffectedEObject();
        assert root instanceof EPackage;

        List<VitruviusChange> changes = new ArrayList<VitruviusChange>(
                ((CompositeContainerChange) cs.getLastChanges().get(0)).getChanges());
        // System.err.println(root);
        //
        // ChangeAssert.printChangeList(changes);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes, root.eClass().getEStructuralFeature("name"),
                root2NewName);
        ChangeAssert.assertContainsSingleValuedAttributeChange(changes, root.eClass().getEStructuralFeature("nsPrefix"),
                root1NewPrefix);

    }

    @Test
    public void modelCanBeMonitoredAfterEditorCreationWhenVitruviusDecidesItBecomesMonitorable() {
        RoleChangingVitruviusAccessor va = new RoleChangingVitruviusAccessor();
        va.setAcceptNone();

        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory,
                DefaultImplementations.EFFECTLESS_VIRTUAL_MODEL, va);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();

        eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);
        assert va.hasBeenExecuted();
        assert va.getAcceptedModels().isEmpty();

        va.setAcceptAll();
        syncMgr.addModel(VURI.getInstance(getURI(Files.EXAMPLEMODEL_ECORE)));

        assert va.getAcceptedModels().contains(VURI.getInstance(getURI(Files.EXAMPLEMODEL_ECORE)));
    }

    @Test
    public void modelIsStoppedBeingMonitoredWhenVitruviusDecidesItBecomesNotMonitorable() {
        VitruviusEMFEditorMonitorImpl syncMgr = new VitruviusEMFEditorMonitorImpl(factory,
                DefaultImplementations.EFFECTLESS_VIRTUAL_MODEL, DefaultImplementations.ALL_ACCEPTING_VITRUV_ACCESSOR);
        syncMgr.disableSynchronizationLagRecognition();
        syncMgr.initialize();
        ISynchronizingMonitoredEmfEditor listener = syncMgr.getChangeRecorderMonitor();

        IEditorPart editorPart = eclipseMockCtrl.openNewEMFTreeEditorPart(Files.EXAMPLEMODEL_ECORE);

        assert listener.isMonitoringEditor(editorPart);

        syncMgr.removeModel(VURI.getInstance(getURI(Files.EXAMPLEMODEL_ECORE)));

        assert !listener.isMonitoringEditor(editorPart);
    }

    private static class RoleChangingVitruviusAccessor implements IVitruviusAccessor {
        boolean accepts = true;
        boolean executed = false;
        private final List<VURI> acceptedModels = new ArrayList<>();

        @Override
        public boolean isModelMonitored(VURI modelUri) {
            executed = true;
            if (accepts) {
                acceptedModels.add(modelUri);
            }
            return accepts;
        }

        public void setAcceptAll() {
            accepts = true;
        }

        public void setAcceptNone() {
            accepts = false;
        }

        public boolean hasBeenExecuted() {
            return executed;
        }

        public List<VURI> getAcceptedModels() {
            return acceptedModels;
        }

    }
}
