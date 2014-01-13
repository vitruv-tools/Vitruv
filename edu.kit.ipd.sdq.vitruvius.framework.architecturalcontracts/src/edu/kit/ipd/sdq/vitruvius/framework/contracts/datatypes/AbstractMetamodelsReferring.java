package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;


public class AbstractMetamodelsReferring implements MetamodelsReferring {
	private VURI[] metamodelURIs;
	
	public AbstractMetamodelsReferring(VURI... metamodelURIs) {
		this.metamodelURIs = metamodelURIs;
	}
	
	public VURI[] getMetamodelURIs() {
		return metamodelURIs;
	}
}
