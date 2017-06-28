package tools.vitruv.framework.versioning.extensions.impl

import org.eclipse.xtend.lib.annotations.Accessors
import org.graphstream.graph.implementations.AbstractGraph
import org.graphstream.graph.implementations.SingleNode
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.EChangeNode

class EChangeNodeImpl extends SingleNode implements EChangeNode {
	@Accessors(PUBLIC_GETTER,PUBLIC_SETTER)
	EChange eChange

	protected new(AbstractGraph graph, String id) {
		super(graph, id)
	}

}
