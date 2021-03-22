package tools.vitruv.framework.domains.repository;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.domains.VitruvDomain;

public interface VitruvDomainRepository extends Iterable<VitruvDomain> {
	boolean hasDomain(EObject object);

	VitruvDomain getDomain(EObject object);

	VitruvDomain getDomain(String fileExtension);
}
