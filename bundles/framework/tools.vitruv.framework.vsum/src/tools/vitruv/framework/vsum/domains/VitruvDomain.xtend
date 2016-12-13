package tools.vitruv.framework.vsum.domains

import tools.vitruv.framework.metamodel.Metamodel

interface VitruvDomain<T extends Metamodel> {
	public static String EXTENSION_POINT_ID = "tools.vitruv.framework.vsum.domain"
	
	def T getMetamodel();	
}