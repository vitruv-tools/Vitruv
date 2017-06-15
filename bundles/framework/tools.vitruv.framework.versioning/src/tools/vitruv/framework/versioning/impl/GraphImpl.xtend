package tools.vitruv.framework.versioning.impl

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Queue
import tools.vitruv.framework.versioning.Graph
import tools.vitruv.framework.versioning.Node

class GraphImpl<T> implements Graph<T> {
	val Map<T, Node<T>> adjacencyList

	new() {
		adjacencyList = new HashMap
	}

	override addVertex(T vertex) {
		if (adjacencyList.containsKey(vertex))
			return false

		adjacencyList.put(vertex, new NodeImpl(vertex))
		return true
	}

	override addEdge(T vertex1, T vertex2) {
		addEdge(vertex1, vertex2, 0)
	}

	override addEdge(T vertex1, T vertex2, int weight) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2))
			throw new RuntimeException("Vertex does not exist")

		val node1 = getNode(vertex1)
		val node2 = getNode(vertex2)
		return node1.addEdge(node2, weight)
	}

	override removeVertex(T vertex) {
		if (!adjacencyList.containsKey(vertex))
			return false

		// get node to be removed
		val toRemove = getNode(vertex)
		// remove all incoming edges to node
		adjacencyList.values.forEach([node|node.removeEdge(toRemove)])
		// remove the node
		adjacencyList.remove(vertex)
		true
	}

	override removeEdge(T vertex1, T vertex2) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2))
			return false
		getNode(vertex1).removeEdge(getNode(vertex2))
	}

	override vertexCount() {
		adjacencyList.keySet.size
	}

	override edgeCount() {
		val x = adjacencyList.values().stream().mapToInt[it.edgeCount].sum
		return x
	}

	override containsVertex(T vertex) {
		adjacencyList.containsKey(vertex)
	}

	override containsEdge(T vertex1, T vertex2) {
		if (!containsVertex(vertex1) || !containsVertex(vertex2))
			false
		else
			getNode(vertex1).hasEdge(getNode(vertex2))
	}

	override shortestPath(T startVertex, T endVertex) {
		// if nodes not found, return empty path
		if (!containsVertex(startVertex) || !containsVertex(endVertex))
			return null
		// run bfs on the graph
		runBFS(startVertex)
		val List<T> path = new ArrayList
		// trace path back from end vertex to start
		var end = getNode(endVertex)
		while (end !== null && end !== getNode(startVertex)) {
			path.add(end.vertex)
			end = end.parent
		}
		// if end is null, node not found
		if (end === null)
			return null
		Collections::reverse(path)
		return path
	}

	override getNode(T value) {
		adjacencyList.get(value)
	}

	def private void resetGraph() {
		adjacencyList.keySet.forEach [
			val node = getNode(it)
			node.parent = null
			node.visited = false
		]
	}

	def private void runBFS(T startVertex) {
		if (!containsVertex(startVertex))
			throw new RuntimeException("Vertex does not exist.")

		// reset the graph
		resetGraph
		// init the queue
		val Queue<Node<T>> queue = new LinkedList
		val start = getNode(startVertex)
		queue.add(start)
		// explore the graph
		while (!queue.empty) {
			val Node<T> first = queue.remove
			first.visited = true
			first.edges.forEach [
				val neighbour = toNode
				if (!neighbour.visited) {
					neighbour.parent = first
					queue.add(neighbour)
				}
			]
		}
	}
}
