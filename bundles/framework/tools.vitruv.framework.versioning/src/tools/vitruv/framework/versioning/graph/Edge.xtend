package tools.vitruv.framework.versioning.graph

interface Edge<T> {
	def Node<T> getFromNode()

	def Node<T> getToNode()

	def int getWeight()

	def boolean isBetween(Node<T> node_1, Node<T> node_2)
}
