package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Node
import tools.vitruv.framework.change.echange.EChange

interface EChangeNode extends Node {
	def EChange getEChange()

	def void setEChange(EChange e)
}
