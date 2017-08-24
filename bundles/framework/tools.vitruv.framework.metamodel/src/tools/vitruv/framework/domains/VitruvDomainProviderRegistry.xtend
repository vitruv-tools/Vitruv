package tools.vitruv.framework.domains

import java.util.HashMap
import org.eclipse.core.runtime.Platform
import java.util.List
import java.util.LinkedList
import static com.google.common.base.Preconditions.*

/**
 * Registry of all {@linkplain VitruvDomainProvider DomainProviders} known to
 * Vitruvius. Domain Providers are usually registered by providing them at the
 * extension point {@link #EXTENSION_POINT_ID}. They can, however, also be
 * registered at runtime.
 */
class VitruvDomainProviderRegistry {

	private new() {
	}

	static val runtimeRegisteredProviders = new HashMap<String, VitruvDomainProvider<?>>
	public static val EXTENSION_POINT_ID = "tools.vitruv.framework.metamodel.domain"

	/**
	 * Registers a domain provider that provides the domain {@code domainName}.
	 * There must not be an existing provider for that domain.
	 * 
	 * @throws IllegalStateException 
	 * 		If there is already a domain provider registered for the given
	 * 		{@code domainName}.
	 */
	def static void registerDomainProvider(String domainName, VitruvDomainProvider<?> provider) {
		checkNotNull(provider)
		checkNotNull(domainName)

		var VitruvDomainProvider<?> existingProvider = runtimeRegisteredProviders.get(domainName) ?:
			getExtensionPointProviders(domainName).findFirst[true]
		checkState(existingProvider ===
			null, '''There is already a provider registered for the domain “«domainName»”: «existingProvider»''')
		runtimeRegisteredProviders.put(domainName, provider)
	}

	/**
	 * Unregisters a domain provider previously registered through
	 * {@link #registerDomainProvider}.
	 * 
	 * @throws IllegalArgumentException 
	 * 		If there is a domain provider registered for the given
	 * 		{@code domainName} but that domain provider was registered through
	 * 		the extension point and not through {@link #registerDomainProvider}.
	 */
	def static void unregisterDomainProvider(String domainName) {
		if (!runtimeRegisteredProviders.containsKey(domainName)) {
			val extensionPointProvider = getExtensionPointProviders(domainName).findFirst[true]
			checkArgument(extensionPointProvider ===
				null, '''The provider «extensionPointProvider» for the domain “«domainName» was registered via extension point and can thus not be unregistered!''')
		}
		runtimeRegisteredProviders.remove(domainName)
	}

	def private static getExtensionPointProviders(String domainName) {
		getAllDomainProvidersFromExtensionPoint.filter[domain.name.equals(domainName)];
	}

	def static getAllDomainProviders() {
		getAllDomainProvidersFromExtensionPoint + runtimeRegisteredProviders.values()
	}

	/**
	 * Retrieves the provider for the domain with the given name.
	 * 
	 * @param domainName - the name of the domain to retrieve
	 */
	def static VitruvDomainProvider<?> getDomainProvider(String domainName) {
		checkNotNull(domainName)

		if (runtimeRegisteredProviders.containsKey(domainName)) {
			return runtimeRegisteredProviders.get(domainName)
		}
		val potentialDomainProviders = getExtensionPointProviders(domainName)
		if (potentialDomainProviders.size == 0) {
			throw new IllegalStateException(
				'''Found no provider for domain “«domainName»”!'''
			);
		}
		if (potentialDomainProviders.size > 1) {
			throw new IllegalStateException(
				'''Found more than one provider for domain “«domainName»”: «FOR provider : potentialDomainProviders SEPARATOR "', "»«provider.domain.name»«ENDFOR»'''
			);
		}
		return potentialDomainProviders.get(0);
	}

	/**
	 * Retrieves all providers for domain from the extensions registered to the VitruvDomain
	 * extension point.
	 */
	def private static Iterable<VitruvDomainProvider<?>> getAllDomainProvidersFromExtensionPoint() {
		val List<VitruvDomainProvider<?>> domainProvider = new LinkedList<VitruvDomainProvider<?>>();
		Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID).map [
			it.createExecutableExtension("class")
		].forEach[if (it instanceof VitruvDomainProvider<?>) domainProvider.add(it)];
		return domainProvider
	}
}
