package tools.vitruv.extensions.dslsruntime.mapping.interfaces;

import tools.vitruv.framework.correspondence.Correspondence;

@FunctionalInterface
public interface CorrespondenceProvider {
	public Correspondence getCorrespondence();
}
