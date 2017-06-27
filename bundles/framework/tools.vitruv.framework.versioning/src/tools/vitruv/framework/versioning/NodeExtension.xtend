package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.impl.NodeExtensionImpl
import org.graphstream.graph.Node

interface NodeExtension {
	static def NodeExtension newManager() {
		NodeExtensionImpl::init
	}

	def boolean isLeave(Node node)

	def void setLabel(Node node, String label)
}
