package tools.vitruv.framework.domains

import java.util.HashMap
import org.eclipse.core.runtime.Platform
import java.util.List
import static com.google.common.base.Preconditions.*
import org.reflections.Reflections
import org.apache.log4j.Logger
import java.lang.annotation.Target
import java.lang.annotation.Retention
import edu.kit.ipd.sdq.activextendannotations.Lazy
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

/**
 * Registry of all {@linkplain VitruvDomainProvider DomainProviders} known to
 * Vitruvius. Domain Providers are usually registered by providing them at the
 * extension point {@link #EXTENSION_POINT_ID}. They can, however, also be
 * registered at runtime.
 */
final class VitruvDomainProviderRegistry {
	static val LOGGER = Logger.getLogger(VitruvDomainProviderRegistry)

	private new() {
	}

	@Lazy(value = PRIVATE, synchronizeInitialization = true) 
	static val List<VitruvDomainProvider<?>> staticallyRegisteredProviders = readAllDomainProvidersFromPlatform()
	static val runtimeRegisteredProviders = new HashMap<String, VitruvDomainProvider<?>>
	public static val EXTENSION_POINT_ID = "tools.vitruv.framework.domains.provider"

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
			findStaticallyRegisteredProviders(domainName).findFirst[true]
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
			val extensionPointProvider = findStaticallyRegisteredProviders(domainName).findFirst[true]
			checkArgument(extensionPointProvider === null, '''The provider «extensionPointProvider» for the domain “«domainName»” was registered statically (e.g. via extension point) and can thus not be unregistered!''')
		}
		runtimeRegisteredProviders.remove(domainName)
	}

	def private static findStaticallyRegisteredProviders(String domainName) {
		staticallyRegisteredProviders.filter[domain.name.equals(domainName)]
	}

	def static getAllDomainProviders() {
		staticallyRegisteredProviders + runtimeRegisteredProviders.values()
	}

	/**
	 * Returns whether a domain with the given name is registered or not
	 * 
	 * @param domainName - the name of the domain to look for
	 */
	def static boolean hasDomainProvider(String domainName) {
		return runtimeRegisteredProviders.containsKey(domainName) 
			|| findStaticallyRegisteredProviders(domainName).size > 0
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
		val potentialDomainProviders = findStaticallyRegisteredProviders(domainName)
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
	def private static List<VitruvDomainProvider<?>> readAllDomainProvidersFromPlatform() {
		// If we are in an Eclipse environment, read the VitruvDomainProvider from the extension point.
		// If we are not in an Eclipse environment, read the VitruvDomainProvider from the classpath.
		// We cannot use the classpath in Eclipse, because a classloader can only resolve within a bundle,
		// so we would need to know the name of the bundle to get the classloader from. In a non-Eclipse
		// environment, the bundles are mapped into one classpath.
		if (Platform.isRunning) {
			Platform.extensionRegistry.getConfigurationElementsFor(EXTENSION_POINT_ID)
				.map [it.createExecutableExtension("class")]
				.filter(VitruvDomainProvider as Class<?> as Class<VitruvDomainProvider<?>>)
				.toList
		} else {
			val providers = new Reflections("tools.vitruv")
				.getSubTypesOf(VitruvDomainProvider as Class<?> as Class<VitruvDomainProvider<?>>)
				.filter [!annotations.exists [it instanceof IgnoreInStandalone]]
				.filter [
					if (!constructors.exists [parameterCount == 0]) {
						LOGGER.warn('''Domain provider '«name»' has no default constructor and will not be added to the domain provider registry.''')
						false
					} else true
				]
				.mapFixed [constructor.newInstance()]
				
			// Get the domain for each provider when we are not an Eclipse environment to register the
			// packages of the domains appropriately, so they can be resolved
			providers.forEach [getDomain()]
			providers
		}
	}
	
	/**
	 * Annotation for domain providers that should be ignored when running in standalone mode.
	 * This annotation has no effect when running in Eclipse, because domain providers will then
	 * be registered via extension points, with no need to ignore types.  
	 */
	@Target(TYPE)
 	@Retention(RUNTIME)
	annotation IgnoreInStandalone {}
}
