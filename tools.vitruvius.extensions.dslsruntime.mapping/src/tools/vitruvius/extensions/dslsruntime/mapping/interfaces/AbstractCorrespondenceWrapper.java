package tools.vitruvius.extensions.dslsruntime.mapping.interfaces;

import tools.vitruvius.framework.correspondence.Correspondence;

public abstract class AbstractCorrespondenceWrapper {
	public Correspondence getCorrespondence() {
		return correspondence;
	}

	private Correspondence correspondence;
	
	public AbstractCorrespondenceWrapper(Correspondence correspondence) {
		this.correspondence = correspondence;
	}
}
