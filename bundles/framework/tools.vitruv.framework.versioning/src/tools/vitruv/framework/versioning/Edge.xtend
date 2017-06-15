package tools.vitruv.framework.versioning

class Edge<T> {
	Node<T> node1
	Node<T> node2

	// private int weight;
	new(Node<T> node1, Node<T> node2, int weight) {
		this.node1 = node1
		this.node2 = node2
		this.weight = weight
	}

	def Node<T> fromNode() {
		return node1
	}

	def Node<T> toNode() {
		return node2
	}

	def boolean isBetween(Node<T> node1, Node<T> node2) {
		return (this.node1 === node1 && this.node2 === node2)
	}
}
