package tools.vitruv.extensions.dslsruntime.mapping.interfaces;

import tools.vitruv.framework.correspondence.Correspondence;

public abstract class AbstractCorrespondenceWrapper {
	public Correspondence getCorrespondence() {
		return correspondence;
	}

	private Correspondence correspondence;
	
	public AbstractCorrespondenceWrapper(Correspondence correspondence) {
		this.correspondence = correspondence;
	}
}
