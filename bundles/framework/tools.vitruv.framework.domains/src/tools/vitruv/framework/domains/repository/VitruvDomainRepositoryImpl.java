package tools.vitruv.framework.domains.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.domains.TuidAwareVitruvDomain;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.tuid.Tuid;
import tools.vitruv.framework.util.datatypes.ClaimableHashMap;
import tools.vitruv.framework.util.datatypes.ClaimableMap;
import tools.vitruv.framework.util.datatypes.VURI;

public class VitruvDomainRepositoryImpl implements VitruvDomainRepository {
    private ClaimableMap<VURI, VitruvDomain> nsUri2Domain = new ClaimableHashMap<>();
    /**
     * Maps all file extensions of all registered metamodels to the respective metamodel. We check
     * in the constructor that for every file extension at most one metamodel is mapped.
     */
    private Map<String, VitruvDomain> fileExtension2Domain = new HashMap<>();

    public VitruvDomainRepositoryImpl(Iterable<VitruvDomain> domains) {
        for (var domain : domains) {
            VURI mainMMuri = domain.getURI();
            nsUri2Domain.putClaimingNullOrSameMapped(mainMMuri, domain);
            for (String nsURI : domain.getNsUris()) {
                VURI nsVURI = VURI.getInstance(nsURI);
                this.nsUri2Domain.put(nsVURI, domain);
            }
            for (String fileExtension : domain.getFileExtensions()) {
                VitruvDomain mappedMetamodel = this.fileExtension2Domain.get(fileExtension);
                if (mappedMetamodel != null) {
                    throw new RuntimeException("The metamodel '" + domain
                        + "' cannot be registered for the file extension '" + fileExtension
                        + "' because the metamodel '" + mappedMetamodel + "' is already mapped to it!");
                }
                this.fileExtension2Domain.put(fileExtension, domain);
            }
        }
    }

    @Override
    public VitruvDomain getDomain(final VURI uri) {
        return this.nsUri2Domain.get(uri);
    }

    @Override
    public VitruvDomain getDomain(final String fileExtension) {
        return this.fileExtension2Domain.get(fileExtension);
    }

    @Override
    public Iterator<VitruvDomain> iterator() {
        return this.fileExtension2Domain.values().iterator();
    }

    @Override
    public VitruvDomain getDomain(EObject object) {
        for (VitruvDomain domain : nsUri2Domain.values()) {
            if (domain.isInstanceOfDomainMetamodel(object)) {
                return domain;
            }
        }
        throw new IllegalStateException("No domain for given object <" + object + "> registered");
    }

    @Override
    public boolean hasDomain(EObject object) {
        for (VitruvDomain domain : nsUri2Domain.values()) {
            if (domain.isInstanceOfDomainMetamodel(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TuidAwareVitruvDomain getDomain(Tuid tuid) {
        for (VitruvDomain domain : nsUri2Domain.values()) {
            if (domain instanceof TuidAwareVitruvDomain) {
                TuidAwareVitruvDomain typedDomain = (TuidAwareVitruvDomain)domain;
                if (typedDomain.hasTuid(tuid.toString())) {
                    return typedDomain;
                }
            }
        }
        throw new IllegalStateException("No domain for given tuid <" + tuid+ "> registered");
    }
}
