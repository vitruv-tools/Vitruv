package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Set;

public class AbstractMetamodelsReferring implements MetamodelsReferring {
	private Set<URI> metamodelURIs;
	
	public AbstractMetamodelsReferring(Set<URI> metamodelURIs) {
		this.metamodelURIs = metamodelURIs;
	}
	
	public Set<URI> getMetamodelURIs() {
		return metamodelURIs;
	}
}
