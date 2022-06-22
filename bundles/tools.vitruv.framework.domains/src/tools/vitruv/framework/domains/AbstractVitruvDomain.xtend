package tools.vitruv.framework.domains

import java.util.Collections
import java.util.Set
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.framework.domains.VitruvDomain
import java.util.HashSet

abstract class AbstractVitruvDomain implements VitruvDomain {
	String name
	Set<String> fileExtensions
	Set<String> nsURIs
	EPackage metamodelRootPackage

	/**
	 * Returns the namespace URI of the given {@link EPackage} and all subpackages.
	 */
	protected static def Set<String> getNsURIsRecursive(EPackage rootPackage) {
		return (#[rootPackage.nsURI] + rootPackage.ESubpackages.map[it.nsURIsRecursive].flatten).toSet
	}

	new(String name, EPackage metamodelRootPackage, String... fileExtensions) {
		initialize(name, metamodelRootPackage, fileExtensions)
	}

	protected def void initialize(String name, EPackage metamodelRootPackage, String... fileExtensions) {
		this.name = name
		this.fileExtensions = new HashSet(fileExtensions)
		this.metamodelRootPackage = metamodelRootPackage
		this.nsURIs = metamodelRootPackage.nsURIsRecursive
	}

	override getName() {
		return name
	}

	override getMetamodelRootPackage() {
		return metamodelRootPackage
	}

	override getFileExtensions() {
		return Collections.unmodifiableSet(fileExtensions)
	}

	override getNsUris() {
		return Collections.unmodifiableSet(nsURIs)
	}

	override toString() {
		return '''domain ‘«name»’'''
	}

	override shouldTransitivelyPropagateChanges() {
		false
	}

}
