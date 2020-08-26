package tools.vitruv.extensions.dslruntime.commonalities.matching

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.EqualsHashCode
import org.eclipse.xtend.lib.annotations.ToString

import static com.google.common.base.Preconditions.*

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
		checkArgument(!objectsByName.containsKey(name), '''There is already an object for this name: «name»''')
		checkArgument(!objectsByName.containsValue(object), '''The object is already present: «object»''')
		objectsByName.put(name, object)
	}

	// Convenience type parameter: The returned object gets automatically cast to the type expected by the caller.
	def <T extends EObject> T getObject(String name) {
		return objectsByName.get(name) as T
	}

	def copy() {
		return new ParticipationObjects().merge(this)
	}

	/**
	 * ParticipationObjects can only be merged if they don't contain
	 * contradicting mappings for the same name or object.
	 */
	def boolean canBeMerged(ParticipationObjects other) {
		if (other === this) return false
		return other.objectsByName.entrySet
			// Ignore any matching entries:
			.filter[getObject(key) != value]
			// Check if we already contain a mapping for the name or object:
			.forall[!this.objectsByName.containsKey(key) && !this.objects.contains(value)]
	}

	/**
	 * Adds the entries of the given ParticipationObjects to this
	 * ParticipationObjects, if they are not already present.
	 * <p>
	 * Throws an exception when trying to add entries for names or objects that
	 * are already present but mapped differently.
	 */
	def merge(ParticipationObjects other) {
		checkArgument(other !== this, "Cannot merge with self")
		other.objectsByName.entrySet
			// Ignore any entries that are already present:
			.filter[getObject(key) != value]
			// Throws an exception if there is already a mapping for the name or object:
			.forEach[addObject(key, value)]
		return this
	}
}
