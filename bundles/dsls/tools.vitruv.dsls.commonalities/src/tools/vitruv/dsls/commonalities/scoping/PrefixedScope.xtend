package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope

/**
 * Queries for objects for a given name are prefixed with the specified name.
 * The prefix is removed from the name of results.
 */
class PrefixedScope extends NameTransformingScope {

	new(IScope delegate, QualifiedName prefix) {
		super(delegate, [
			prefix.append(it)
		],[
			it.startsWith(prefix) ? it.skipFirst(prefix.segmentCount) : it
		])
	}
}
