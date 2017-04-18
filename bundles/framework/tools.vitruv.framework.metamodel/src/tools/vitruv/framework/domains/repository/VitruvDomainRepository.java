package tools.vitruv.framework.domains.repository;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.util.datatypes.VURI;

public interface VitruvDomainRepository extends Iterable<VitruvDomain> {
    void addDomain(VitruvDomain metamodel);
    VitruvDomain getDomain(VURI mmURI);
    VitruvDomain getDomain(String fileExtension);
}
