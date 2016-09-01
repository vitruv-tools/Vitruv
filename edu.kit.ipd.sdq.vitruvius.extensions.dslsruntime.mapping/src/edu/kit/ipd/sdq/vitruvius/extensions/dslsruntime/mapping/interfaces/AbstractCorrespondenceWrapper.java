package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.mapping.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence;

public abstract class AbstractCorrespondenceWrapper {
	public Correspondence getCorrespondence() {
		return correspondence;
	}

	private Correspondence correspondence;
	
	public AbstractCorrespondenceWrapper(Correspondence correspondence) {
		this.correspondence = correspondence;
	}
}
