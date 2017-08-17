package tools.vitruv.framework.versioning

import java.util.Map
import java.util.Set

import tools.vitruv.framework.versioning.extensions.EChangeNode
import tools.vitruv.framework.versioning.impl.PrimitiveIsomorphismTesterImpl

interface IsomorphismTesterAlgorithm extends IsomorphismTester {
	static def IsomorphismTesterAlgorithm createIsomorphismTester() {
		PrimitiveIsomorphismTesterImpl::createPrimitiveIsomorphismTesterImpl
	}

	def EChangeGraph getCombinedGraph()

	def Map<EChangeNode, EChangeNode> getIsomorphism()

	def Set<EChangeNode> getUnmatchedOfGraph1()

	def Set<EChangeNode> getUnmatchedOfGraph2()

	def boolean isIsomorphic()

	def void compute()

	def void init(EChangeGraph g1, EChangeGraph g2)
}
