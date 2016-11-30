package tools.vitruv.framework.metamodel;

import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.util.datatypes.VURI;

public interface MetamodelRepository extends Iterable<Metamodel> {
    void addMetamodel(Metamodel metamodel);
    Metamodel getMetamodel(VURI mmURI);
    Metamodel getMetamodel(String fileExtension);
}
