package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;


public class ViewType extends AbstractMetamodelsReferring implements URIHaving {
	private VURI uri;
	
	public ViewType(VURI uri, VURI... metamodelURIs) {
		super(metamodelURIs);
		this.uri = uri;
	}
	
	@Override
	public VURI getURI() {
		return uri;
	}

}
