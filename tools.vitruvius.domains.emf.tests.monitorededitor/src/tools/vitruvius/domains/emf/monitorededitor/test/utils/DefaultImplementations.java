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

package tools.vitruvius.domains.emf.monitorededitor.test.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruvius.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import tools.vitruvius.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.metamodel.ModelInstance;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.VURI;

public class DefaultImplementations {
    public static final ResourceChangeSynchronizing EFFECTLESS_CHANGESYNC = new ResourceChangeSynchronizing() {

        @Override
        public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
        }

    };

    public static final ChangeSynchronizing EFFECTLESS_EXTERNAL_CHANGESYNC = new ChangeSynchronizing() {
        @Override
        public List<List<VitruviusChange>> synchronizeChange(VitruviusChange change) {
            return null;
        }
    };

    public static final IVitruviusAccessor ALL_ACCEPTING_VITRUV_ACCESSOR = new IVitruviusAccessor() {

        @Override
        public boolean isModelMonitored(VURI modelUri) {
            return true;
        }
    };

    public static final IVitruviusAccessor NONE_ACCEPTING_VITRUV_ACCESSOR = new IVitruviusAccessor() {

        @Override
        public boolean isModelMonitored(VURI modelUri) {
            return false;
        }
    };

    public static final ModelProviding DEFAULT_MODEL_PROVIDING = new ModelProviding() {
        @Override
        public ModelInstance getAndLoadModelInstanceOriginal(VURI uri) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void saveExistingModelInstanceOriginal(VURI vuri) {
            // TODO Auto-generated method stub

        }

        @Override
        public void detachTransactionalEditingDomain() {
            // TODO Auto-generated method stub

        }

        @Override
        public void deleteModelInstanceOriginal(VURI vuri) {
            // TODO Auto-generated method stub

        }

        @Override
        public void forceReloadModelInstanceOriginalIfExisting(VURI modelURI) {
            // TODO Auto-generated method stub

        }

        @Override
        public void saveModelInstanceOriginalWithEObjectAsOnlyContent(VURI vuri, EObject rootEObject, TUID oldTUID) {
            // TODO Auto-generated method stub

        }

        @Override
        public void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable) {
            // TODO Auto-generated method stub

        }

        @Override
        public void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command) {
            // TODO Auto-generated method stub

        }

    };

    public static class TestChangeSynchronizing implements ResourceChangeSynchronizing, ChangeSynchronizing {
        private VURI lastVURI = null;
        private List<VitruviusChange> lastChanges = null;
        private int executionCount = 0;

        @Override
        public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
            this.lastChanges = new ArrayList<>();
            if (changes != null) {
                this.lastChanges.addAll(changes);
            }
            this.lastVURI = sourceModelURI;
            this.executionCount++;
        }

        public boolean hasBeenExecuted() {
            return executionCount > 0;
        }

        public int getExecutionCount() {
            return executionCount;
        }

        public List<VitruviusChange> getLastChanges() {
            return lastChanges;
        }

        public VURI getLastVURI() {
            return lastVURI;
        }

        public static TestChangeSynchronizing createInstance() {
            return new TestChangeSynchronizing();
        }

        @Override
        public List<List<VitruviusChange>> synchronizeChange(VitruviusChange changes) {
            synchronizeChanges(Collections.singletonList(changes), null, null);
            return null;
        }

    }
}
