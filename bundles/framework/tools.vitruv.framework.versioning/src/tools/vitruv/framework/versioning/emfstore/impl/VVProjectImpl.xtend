package tools.vitruv.framework.versioning.emfstore.impl

import tools.vitruv.framework.versioning.emfstore.VVProject
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.UUID

abstract class VVProjectImpl implements VVProject {
	@Accessors(PUBLIC_GETTER)
	String id

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	String name

	new() {
		id = UUID.randomUUID.toString
	}
}
