package tools.vitruvius.framework.metarepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tools.vitruvius.framework.metamodel.Mapping;
import tools.vitruvius.framework.metamodel.MappingManaging;
import tools.vitruvius.framework.metamodel.Metamodel;
import tools.vitruvius.framework.metamodel.MetamodelManaging;
import tools.vitruvius.framework.util.datatypes.ClaimableConcatMap;
import tools.vitruvius.framework.util.datatypes.ClaimableHashMap;
import tools.vitruvius.framework.util.datatypes.ClaimableLexicographicalConcatHashMap;
import tools.vitruvius.framework.util.datatypes.ClaimableMap;
import tools.vitruvius.framework.util.datatypes.VURI;

public class MetaRepositoryImpl implements MetamodelManaging, MappingManaging {

    // TODO MK (meta repo): either rename all gets in interfaces to claim... instead of get... or
    // change
    // implementations so that they do
    // not claim but only get elements

    private ClaimableMap<VURI, Metamodel> uri2MetamodelMap;
    /**
     * Maps all file extensions of all registered metamodels to the respective metamodel. It is
     * ensured by {@link #addMetamodel(Metamodel) addMetamodel} that for every file extension at
     * most one metamodel is mapped.
     */
    private Map<String, Metamodel> fileExtension2MetamodelMap;
    private ClaimableConcatMap<VURI, Mapping> uris2MappingMap;

    public MetaRepositoryImpl() {
        this.uri2MetamodelMap = new ClaimableHashMap<VURI, Metamodel>();
        this.fileExtension2MetamodelMap = new HashMap<String, Metamodel>();
        this.uris2MappingMap = new ClaimableLexicographicalConcatHashMap<VURI, Mapping>();
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
        String[] fileExtensions = metamodel.getFileExtensions();
        for (String fileExtension : fileExtensions) {
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

    private void claimReferredMetamodels(final Mapping mapping) {
        this.uri2MetamodelMap.claimKeyIsMapped(mapping.getMetamodelA().getURI());
        this.uri2MetamodelMap.claimKeyIsMapped(mapping.getMetamodelB().getURI());
    }

    @Override
    public void addMapping(final Mapping mapping) {
        claimReferredMetamodels(mapping);
        this.uris2MappingMap.putClaimingNullOrSameMapped(mapping, mapping.getMetamodelA().getURI(),
                mapping.getMetamodelB().getURI());
    }

    @Override
    public Mapping getMapping(final VURI... metamodelURIs) {
        for (VURI metamodelURI : metamodelURIs) {
            this.uri2MetamodelMap.claimKeyIsMapped(metamodelURI);
        }
        return this.uris2MappingMap.claimValueForKeys(metamodelURIs);
    }

    @Override
    public Mapping getMapping(final Metamodel... metamodels) {
        VURI[] uris = new VURI[metamodels.length];
        for (int i = 0; i < metamodels.length; i++) {
            VURI uri = metamodels[i].getURI();
            uris[i] = uri;
        }
        return getMapping(uris);
    }

    @Override
    public Collection<Mapping> getAllMappings(final Metamodel metamodel) {
        VURI uri = metamodel.getURI();
        return this.uris2MappingMap.get(uri);
    }

    @Override
    public Metamodel[] getAllMetamodels() {
        return this.fileExtension2MetamodelMap.values()
                .toArray(new Metamodel[this.fileExtension2MetamodelMap.values().size()]);
    }
}
