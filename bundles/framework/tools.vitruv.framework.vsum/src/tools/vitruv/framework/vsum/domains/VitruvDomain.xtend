package tools.vitruv.framework.vsum.domains

import tools.vitruv.framework.metamodel.Metamodel

interface VitruvDomain<T extends Metamodel> {
	def T getMetamodel();	
}