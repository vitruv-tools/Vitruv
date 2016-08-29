package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.CorrespondenceProvider;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.ElementProvider;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;

public abstract class AbstractWrapper implements ElementProvider {
	protected final ElementProvider elementProvider;
	protected final List<CorrespondenceProvider> requiredCorrespondenceProviders;

	public AbstractWrapper(List<CorrespondenceProvider> requiredCorrespondenceProviders, ElementProvider elementProvider) {
		this.requiredCorrespondenceProviders = requiredCorrespondenceProviders;
		this.elementProvider = elementProvider;
	}

	@Override
	public List<EObject> getElements() {
		return elementProvider.getElements();
	}

	public List<Correspondence> getRequiredCorrespondences() {
		return requiredCorrespondenceProviders.stream().map(it -> it.getCorrespondence()).collect(Collectors.toList());
	}

}