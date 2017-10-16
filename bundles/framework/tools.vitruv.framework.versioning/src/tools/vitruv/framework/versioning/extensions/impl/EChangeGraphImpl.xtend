package tools.vitruv.framework.versioning.extensions.impl

import java.util.Collection
import java.util.Map
import java.util.Set
import java.util.UUID

import org.eclipse.xtext.xbase.lib.Functions.Function1

import org.graphstream.algorithm.ConnectedComponents
import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.stream.file.FileSinkImages
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy
import org.graphstream.stream.file.FileSinkImages.OutputType
import org.graphstream.stream.file.FileSinkImages.Resolutions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertRootImpl
import tools.vitruv.framework.versioning.EChangeGraph
import tools.vitruv.framework.versioning.EdgeType
import tools.vitruv.framework.versioning.NodeType
import tools.vitruv.framework.versioning.extensions.EChangeEdge
import tools.vitruv.framework.versioning.extensions.EChangeExtension
import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.extensions.GraphStreamConstants

class EChangeGraphImpl extends SingleGraph implements EChangeGraph {
	// Static extensions.
	static extension EChangeExtension = EChangeExtension::instance

	// Static values. 
	static val VM_ARGUMENT_PRINT_PICTURES = "printPictures"
	static val Map<Graph, Integer> graphMap = newHashMap
	static val String styleSheet = '''
		node {
			fill-color: blue;
		}
		
		node.graph0 {
			fill-color: green;
		}
		node.graph1 {
			fill-color: yellow;
		}
		node.«NodeType::UNPAIRED.toString» {
			fill-color: red;
		}
		node.«CreateAndInsertRootImpl.simpleName» {
			size: 50px;
		}
		edge.«EdgeType::REQUIRED.toString» {
			visibility-mode: hidden;
		}
		edge.«EdgeType::REQUIRES.toString» {
			fill-color: red;
		}
		edge.«EdgeType::TRIGGERS.toString» {
			fill-color: black;
		}
		edge.«EdgeType::CONFLICTS.toString» {	
			fill-color: blue;
		}
		edge.«EdgeType::ISOMORPHIC.toString» {
			fill-color: brown;
		}
	'''

	new(String id) {
		super(id)
		nodeFactory = new EChangeNodeFactoryImpl
		edgeFactory = new EChangeEdgeFactoryImpl
		addAttribute(GraphStreamConstants::stylesheet, styleSheet)
	}

	static def EChangeGraph createNewEChangeGraph() {
		val id = UUID.randomUUID.toString
		new EChangeGraphImpl(id)
	}

	// Overridden methods.
	override addNode(EChange e) {
		val EChangeNode node = this.addNode(e.nodeId)
		node.label = e.nodeLabel
		node.EChange = e
		return node
	}

	override getProvideLeaves() {
		determineLeaves([EChangeNode n|n.provideLeave])
	}

	override getLeaves() {
		determineLeaves([EChangeNode n|n.leave])
	}

	override getNode(EChange e) {
		getNode(e.nodeId)
	}

	override edgesWithType(EdgeType t) {
		edgeSet.filter[e|e.id.contains(t.toString)]
	}

	override checkIfEdgeExists(EChange e1, EChange e2) {
		val node1 = getNode(e1)
		val node2 = getNode(e2)
		val x = node1.hasEdgeBetween(node2)
		return x
	}

	override checkIfEdgeExists(EChange e1, EChange e2, EdgeType type) {
		val edgeExists = checkIfEdgeExists(e1, e2)
		if(!edgeExists)
			return false
		val edge = getNode(e1).<EChangeEdge>getEdgeBetween(getNode(e2))
		return edge.isType(type)
	}

	override addEdge(EChange fromEchange, EChange toEChange, EdgeType type) {
		switch (type) {
			case REQUIRED: {
				addDirectedEdge(fromEchange, toEChange, type)
				addDirectedEdge(toEChange, fromEchange, type.inverse)
			}
			case TRIGGERS: {
				addDirectedEdge(fromEchange, toEChange, type)
			}
			case ISOMORPHIC: {
				addUndirectedEdge(fromEchange, toEChange, type)
			}
			case CONFLICTS: {
				addUndirectedEdge(fromEchange, toEChange, type)
			}
			default: {
				throw new UnsupportedOperationException
			}
		}
	}

	override cloneGraph(Function1<EChangeNode, Boolean> nodePredicate, Function1<EChangeEdge, Boolean> edgePredicate) {
		val newGraph = createNewEChangeGraph
		this.<EChangeNode>nodeSet.filter(nodePredicate).forEach [ EChangeNode oldNode |
			val EChangeNode node = newGraph.addNode(oldNode.id)
			node.EChange = oldNode.EChange
			node.triggered = oldNode.triggered
			node.vuri = oldNode.vuri
			val uiLabel = oldNode.<String>getAttribute(GraphStreamConstants::uiLabel)
			node.setAttribute(GraphStreamConstants::uiLabel, uiLabel)
			val uiClass = oldNode.<String>getAttribute(GraphStreamConstants::uiClass)
			node.setAttribute(GraphStreamConstants::uiClass, uiClass)
		]
		this.<EChangeEdge>edgeSet.filter [
			nodePredicate.apply(sourceNode) && nodePredicate.apply(targetNode)
		].filter(edgePredicate).forEach [ edge |
			val newSourceNode = newGraph.getNode(edge.sourceNode.id)
			val newTargetNode = newGraph.getNode(edge.targetNode.id)
			val newEdge = newGraph.<EChangeEdge>addEdge(edge.id, newSourceNode, newTargetNode, edge.directed)
			newEdge.type = edge.type
		]
		return newGraph
	}

	override getSubgraphs() {
		val requiredGraph = cloneGraph([true], [isType(EdgeType::REQUIRED)])
		val currentLeaves = requiredGraph.provideLeaves
		val Collection<EChangeGraph> currentGraphs = newArrayList
		currentLeaves.forEach [ leave |
			val g = createNewEChangeGraph
			leave.breadthFirstIterator.forEach[EChangeNode n|g.addNode(n.EChange)]
			requiredGraph.edgeSet.forEach [ edge |
				val source = g.getNode(edge.sourceNode.id)
				val target = g.getNode(edge.targetNode.id)
				if(null !== source && null !== target)
					g.addEdge(edge.id, source, target)
			]
			currentGraphs += g
		]
		return currentGraphs
	}

	override calculateComponentNumber() {
		val newGraph = cloneGraph([true], [isType(EdgeType::REQUIRED)])
		val cc = new ConnectedComponents
		cc.init(newGraph)
		return cc.connectedComponentsCount
	}

	override getSubgraphContainingEChanges(Set<EChange> nodes) {
		cloneGraph([EChangeNode n|nodes.contains(n.EChange)], [
			isType(EdgeType::REQUIRED) || isType(EdgeType::TRIGGERS)
		])
	}

	override savePicture(String name) {
		val isPrintActive = null !== System.getProperty(VM_ARGUMENT_PRINT_PICTURES)
		if(isPrintActive) {
			val fsi = new FileSinkImages(OutputType.PNG, Resolutions.HD720)
			fsi.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE)
			write(fsi, '''./test_«name»_write.png''')
		}
	}

	override savePicture() {
		savePicture(id)
	}

	override add(EChangeGraph graphToAdd) {
		val x = if(graphMap.containsKey(this)) {
				graphMap.remove(this)
			} else
				0
		graphToAdd.<EChangeNode>nodeSet.forEach [ node |
			val newNode = <EChangeNode>addNode(node.id)
			attributeKeySet.forEach [ attKey |
				val attribute = <String>getAttribute(attKey)
				newNode.addAttribute(attKey, attribute)
			]
			val uiLabel = node.<String>getAttribute(GraphStreamConstants::uiLabel)
			newNode.setAttribute(GraphStreamConstants::uiLabel, uiLabel)
			newNode.setAttribute(GraphStreamConstants::uiClass, '''graph«x», «node.getAttribute(GraphStreamConstants::uiClass, String)»''')
			newNode.EChange = node.EChange
			newNode.vuri = node.vuri
			
		]
		graphMap.put(this, x + 1)
		graphToAdd.edgeSet.forEach [ currentEdge |
			val newSourceNode = getNode(currentEdge.sourceNode.id)
			val newTargetNode = getNode(currentEdge.targetNode.id)
			val edge = addEdge(currentEdge.id, newSourceNode, newTargetNode, currentEdge.directed)
			attributeKeySet.forEach [ attKey |
				val attribute = <String>getAttribute(attKey)
				edge.addAttribute(attKey, attribute)
			]
		]
	}

	// Private methods.
	private def addDirectedEdge(EChange e1, EChange e2, EdgeType type) {
		addEdge(e1, e2, type, true)
	}

	private def addUndirectedEdge(EChange e1, EChange e2, EdgeType type) {
		addEdge(e1, e2, type, false)
	}

	private def addEdge(EChange e1, EChange e2, EdgeType type, boolean directed) {
		val edge = addChangeEdge(createEdgeName(e1, e2, type), e1, e2, directed)
		edge.type = type
	}

	private def EChangeEdge addChangeEdge(String n, EChange e1, EChange e2, boolean directed) {
		<EChangeEdge>addEdge(n, e1.nodeId, e2.nodeId, directed)
	}

	private def determineLeaves(Function1<EChangeNode, Boolean> nodePredicate) {
		<EChangeNode>nodeSet.filter(nodePredicate)
	}
}
