package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;

@FunctionalInterface
public interface CorrespondenceProvider {
	public Correspondence getCorrespondence();
}
