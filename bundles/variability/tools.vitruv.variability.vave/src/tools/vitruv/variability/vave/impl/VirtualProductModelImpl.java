package tools.vitruv.variability.vave.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.propagation.ChangePropagationSpecificationProvider;
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.framework.vsum.internal.VirtualModelImpl;
import tools.vitruv.variability.vave.VirtualProductModel;
import vavemodel.Configuration;

public class VirtualProductModelImpl extends VirtualModelImpl implements VirtualProductModel {

	private Configuration configuration;

	// original changes
	private Collection<VitruviusChange> deltas = new ArrayList<>();

	public VirtualProductModelImpl(final Configuration configuration, final VsumFileSystemLayout fileSystemLayout, final InternalUserInteractor userInteractor, final VitruvDomainRepository domainRepository, final ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
		super(fileSystemLayout, userInteractor, domainRepository, changePropagationSpecificationProvider);
		this.configuration = configuration;
	}

	@Override
	public synchronized List<PropagatedChange> propagateChange(final VitruviusChange change) {
		this.deltas.add(change);
		return super.propagateChange(change);
	}

	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}

	@Override
	public Collection<VitruviusChange> getDeltas() {
		return Collections.unmodifiableCollection(this.deltas);
	}

	@Override
	public void clearDeltas() {
		this.deltas.clear();
	}

}
