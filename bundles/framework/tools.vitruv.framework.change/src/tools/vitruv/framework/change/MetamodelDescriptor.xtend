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
	val Set<String> nsUris

	private new(Set<String> nsUris) {
		nsUris.forEach [
			checkArgument(it !== null,
				"metamodel descriptor to be instantiated for namespace URIs %s must not contain a null URI", nsUris)
		]
		this.nsUris = new HashSet(nsUris)
	}

	/**
	 * Returns whether the metamodel of the given descriptor is contained in this 
	 * descriptor's metamodel, i.e., whether its namespace URIs are a subset of the 
	 * ones of this descriptor.
	 * 
	 * @param descriptorForPotentiallyContainedMetamodel the descriptor to check for 
	 * 			containment of its metamodel in this one, must not be <code>null</code>
	 * @return whether the metamodel of the given descriptor is contained in this one
	 */
	def contains(MetamodelDescriptor descriptorForPotentiallyContainedMetamodel) {
		checkArgument(descriptorForPotentiallyContainedMetamodel !== null,
			"metamodel descriptor to check for containment in %s must not be null", this)
		nsUris.containsAll(descriptorForPotentiallyContainedMetamodel.nsUris)
	}

	override equals(Object obj) {
		if (obj instanceof MetamodelDescriptor) {
			return nsUris == obj.nsUris
		}
		false
	}

	override hashCode() {
		nsUris.hashCode()
	}

	override toString() {
		nsUris.toString()
	}

	static def of(EPackage rootPackage) {
		with(#{rootPackage.nsURI})
	}

	static def of(Set<EPackage> rootPackages) {
		with(rootPackages.map[it.nsURI].toSet)
	}

	static def with(String nsUri) {
		with(#{nsUri})
	}

	static def with(Set<String> nsUris) {
		new MetamodelDescriptor(nsUris)
	}

}
