package tools.vitruv.framework.domains.repository;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.util.datatypes.VURI;

public interface VitruvDomainRepository extends Iterable<VitruvDomain> {
    VitruvDomain getDomain(EObject object);
    boolean hasDomain(EObject object);
    VitruvDomain getDomain(VURI mmURI);
    VitruvDomain getDomain(String fileExtension);
}
