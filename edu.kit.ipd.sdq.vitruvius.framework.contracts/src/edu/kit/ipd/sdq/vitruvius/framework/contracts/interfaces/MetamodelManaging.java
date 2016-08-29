package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public interface MetamodelManaging {
    void addMetamodel(Metamodel metamodel);

    Metamodel getMetamodel(VURI mmURI);

    Metamodel getMetamodel(String fileExtension);

    Metamodel[] getAllMetamodels();

    // TODO decide whether MetamodelManaging.remove(String uri) is needed
}
