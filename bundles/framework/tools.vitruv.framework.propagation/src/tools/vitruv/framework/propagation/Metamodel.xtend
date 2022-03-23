package tools.vitruv.framework.propagation

import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Set
import java.util.HashSet
import static extension com.google.common.base.Preconditions.checkNotNull

/**
 * A metamodel represented by its namespace URIs.
 */
class Metamodel {
	@Accessors(PUBLIC_GETTER)
	val Set<String> nsURIs = new HashSet

	private new(Set<String> nsURIs) {
		nsURIs.forEach[checkNotNull]
		this.nsURIs += nsURIs
	}

	override equals(Object obj) {
		nsURIs == obj
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
