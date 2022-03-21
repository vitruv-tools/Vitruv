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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.domains.emf.monitorededitor.ISynchronizingMonitoredEmfEditor.ResourceChangeSynchronizing;
import tools.vitruv.domains.emf.monitorededitor.IVitruviusEMFEditorMonitor.IVitruviusAccessor;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.propagation.ChangePropagationListener;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.vsum.VirtualModel;

public class DefaultImplementations {
	public static final ResourceChangeSynchronizing EFFECTLESS_CHANGESYNC = new ResourceChangeSynchronizing() {

		@Override
		public void synchronizeChanges(List<VitruviusChange> changes, URI sourceModelURI, Resource res) {
		}

	};

	public static final IVitruviusAccessor ALL_ACCEPTING_VITRUV_ACCESSOR = new IVitruviusAccessor() {

		@Override
		public boolean isModelMonitored(URI modelUri) {
			return true;
		}
	};

	public static final IVitruviusAccessor NONE_ACCEPTING_VITRUV_ACCESSOR = new IVitruviusAccessor() {

		@Override
		public boolean isModelMonitored(URI modelUri) {
			return false;
		}
	};

	public static class TestVirtualModel implements ResourceChangeSynchronizing, VirtualModel {
		private URI lastURI = null;
		private List<VitruviusChange> lastChanges = null;
		private int executionCount = 0;

		@Override
		public void synchronizeChanges(List<VitruviusChange> changes, URI sourceModelURI, Resource res) {
			this.lastChanges = new ArrayList<>();
			if (changes != null) {
				this.lastChanges.addAll(changes);
			}
			this.lastURI = sourceModelURI;
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

		public URI getLastURI() {
			return lastURI;
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
		public Path getFolder() {
			return null;
		}

		@Override
		public void addChangePropagationListener(ChangePropagationListener propagationListener) {
		}

		@Override
		public void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		}

		@Override
		public Collection<ViewType<?>> getViewTypes() {
			return null;
		}

		@Override
		public <S extends ViewSelector> S createSelector(ViewType<S> viewType) {
			return null;
		}

	}
}
