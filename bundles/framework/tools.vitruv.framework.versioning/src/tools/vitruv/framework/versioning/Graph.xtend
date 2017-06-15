package tools.vitruv.framework.versioning

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Queue

class Graph<T> {
	Map<T, Node<T>> adjacencyList

	new() {
		adjacencyList = new HashMap()
	}

	/** 
	 * Adds a vertex to the graph.
	 * @param vertexvertex to add
	 */
	def boolean addVertex(T vertex) {
		if (adjacencyList.containsKey(vertex)) {
			return false
		}
		adjacencyList.put(vertex, new Node(vertex))
		return true
	}

	/** 
	 * Adds a directed edge between two vertices in the graph.
	 * @param vertex1vertex where the (directed) edge begins
	 * @param vertex2vertex where the (directed) edge ends
	 */
	def boolean addEdge(T vertex1, T vertex2) {
		return addEdge(vertex1, vertex2, 0)
	}

	/** 
	 * Adds a weighted directed edge between two vertices in the graph.
	 * @param vertex1vertex where the (directed) edge begins
	 * @param vertex2vertex where the (directed) edge ends
	 * @param weightweight of the edge
	 */
	def boolean addEdge(T vertex1, T vertex2, int weight) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2)) {
			throw new RuntimeException("Vertex does not exist")
		}
		// add the edge
		var Node<T> node1 = getNode(vertex1)
		var Node<T> node2 = getNode(vertex2)
		return node1.addEdge(node2, weight)
	}

	/** 
	 * Remove a vertex from the graph.
	 * @param vertexvertex to be removed
	 * @return true if the vertex was removed, false if no such vertex was
	 * found.
	 */
	def boolean removeVertex(T vertex) {
		if (!adjacencyList.containsKey(vertex)) {
			return false
		}
		// get node to be removed
		val Node<T> toRemove = getNode(vertex)
		// remove all incoming edges to node
		adjacencyList.values().forEach([node|node.removeEdge(toRemove)])
		// remove the node
		adjacencyList.remove(vertex)
		return true
	}

	/** 
	 * Method to remove a directed edge between two vertices in the graph.
	 * @param vertex1vertex where the (directed) edge begins
	 * @param vertex2vertex where the (directed) edge ends
	 * @return true if the edge was removed, false if no such edge was found.
	 */
	def boolean removeEdge(T vertex1, T vertex2) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2)) {
			return false
		}
		return getNode(vertex1).removeEdge(getNode(vertex2))
	}

	/** 
	 * Method to get the number of vertices in the graph.
	 * @return count of vertices
	 */
	def int vertexCount() {
		return adjacencyList.keySet().size()
	}

	/** 
	 * Method to get the number of edges in the graph.
	 * @return count of edges
	 */
	def int edgeCount() {
		return adjacencyList.values().stream().mapToInt(NodegetEdgeCount).sum()
	}

	/** 
	 * Method to check if a vertex exists in the graph.
	 * @param vertexvertex which is to be checked
	 * @return returns true if the graph contains the vertex, false otherwise
	 */
	def boolean containsVertex(T vertex) {
		return adjacencyList.containsKey(vertex)
	}

	/** 
	 * Method to check if an edge exists in the graph.
	 * @param vertex1vertex where the (directed) edge begins
	 * @param vertex2vertex where the (directed) edge ends
	 * @return returns true if the graph contains the edge, false otherwise
	 */
	def boolean containsEdge(T vertex1, T vertex2) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2)) {
			return false
		}
		return getNode(vertex1).hasEdge(getNode(vertex2))
	}

	/** 
	 * Method to get the shortest path from startVertex to endVertex.
	 * @param startVertexvertex where the path begins
	 * @param endVertexvertex where the path ends
	 * @return list of vertices in the shortest path from startVertex to
	 * endVertex, null if no such path exists.
	 */
	def // @Nullable
	List<T> shortestPath(T startVertex, T endVertex) {
		// if nodes not found, return empty path
		if (!containsVertex(startVertex) || !containsVertex(endVertex)) {
			return null
		}
		// run bfs on the graph
		runBFS(startVertex)
		var List<T> path = new ArrayList()
		// trace path back from end vertex to start
		var Node<T> end = getNode(endVertex)
		while (end !== null && end !== getNode(startVertex)) {
			path.add(end.vertex())
			end = end.parent()
		}
		// if end is null, node not found
		if (end === null) {
			return null
		}
		Collections::reverse(path)
		return path
	}

	def private void runBFS(T startVertex) {
		if (!containsVertex(startVertex)) {
			throw new RuntimeException("Vertex does not exist.")
		}
		// reset the graph
		resetGraph()
		// init the queue
		var Queue<Node<T>> queue = new LinkedList()
		var Node<T> start = getNode(startVertex)
		queue.add(start)
		// explore the graph
		while (!queue.isEmpty()) {
			var Node<T> first = queue.remove()
			first.setVisited(true)
			first.edges().forEach([edge |
				{
					var Node<T> neighbour = edge.toNode()
					if (!neighbour.isVisited()) {
						neighbour.setParent(first)
						queue.add(neighbour)
					}
				}
			])
		}
	}

	def private Node<T> getNode(T value) {
		return adjacencyList.get(value)
	}

	def private void resetGraph() {
		adjacencyList.keySet().forEach([key |
			{
				var Node<T> node = getNode(key)
				node.setParent(null)
				node.setVisited(false)
			}
		])
	}
}
