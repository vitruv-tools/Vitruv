package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;


public class AbstractMetamodelsReferring implements MetamodelsReferring {
	private URI[] metamodelURIs;
	
	public AbstractMetamodelsReferring(URI... metamodelURIs) {
		this.metamodelURIs = metamodelURIs;
	}
	
	public URI[] getMetamodelURIs() {
		return metamodelURIs;
	}
}
