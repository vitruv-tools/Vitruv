package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;

@FunctionalInterface
public interface CorrespondenceProvider {
	public Correspondence getCorrespondence();
}
