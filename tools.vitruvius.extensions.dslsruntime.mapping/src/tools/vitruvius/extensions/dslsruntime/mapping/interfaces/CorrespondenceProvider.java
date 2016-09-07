package tools.vitruvius.extensions.dslsruntime.mapping.interfaces;

import tools.vitruvius.framework.correspondence.Correspondence;

@FunctionalInterface
public interface CorrespondenceProvider {
	public Correspondence getCorrespondence();
}
