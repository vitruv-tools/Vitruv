package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableConcatMap;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.ClaimableMap;

public class ProjectInput {

    private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
    private ClaimableConcatMap<VURI, Mapping> uris2MappingMap;

    public ProjectInput(final ClaimableMap<VURI, Metamodel> uri2MetamodelMap,
            final ClaimableConcatMap<VURI, Mapping> uris2MappingMap) {
        this.uri2MetamodelMap = uri2MetamodelMap;
        this.uris2MappingMap = uris2MappingMap;
    }

    public ClaimableMap<VURI, Metamodel> getUri2MetamodelMap() {
        return this.uri2MetamodelMap;
    }

    public ClaimableConcatMap<VURI, Mapping> getUris2MappingMap() {
        return this.uris2MappingMap;
    }

}
