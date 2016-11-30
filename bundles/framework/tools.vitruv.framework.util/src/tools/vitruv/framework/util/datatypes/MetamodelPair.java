package tools.vitruv.framework.util.datatypes;

public class MetamodelPair extends Pair<VURI, VURI> {

	public MetamodelPair(VURI firstMetamodelVuri, VURI secondMetamodelVuri) {
		super(firstMetamodelVuri, secondMetamodelVuri);
	}
	
	public MetamodelPair(String firstMetamodelNamespace, String secondMetamodelNamespace) {
		super(VURI.getInstance(firstMetamodelNamespace), VURI.getInstance(secondMetamodelNamespace));
	}

}
