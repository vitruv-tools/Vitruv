package tools.vitruv.framework.metarepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.metamodel.MetamodelRepository;
import tools.vitruv.framework.util.datatypes.ClaimableHashMap;
import tools.vitruv.framework.util.datatypes.ClaimableMap;
import tools.vitruv.framework.util.datatypes.VURI;

public class MetaRepositoryImpl implements MetamodelRepository {

    private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
    /**
     * Maps all file extensions of all registered metamodels to the respective metamodel. It is
     * ensured by {@link #addMetamodel(Metamodel) addMetamodel} that for every file extension at
     * most one metamodel is mapped.
     */
    private Map<String, Metamodel> fileExtension2MetamodelMap;

    public MetaRepositoryImpl() {
        this.uri2MetamodelMap = new ClaimableHashMap<VURI, Metamodel>();
        this.fileExtension2MetamodelMap = new HashMap<String, Metamodel>();
    }

    @Override
    public void addMetamodel(final Metamodel metamodel) {
        VURI mainMMuri = metamodel.getURI();
        this.uri2MetamodelMap.putClaimingNullOrSameMapped(mainMMuri, metamodel);
        Set<String> nsURIs = metamodel.getNsURIs();
        for (String nsURI : nsURIs) {
            VURI nsVURI = VURI.getInstance(nsURI);
            this.uri2MetamodelMap.put(nsVURI, metamodel);
        }
        for (String fileExtension : metamodel.getFileExtensions()) {
            Metamodel mappedMetamodel = this.fileExtension2MetamodelMap.get(fileExtension);
            if (mappedMetamodel != null) {
                throw new RuntimeException("The metamodel '" + metamodel
                        + "' cannot be registered for the file extension '" + fileExtension
                        + "' because the metamodel '" + mappedMetamodel + "' is already mapped to it!");
            }
            this.fileExtension2MetamodelMap.put(fileExtension, metamodel);
        }
    }

    @Override
    public Metamodel getMetamodel(final VURI uri) {
        return this.uri2MetamodelMap.get(uri);
    }

    @Override
    public Metamodel getMetamodel(final String fileExtension) {
        return this.fileExtension2MetamodelMap.get(fileExtension);
    }

    @Override
    public Iterator<Metamodel> iterator() {
        return this.fileExtension2MetamodelMap.values().iterator();
    }
}
