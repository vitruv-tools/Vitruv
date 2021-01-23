package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope
import static com.google.common.base.Preconditions.checkNotNull

/**
 * Queries for objects for a given name are prefixed with the specified name.
 * The prefix is removed from the name of results.
 */
class PrefixedScope extends NameTransformingScope {
	val QualifiedName prefix
	
	new(IScope delegate, QualifiedName prefix) {
		super(delegate)
		this.prefix = checkNotNull(prefix, "prefix was null!")
	}
	
	override protected transformQuery(QualifiedName name) {
		prefix.append(name)
	}
	
	override protected transformResult(QualifiedName name) {
		name.startsWith(prefix) ? name.skipFirst(prefix.segmentCount) : name
	}
	
	override toString() {
		'''prefixed with ‹«prefix»›: «delegate»'''
	}
}
