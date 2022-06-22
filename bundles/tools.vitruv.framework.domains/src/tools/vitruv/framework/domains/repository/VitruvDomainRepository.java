package tools.vitruv.framework.domains.repository;

import tools.vitruv.framework.domains.VitruvDomain;

public interface VitruvDomainRepository extends Iterable<VitruvDomain> {
	VitruvDomain getDomainForFileExtension(String fileExtension);
	
	VitruvDomain getDomainForNsUri(String nsUri);
}
