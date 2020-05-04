package tools.vitruv.extensions.dslruntime.commonalities.matching

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.ToString

/**
 * A set of named EObjects.
 */
@EqualsHashCode
@ToString
class ParticipationObjects {

	val BiMap<String, EObject> objectsByName = HashBiMap.create()

	new() {
	}

	def getObjects() {
		return objectsByName.values
	}

	def addObject(String name, EObject object) {
		objectsByName.put(name, object)
	}

	// Convenience type parameter: The returned object gets automatically cast
	// to the type expected by the caller.
	def <T> T getObject(String name) {
		return objectsByName.get(name) as T
	}

	def copy() {
		return new ParticipationObjects().merge(this)
	}

	/**
	 * Adds the entries of the given ParticipationObjects to this
	 * ParticipationObjects.
	 */
	def merge(ParticipationObjects other) {
		this.objectsByName.putAll(other.objectsByName)
		return this
	}
}
