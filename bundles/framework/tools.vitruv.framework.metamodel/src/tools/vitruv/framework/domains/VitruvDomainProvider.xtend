package tools.vitruv.framework.domains

import org.eclipse.core.runtime.Platform
import java.util.ArrayList
import java.util.List

interface VitruvDomainProvider<D extends VitruvDomain> {
	abstract def D getDomain();

	/**
	 * Retrieves the provider for the domain with the given name from the extensions registered to the VitruvDomain
	 * extension point.
	 * 
	 * @param name - the name of the domain to retrieve
	 */
	def static VitruvDomainProvider<?> getDomainProviderFromExtensionPoint(String name) {
		val allDomainProviders = getAllDomainProvidersFromExtensionPoint();
		val potentialDomainProvider = allDomainProviders.filter[domain.name.equals(name)];
		if (potentialDomainProvider.size == 0) {
			throw new IllegalStateException(
				'''No provider for domain with given name found: «name»'''
			);
		}
		if (potentialDomainProvider.size >
			1) {
			throw new IllegalStateException(
				'''More than one provider for domain with given name found: «FOR provider : potentialDomainProvider SEPARATOR "', "»«provider.domain.name»«ENDFOR»'''
			);
		}
		return potentialDomainProvider.get(0);
	}

	/**
	 * Retrieves all providers for domain from the extensions registered to the VitruvDomain
	 * extension point.
	 */
	def static Iterable<VitruvDomainProvider<?>> getAllDomainProvidersFromExtensionPoint() {
		val List<VitruvDomainProvider<?>> domainProvider = new ArrayList<VitruvDomainProvider<?>>();
		Platform.getExtensionRegistry().getConfigurationElementsFor(VitruvDomain.EXTENSION_POINT_ID).map [
			it.createExecutableExtension("class")
		].forEach[if(it instanceof VitruvDomainProvider<?>) domainProvider.add(it)];
		return domainProvider;
	}
}
