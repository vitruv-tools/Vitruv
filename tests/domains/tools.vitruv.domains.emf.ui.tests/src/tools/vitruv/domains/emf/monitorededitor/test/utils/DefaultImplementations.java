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

package tools.vitruv.domains.emf.monitorededitor.test.utils;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruv.domains.emf.monitorededitor.test.testmodels.Models;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver;
import static tools.vitruv.framework.uuid.UuidGeneratorAndResolverFactory.createUuidGeneratorAndResolver;
import tools.vitruv.framework.uuid.UuidResolver;
import tools.vitruv.framework.vsum.VirtualModel;

public class DefaultImplementations {
    public static final ResourceChangeSynchronizing EFFECTLESS_CHANGESYNC = new ResourceChangeSynchronizing() {

        @Override
        public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
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

    public static class TestVirtualModel implements ResourceChangeSynchronizing, VirtualModel {
        private VURI lastVURI = null;
        private List<VitruviusChange> lastChanges = null;
        private int executionCount = 0;
        private UuidGeneratorAndResolver uuidGeneratorAndResolver = createUuidGeneratorAndResolver(new ResourceSetImpl());
        
        @Override
        public void synchronizeChanges(List<VitruviusChange> changes, VURI sourceModelURI, Resource res) {
            this.lastChanges = new ArrayList<>();
            if (changes != null) {
                this.lastChanges.addAll(changes);
            }
            this.lastVURI = sourceModelURI;
            this.executionCount++;
        }
        
        public void registerExistingModel(URL testResourceURL) {
        	Resource testResource = Models.loadModel(uuidGeneratorAndResolver.getResourceSet(), testResourceURL);
        	testResource.getAllContents().forEachRemaining(obj -> uuidGeneratorAndResolver.generateUuid(obj));
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

        public static TestVirtualModel createInstance() {
            return new TestVirtualModel();
        }

        @Override
        public List<PropagatedChange> propagateChange(VitruviusChange change) {
            synchronizeChanges(Collections.singletonList(change), null, null);
            return null;
        }

        @Override
        public ModelInstance getModelInstance(VURI modelVuri) {
            return null;
        }

        @Override
        public Path getFolder() {
            return null;
        }

        @Override
        public void reverseChanges(List<PropagatedChange> changes) {
        }

        @Override
        public UuidResolver getUuidResolver() {
        	return uuidGeneratorAndResolver;
        }

        @Override
        public List<PropagatedChange> propagateChangedState(Resource newState) {
            return null;
        }

        @Override
        public List<PropagatedChange> propagateChangedState(Resource newState, URI oldLocation) {
            return null;
        }

    }
}
