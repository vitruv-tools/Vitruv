package tools.vitruv.framework.change

import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set
import java.util.HashSet
import static com.google.common.base.Preconditions.checkArgument

/**
 * A descriptor for a metamodel represented by its namespace URIs.
 */
final class MetamodelDescriptor {
	@Accessors(PUBLIC_GETTER)
	val Set<String> nsURIs

	private new(Set<String> nsURIs) {
		nsURIs.forEach [
			checkArgument(it !== null,
				"metamodel descriptor to be instantiated for namespace URIs %s must not contain a null URI", nsURIs)
		]
		this.nsURIs = new HashSet(nsURIs)
	}

	/**
	 * Returns whether the metamodel of the given descriptor is is contained in this 
	 * descriptor's metamodel, i.e., whether its namespace URIs are a subset of the 
	 * ones of this descriptor.
	 * 
	 * @param descriptorForPpotentiallyContainedMetamodel the descriptor to check for 
	 * 			containment of its metamodel in this one, must not be <code>null</code>
	 * @return whether the metamodel of the given descriptor is contained in this one
	 */
	def contains(MetamodelDescriptor descriptorForPotentiallyContainedMetamodel) {
		checkArgument(descriptorForPotentiallyContainedMetamodel !== null,
			"metamodel descriptor to check for containment in %s must not be null", this)
		nsURIs.containsAll(descriptorForPotentiallyContainedMetamodel.nsURIs)
	}

	override equals(Object obj) {
		if (obj instanceof MetamodelDescriptor) {
			return nsURIs == obj.nsURIs
		}
		false
	}

	override hashCode() {
		nsURIs.hashCode()
	}

	override toString() {
		nsURIs.toString()
	}

	static def of(EPackage rootPackage) {
		with(#{rootPackage.nsURI})
	}

	static def of(Set<EPackage> rootPackages) {
		with(rootPackages.map[it.nsURI].toSet)
	}

	static def with(String nsURI) {
		with(#{nsURI})
	}

	static def with(Set<String> nsURIs) {
		new MetamodelDescriptor(nsURIs)
	}

}
