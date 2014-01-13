package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;


public class ViewType extends AbstractMetamodelsReferring implements URIHaving {
	private URI uri;
	
	public ViewType(URI uri, URI... metamodelURIs) {
		super(metamodelURIs);
		this.uri = uri;
	}
	
	@Override
	public URI getURI() {
		return uri;
	}

}
