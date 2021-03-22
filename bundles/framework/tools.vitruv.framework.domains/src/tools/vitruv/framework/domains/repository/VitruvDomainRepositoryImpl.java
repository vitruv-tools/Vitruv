package tools.vitruv.framework.domains.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.domains.VitruvDomain;
import static com.google.common.base.Preconditions.checkState;

public class VitruvDomainRepositoryImpl implements VitruvDomainRepository {
	private Map<String, VitruvDomain> nsUri2Domain = new HashMap<>();

	/**
	 * Maps all file extensions of all registered metamodels to the respective
	 * metamodel. We check in the constructor that for every file extension at most
	 * one metamodel is mapped.
	 */
	private Map<String, VitruvDomain> fileExtension2Domain = new HashMap<>();

	public VitruvDomainRepositoryImpl(Iterable<VitruvDomain> domains) {
		for (var domain : domains) {
			String mainPackageNsUri = domain.getMetamodelRootPackage().getNsURI();
			checkState(!nsUri2Domain.containsKey(mainPackageNsUri),
					"Duplicate metamodel registered for namespace " + mainPackageNsUri);
			nsUri2Domain.put(mainPackageNsUri, domain);
			for (String nsURI : domain.getNsUris()) {
				this.nsUri2Domain.put(nsURI, domain);
			}
			for (String fileExtension : domain.getFileExtensions()) {
				VitruvDomain mappedDomain = this.fileExtension2Domain.get(fileExtension);
				checkState(mappedDomain == null,
						"The domain'" + domain + "' cannot be registered for the file extension '" + fileExtension
								+ "' because the metamodel '" + mappedDomain + "' is already mapped to it!");
				this.fileExtension2Domain.put(fileExtension, domain);
			}
		}
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

}
