package tools.vitruv.framework.domains.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.tuid.Tuid;
import tools.vitruv.framework.util.datatypes.ClaimableHashMap;
import tools.vitruv.framework.util.datatypes.ClaimableMap;
import tools.vitruv.framework.util.datatypes.VURI;

public class VitruvDomainRepositoryImpl implements VitruvDomainRepository {

    private ClaimableMap<VURI, VitruvDomain> uri2MetamodelMap;
    /**
     * Maps all file extensions of all registered metamodels to the respective metamodel. It is
     * ensured by {@link #addMetamodel(AbstractVitruvDomain) addMetamodel} that for every file extension at
     * most one metamodel is mapped.
     */
    private Map<String, VitruvDomain> fileExtension2MetamodelMap;

    public VitruvDomainRepositoryImpl() {
        this.uri2MetamodelMap = new ClaimableHashMap<VURI, VitruvDomain>();
        this.fileExtension2MetamodelMap = new HashMap<String, VitruvDomain>();
    }

    @Override
    public void addDomain(final VitruvDomain domain) {
        VURI mainMMuri = domain.getURI();
        this.uri2MetamodelMap.putClaimingNullOrSameMapped(mainMMuri, domain);
        Set<String> nsURIs = domain.getNsUris();
        for (String nsURI : nsURIs) {
            VURI nsVURI = VURI.getInstance(nsURI);
            this.uri2MetamodelMap.put(nsVURI, domain);
        }
        for (String fileExtension : domain.getFileExtensions()) {
            VitruvDomain mappedMetamodel = this.fileExtension2MetamodelMap.get(fileExtension);
            if (mappedMetamodel != null) {
                throw new RuntimeException("The metamodel '" + domain
                        + "' cannot be registered for the file extension '" + fileExtension
                        + "' because the metamodel '" + mappedMetamodel + "' is already mapped to it!");
            }
            this.fileExtension2MetamodelMap.put(fileExtension, domain);
        }
    }

    @Override
    public VitruvDomain getDomain(final VURI uri) {
        return this.uri2MetamodelMap.get(uri);
    }

    @Override
    public VitruvDomain getDomain(final String fileExtension) {
        return this.fileExtension2MetamodelMap.get(fileExtension);
    }

    @Override
    public Iterator<VitruvDomain> iterator() {
        return this.fileExtension2MetamodelMap.values().iterator();
    }

	@Override
	public VitruvDomain getDomain(EObject object) {
		for (VitruvDomain domain : uri2MetamodelMap.values()) {
			if (domain.isInstanceOfDomainMetamodel(object)) {
				return domain;
			}
		}
		throw new IllegalStateException("No domain for given object <" + object + "> registered");
	}
	
	@Override
	public boolean hasDomain(EObject object) {
		for (VitruvDomain domain : uri2MetamodelMap.values()) {
			if (domain.isInstanceOfDomainMetamodel(object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public VitruvDomain getDomain(Tuid tuid) {
		for (VitruvDomain domain : uri2MetamodelMap.values()) {
			if (domain.hasTuid(tuid.toString())) {
				return domain;
			}
		}
		throw new IllegalStateException("No domain for given tuid <" + tuid+ "> registered");
	}
}
