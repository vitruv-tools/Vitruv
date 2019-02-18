package tools.vitruv.framework.domains

import java.util.Collections
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import java.util.ArrayList
import org.eclipse.emf.ecore.EPackage
import java.util.Collection
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EClass

abstract class AbstractVitruvDomain extends AbstractURIHaving implements VitruvDomain {
	Collection<String> fileExtensions
	Set<String> nsURIs
	EPackage metamodelRootPackage;
	Set<EPackage> furtherRootPackages;
	Map<Object, Object> defaultLoadOptions
	Map<Object, Object> defaultSaveOptions
	String name;
	DefaultStateChangePropagationStrategy stateChangePropagationStrategy
	
	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		return (#[rootPackage.nsURI] + rootPackage.ESubpackages.map[it.nsURIsRecursive].flatten).toSet;
	}

	def protected static String getTuidPrefix(Iterable<String> nsURIs) {
		if (nsURIs !== null && nsURIs.size() > 0) {
			return nsURIs.iterator().next()
		} else {
			throw new RuntimeException(
				'''Cannot get a Tuid prefix from the set of namespace URIs '«»«nsURIs»'!'''.toString)
		}
	}

	/** 
	 * Convenience method if the metamodel consists of only a single namespace
	 */
	new(String name, EPackage metamodelRootPackage, String... fileExtensions) {
		super(VURI::getInstance(metamodelRootPackage.nsURI));
		initialize(name, metamodelRootPackage, Collections::emptySet(), Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}
	
	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, String... fileExtensions) {
		super(VURI::getInstance(metamodelRootPackage.nsURI));
		initialize(name, metamodelRootPackage, furtherRootPackages, Collections::emptyMap(), Collections::emptyMap(), fileExtensions)
	}
	
	override StateChangePropagationStrategy getStateChangePropagationStrategy() {
		return stateChangePropagationStrategy
	}

	protected def void initialize(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, Map<Object, Object> defaultLoadOptions, Map<Object, Object> defaultSaveOptions, String... fileExtensions) {
		this.name = name;
		this.fileExtensions = fileExtensions
		this.metamodelRootPackage = metamodelRootPackage;
		this.furtherRootPackages = furtherRootPackages;
		this.nsURIs = (metamodelRootPackage.nsURIsRecursive + furtherRootPackages.map[nsURIsRecursive].flatten).toSet
		this.defaultLoadOptions = defaultLoadOptions
		this.defaultSaveOptions = defaultSaveOptions
		stateChangePropagationStrategy = new DefaultStateChangePropagationStrategy()
	}

	override Collection<String> getFileExtensions() {
		return new ArrayList<String>(this.fileExtensions);
	}

	override boolean isInstanceOfDomainMetamodel(EObject eObject) {
		if (eObject === null) {
			return false;
		}
		val eClass = if (eObject instanceof EClass) eObject else eObject.eClass(); 
		
		if (null === eClass || null === eClass.getEPackage() ||	null === eClass.getEPackage().getNsURI() ||
			!this.nsURIs.contains(eClass.getEPackage().getNsURI())) {
			return false
		}
		return true
	}

	override Set<String> getNsUris() {
		return this.nsURIs
	}

	override Map<Object, Object> getDefaultLoadOptions() {
		return this.defaultLoadOptions
	}

	override Map<Object, Object> getDefaultSaveOptions() {
		return this.defaultSaveOptions
	}

	override toString() {
		return "Metamodel for namespaces: " + nsURIs;
	}

	def boolean isMetamodelForVuri(VURI metamodelVURI) {
		return nsURIs.exists[VURI.getInstance(it).equals(metamodelVURI)];
	}

	def boolean isMetamodelFor(VURI modelVuri) {
		return fileExtensions.contains(modelVuri.fileExtension);
	}
	
	override getMetamodelRootPackage() {
		return metamodelRootPackage;
	}
	
	override getFurtherRootPackages() {
		return furtherRootPackages;
	}
	
	override getName() {
		return name;
	}
	
	override isUserVisible() {
		true
	}
	
	override shouldTransitivelyPropagateChanges() {
		false
	}
	
	override supportsUuids() {
		true
	}
	
}
