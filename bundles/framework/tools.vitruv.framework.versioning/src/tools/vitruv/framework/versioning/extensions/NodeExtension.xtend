package tools.vitruv.framework.versioning.extensions

import org.graphstream.graph.Node
import tools.vitruv.framework.versioning.extensions.impl.NodeExtensionImpl

interface NodeExtension {
	static NodeExtension instance = NodeExtensionImpl::init

	def boolean isLeave(Node node)

	def void setLabel(Node node, String label)

}
