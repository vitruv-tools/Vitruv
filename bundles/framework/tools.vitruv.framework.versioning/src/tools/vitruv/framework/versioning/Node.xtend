package tools.vitruv.framework.versioning

import java.util.List
import java.util.Optional

interface Node<T> {

	def List<Edge<T>> getEdges()

	def Node<T> getParent()

	def Optional<Edge<T>> findEdge(Node<T> node)

	def T getVertex()

	def boolean addEdge(Node<T> node, int weight)

	def boolean hasEdge(Node<T> node)

	def boolean isVisited()

	def boolean removeEdge(Node<T> node)

	def int getEdgeCount()

	def void setParent(Node<T> parent)

	def void setVisited(boolean isVisited)
}
