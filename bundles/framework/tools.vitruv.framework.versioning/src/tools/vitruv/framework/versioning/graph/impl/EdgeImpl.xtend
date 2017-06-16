package tools.vitruv.framework.versioning.graph.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.graph.Edge
import tools.vitruv.framework.versioning.graph.Node

class EdgeImpl<T> implements Edge<T> {
	@Accessors(PUBLIC_GETTER)
	Node<T> fromNode
	@Accessors(PUBLIC_GETTER)
	Node<T> toNode

	@Accessors(PUBLIC_GETTER)
	val int weight

	new(Node<T> fromNode, Node<T> toNode, int weight) {
		this.fromNode = fromNode
		this.toNode = toNode
		this.weight = weight
	}

	override isBetween(Node<T> node_1, Node<T> node_2) {
		fromNode === node_1 && toNode === node_2
	}

	override toString() {
		'''Edge from 
		«fromNode» 
		to 
		«toNode»'''
	}
}
