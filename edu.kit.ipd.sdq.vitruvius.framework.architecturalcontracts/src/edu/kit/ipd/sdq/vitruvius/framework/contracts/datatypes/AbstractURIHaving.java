package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class AbstractURIHaving implements URIHaving {
	private URI uri;
	
	public AbstractURIHaving(URI uri) {
		this.uri = uri;
	}
	
	@Override
	public URI getURI() {
		return this.uri;
	}

}
