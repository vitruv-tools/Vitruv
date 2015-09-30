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

package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelCopyProviding;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.IVitruviusEMFEditorMonitor.IVitruviusAccessor;

public class DefaultImplementations {
    public static final ResourceChangeSynchronizing EFFECTLESS_CHANGESYNC = new ResourceChangeSynchronizing() {

        @Override
        public void synchronizeChanges(List<Change> changes, VURI sourceModelURI, Resource res) {
        }

    };

    public static final ChangeSynchronizing EFFECTLESS_EXTERNAL_CHANGESYNC = new ChangeSynchronizing() {
        @Override
        public List<List<Change>> synchronizeChanges(List<Change> changes) {
        }

        @Override
        public void synchronizeChange(Change change) {
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

    public static final ModelCopyProviding DEFAULT_MODEL_COPY_PROVIDING = new ModelCopyProviding() {
        @Override
        public ModelInstance getModelInstanceCopy(VURI uri) {
            return null;
        }
    };

    public static class TestChangeSynchronizing implements ResourceChangeSynchronizing, ChangeSynchronizing {
        private VURI lastVURI = null;
        private List<Change> lastChanges = null;
        private int executionCount = 0;

        @Override
        public void synchronizeChanges(List<Change> changes, VURI sourceModelURI, Resource res) {
            this.lastChanges = changes;
            this.lastVURI = sourceModelURI;
            this.executionCount++;
        }

        public boolean hasBeenExecuted() {
            return executionCount > 0;
        }

        public int getExecutionCount() {
            return executionCount;
        }

        public List<Change> getLastChanges() {
            return lastChanges;
        }

        public VURI getLastVURI() {
            return lastVURI;
        }

        public static TestChangeSynchronizing createInstance() {
            return new TestChangeSynchronizing();
        }

        @Override
        public List<List<Change>> synchronizeChanges(List<Change> changes) {
            synchronizeChanges(changes, null, null);
        }

        @Override
        public void synchronizeChange(Change change) {
            synchronizeChanges(Collections.singletonList(change));
        }
    }
}
