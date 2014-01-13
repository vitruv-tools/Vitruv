package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class AbstractURIHaving implements URIHaving {
	private VURI uri;
	
	public AbstractURIHaving(VURI uri) {
		this.uri = uri;
	}
	
	@Override
	public VURI getURI() {
		return this.uri;
	}

}
