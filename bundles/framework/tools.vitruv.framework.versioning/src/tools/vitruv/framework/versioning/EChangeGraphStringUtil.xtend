package tools.vitruv.framework.versioning

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.EChangeGraphStringUtilImpl

interface EChangeGraphStringUtil {
	static def EChangeGraphStringUtil newManager() {
		EChangeGraphStringUtilImpl::init
	}

	def String createEdgeName(EChange e1, EChange e2, EdgeType type)

	def String getNodeId(EChange e)

	def String getNodeLabel(EChange e)

}
