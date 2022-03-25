package tools.vitruv.framework.change

import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set
import java.util.HashSet
import static com.google.common.base.Preconditions.checkArgument

/**
 * A metamodel represented by its namespace URIs.
 */
final class Metamodel {
	@Accessors(PUBLIC_GETTER)
	val Set<String> nsURIs

	private new(Set<String> nsURIs) {
		nsURIs.forEach [
			checkArgument(it !== null, "metamodel to be instantiated for namespace URIs %s must not contain a null URI",
				nsURIs)
		]
		this.nsURIs = new HashSet(nsURIs)
	}

	/**
	 * Returns whether the given metamodel is contained in this metamodel,
	 * i.e., whether its namespace URIs are a subset of the ones of this metamodel.
	 * 
	 * @param metamodel the metamodel to check for containment in this metamodel,
	 *                  must not be <code>null</code>
	 * @return whether the given metamodel is contained in this one
	 */
	def contains(Metamodel metamodel) {
		checkArgument(metamodel !== null, "metamodel to check for containment in %s must not be null", this)
		nsURIs.containsAll(metamodel.nsURIs)
	}

	override equals(Object obj) {
		if (obj instanceof Metamodel) {
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
		new Metamodel(nsURIs)
	}

}
