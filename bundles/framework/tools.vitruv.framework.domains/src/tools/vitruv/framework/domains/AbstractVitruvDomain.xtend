package tools.vitruv.framework.domains

import java.util.Collections
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.AbstractURIHaving
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.domains.VitruvDomain
import org.eclipse.emf.ecore.EClass
import java.util.HashSet
import java.util.HashMap
import static java.util.Collections.emptySet
import static java.util.Collections.emptyMap

abstract class AbstractVitruvDomain extends AbstractURIHaving implements VitruvDomain {
	String name
	Set<String> fileExtensions
	Set<String> nsURIs
	EPackage metamodelRootPackage
	Set<EPackage> furtherRootPackages
	Map<Object, Object> defaultLoadOptions
	Map<Object, Object> defaultSaveOptions
	DefaultStateBasedChangeResolutionStrategy stateChangePropagationStrategy

	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		return (#[rootPackage.nsURI] + rootPackage.ESubpackages.map[it.nsURIsRecursive].flatten).toSet
	}

	/** 
	 * Convenience method if the domain consists of only a single namespace
	 */
	new(String name, EPackage metamodelRootPackage, String... fileExtensions) {
		super(VURI.getInstance(metamodelRootPackage.nsURI))
		initialize(name, metamodelRootPackage, emptySet, emptyMap, emptyMap, fileExtensions)
	}

	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages, String... fileExtensions) {
		super(VURI.getInstance(metamodelRootPackage.nsURI))
		initialize(name, metamodelRootPackage, furtherRootPackages, emptyMap, emptyMap, fileExtensions)
	}

	protected def void initialize(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages,
		Map<Object, Object> defaultLoadOptions, Map<Object, Object> defaultSaveOptions, String... fileExtensions) {
		this.name = name
		this.fileExtensions = new HashSet(fileExtensions)
		this.metamodelRootPackage = metamodelRootPackage
		this.furtherRootPackages = new HashSet(furtherRootPackages)
		this.nsURIs = (metamodelRootPackage.nsURIsRecursive + furtherRootPackages.map[nsURIsRecursive].flatten).toSet
		this.defaultLoadOptions = new HashMap(defaultLoadOptions)
		this.defaultSaveOptions = new HashMap(defaultSaveOptions)
		stateChangePropagationStrategy = new DefaultStateBasedChangeResolutionStrategy()
	}

	override StateBasedChangeResolutionStrategy getStateChangePropagationStrategy() {
		return stateChangePropagationStrategy
	}

	override getName() {
		return name
	}

	override getMetamodelRootPackage() {
		return metamodelRootPackage
	}

	override getFurtherRootPackages() {
		return Collections.unmodifiableSet(furtherRootPackages)
	}

	override getFileExtensions() {
		return Collections.unmodifiableSet(fileExtensions)
	}

	override getNsUris() {
		return Collections.unmodifiableSet(nsURIs)
	}

	override getDefaultLoadOptions() {
		return Collections.unmodifiableMap(defaultLoadOptions)
	}

	override getDefaultSaveOptions() {
		return Collections.unmodifiableMap(defaultSaveOptions)
	}

	override isInstanceOfDomainMetamodel(EObject eObject) {
		if (eObject === null) {
			return false
		}
		val eClass = if(eObject instanceof EClass) eObject else eObject.eClass()

		if (null === eClass || null === eClass.EPackage || null === eClass.EPackage.nsURI ||
			!this.nsURIs.contains(eClass.EPackage.nsURI)) {
			return false
		}
		return true
	}

	override toString() {
		return '''domain ‘«name»’'''
	}

	override isUserVisible() {
		true
	}

	override shouldTransitivelyPropagateChanges() {
		false
	}

}
