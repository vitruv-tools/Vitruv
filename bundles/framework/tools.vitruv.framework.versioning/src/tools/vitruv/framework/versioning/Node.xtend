package tools.vitruv.framework.versioning

import java.util.ArrayList
import java.util.List
import java.util.Optional

class Node<T> {
	T vertex
	List<Edge<T>> edges
	Node<T> parent
	boolean isVisited

	new(T vertex) {
		this.vertex = vertex
		this.edges = new ArrayList()
	}

	def T vertex() {
		return vertex
	}

	def boolean addEdge(Node<T> node, int weight) {
		if (hasEdge(node)) {
			return false
		}
		var Edge<T> newEdge = new Edge(this, node, weight)
		return edges.add(newEdge)
	}

	def boolean removeEdge(Node<T> node) {
		var Optional<Edge<T>> optional = findEdge(node)
		if (optional.isPresent()) {
			return edges.remove(optional.get())
		}
		return false
	}

	def boolean hasEdge(Node<T> node) {
		return findEdge(node).isPresent()
	}

	def private Optional<Edge<T>> findEdge(Node<T> node) {
		return edges.stream().filter([edge|edge.isBetween(this, node)]).findFirst()
	}

	def List<Edge<T>> edges() {
		return edges
	}

	def int getEdgeCount() {
		return edges.size()
	}

	def Node<T> parent() {
		return parent
	}

	def boolean isVisited() {
		return isVisited
	}

	def void setVisited(boolean isVisited) {
		this.isVisited = isVisited
	}

	def void setParent(Node<T> parent) {
		this.parent = parent
	}
}
