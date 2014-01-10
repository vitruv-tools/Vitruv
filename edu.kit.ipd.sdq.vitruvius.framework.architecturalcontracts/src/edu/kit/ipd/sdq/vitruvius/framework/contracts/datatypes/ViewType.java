package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Set;

public class ViewType extends AbstractMetamodelsReferring implements URIHaving {
	private URI uri;
	
	public ViewType(URI uri, Set<URI> metamodelURIs) {
		super(metamodelURIs);
		this.uri = uri;
	}
	
	@Override
	public URI getURI() {
		return uri;
	}

}
