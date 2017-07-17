package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Node
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.NodeType
import tools.vitruv.framework.util.datatypes.VURI

interface EChangeNode extends Node {
	def EChange getEChange()

	def void setEChange(EChange e)

	def void setType(NodeType type)

	def boolean isEChangeNodeEqual(EChangeNode node2)

	def boolean isTriggered()

	def void setTriggered(boolean t)

	def void setVuri(VURI v)

	def VURI getVuri()
}
