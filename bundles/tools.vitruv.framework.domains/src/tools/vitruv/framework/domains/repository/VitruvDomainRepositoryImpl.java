package tools.vitruv.framework.domains.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tools.vitruv.framework.domains.VitruvDomain;
import static com.google.common.base.Preconditions.checkState;

public class VitruvDomainRepositoryImpl implements VitruvDomainRepository {
	/**
	 * Maps all file extensions of all registered metamodels to the respective
	 * metamodel. We check in the constructor that for every file extension at most
	 * one metamodel is mapped.
	 */
	private Map<String, VitruvDomain> fileExtension2Domain = new HashMap<>();

	public VitruvDomainRepositoryImpl(Iterable<VitruvDomain> domains) {
		for (var domain : domains) {
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
	public VitruvDomain getDomainForFileExtension(final String fileExtension) {
		return this.fileExtension2Domain.get(fileExtension);
	}

	@Override
	public Iterator<VitruvDomain> iterator() {
		return this.fileExtension2Domain.values().iterator();
	}

}
