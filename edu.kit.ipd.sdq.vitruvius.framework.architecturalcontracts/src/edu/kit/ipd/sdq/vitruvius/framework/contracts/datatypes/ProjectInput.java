package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.ClaimableMap;

public class ProjectInput {

	private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
	private ClaimableMap<VURI, ViewType> uri2ViewTypeMap;
	private ClaimableConcatMap<VURI, Mapping> uris2MappingMap;

	public ProjectInput(ClaimableMap<VURI, Metamodel> uri2MetamodelMap,
			ClaimableMap<VURI, ViewType> uri2ViewTypeMap,
			ClaimableConcatMap<VURI, Mapping> uris2MappingMap) {
		this.uri2MetamodelMap = uri2MetamodelMap;
		this.uri2ViewTypeMap = uri2ViewTypeMap;
		this.uris2MappingMap = uris2MappingMap;
	}

	public ClaimableMap<VURI, Metamodel> getUri2MetamodelMap() {
		return uri2MetamodelMap;
	}

	public ClaimableMap<VURI, ViewType> getUri2ViewTypeMap() {
		return uri2ViewTypeMap;
	}

	public ClaimableConcatMap<VURI, Mapping> getUris2MappingMap() {
		return uris2MappingMap;
	}

}
