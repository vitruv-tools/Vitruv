package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;

public class ProjectInput {

    private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
    private ClaimableMap<VURI, ViewType> uri2ViewTypeMap;
    private ClaimableConcatMap<VURI, Mapping> uris2MappingMap;

    public ProjectInput(final ClaimableMap<VURI, Metamodel> uri2MetamodelMap,
            final ClaimableMap<VURI, ViewType> uri2ViewTypeMap, final ClaimableConcatMap<VURI, Mapping> uris2MappingMap) {
        this.uri2MetamodelMap = uri2MetamodelMap;
        this.uri2ViewTypeMap = uri2ViewTypeMap;
        this.uris2MappingMap = uris2MappingMap;
    }

    public ClaimableMap<VURI, Metamodel> getUri2MetamodelMap() {
        return this.uri2MetamodelMap;
    }

    public ClaimableMap<VURI, ViewType> getUri2ViewTypeMap() {
        return this.uri2ViewTypeMap;
    }

    public ClaimableConcatMap<VURI, Mapping> getUris2MappingMap() {
        return this.uris2MappingMap;
    }

}
