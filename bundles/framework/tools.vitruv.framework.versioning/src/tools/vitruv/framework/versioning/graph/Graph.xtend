package tools.vitruv.framework.versioning.graph

import java.util.List
import java.util.Collection

interface Graph<T> {
	/**
	 * Adds a vertex to the graph.
	 * @param vertex vertex to add
	 */
	def boolean addVertex(T vertex)

	/**
	 * Adds a directed edge between two vertices in the graph.
	 * @param vertex1 vertex where the (directed) edge begins
	 * @param vertex2 vertex where the (directed) edge ends
	 */
	def boolean addEdge(T vertex1, T vertex2)

	/**
	 * Adds a weighted directed edge between two vertices in the graph.
	 * @param vertex1 vertex where the (directed) edge begins
	 * @param vertex2 vertex where the (directed) edge ends
	 * @param weight weight of the edge
	 */
	def boolean addEdge(T vertex1, T vertex2, int weight)

	/**
	 * Remove a vertex from the graph.
	 * @param vertex vertex to be removed
	 * @return true if the vertex was removed, false if no such vertex was
	 * found.
	 */
	def boolean removeVertex(T vertex)

	/**
	 * Method to remove a directed edge between two vertices in the graph.
	 * @param vertex1 vertex where the (directed) edge begins
	 * @param vertex2 vertex where the (directed) edge ends
	 * @return true if the edge was removed, false if no such edge was found.
	 */
	def boolean removeEdge(T vertex1, T vertex2)

	/**
	 * Method to get the number of vertices in the graph.
	 * @return count of vertices
	 */
	def int vertexCount()

	/**
	 * Method to get the number of edges in the graph.
	 * @return count of edges
	 */
	def int edgeCount()

	/**
	 * Method to check if a vertex exists in the graph.
	 * @param vertex vertex which is to be checked
	 * @return returns true if the graph contains the vertex, false otherwise
	 */
	def boolean containsVertex(T vertex)

	/**
	 * Method to check if an edge exists in the graph.
	 * @param vertex1 vertex where the (directed) edge begins
	 * @param vertex2 vertex where the (directed) edge ends
	 * @return returns true if the graph contains the edge, false otherwise
	 */
	def boolean containsEdge(T vertex1, T vertex2)

	/**
	 * Method to get the shortest path from startVertex to endVertex.
	 * @param startVertex vertex where the path begins
	 * @param endVertex vertex where the path ends
	 * @return list of vertices in the shortest path from startVertex to
	 * endVertex, null if no such path exists.
	 */
	// @Nullable
	def List<T> shortestPath(T startVertex, T endVertex)

	def Node<T> getNode(T value)

	def Collection<Edge<T>> getEdges()

	def Collection<Node<T>> getNodes()
}
