package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.List
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.Edge
import tools.vitruv.framework.versioning.Node

class NodeImpl<T> implements Node<T> {
	@Accessors(PUBLIC_GETTER)
	val T vertex
	@Accessors(PUBLIC_GETTER)
	val List<Edge<T>> edges
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	Node<T> parent
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	boolean visited

	new(T vertex) {
		this.vertex = vertex
		edges = new ArrayList
	}

	override addEdge(Node<T> node, int weight) {
		if (hasEdge(node))
			return false
		val Edge<T> newEdge = new EdgeImpl(this, node, weight)
		edges.add(newEdge)
	}

	override removeEdge(Node<T> node) {
		var Optional<Edge<T>> optional = findEdge(node)
		if (optional.present) {
			return edges.remove(optional.get)
		}
		return false
	}

	override hasEdge(Node<T> node) {
		findEdge(node).present
	}

	override Optional<Edge<T>> findEdge(Node<T> node) {
		edges.stream.filter([isBetween(this, node)]).findFirst
	}

	override int getEdgeCount() {
		edges.size
	}
}
