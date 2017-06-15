package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.Edge

class EdgeImpl<T> implements Edge<T> {
	@Accessors(PUBLIC_GETTER)
	tools.vitruv.framework.versioning.Node<T> fromNode
	@Accessors(PUBLIC_GETTER)
	tools.vitruv.framework.versioning.Node<T> toNode

	@Accessors(PUBLIC_GETTER)
	val int weight

	new(tools.vitruv.framework.versioning.Node<T> fromNode, tools.vitruv.framework.versioning.Node<T> toNode,
		int weight) {
		this.fromNode = fromNode
		this.toNode = toNode
		this.weight = weight
	}

	override boolean isBetween(tools.vitruv.framework.versioning.Node<T> node_1,
		tools.vitruv.framework.versioning.Node<T> node_2) {
		fromNode === node_1 && toNode === node_2
	}
}
