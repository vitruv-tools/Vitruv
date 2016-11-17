package tools.vitruv.domains.asem

import tools.vitruv.domains.asem.AsemMetamodel

class AsemDomain {
	private val AsemMetamodel asemMetamodel;
	
	public new() {
		asemMetamodel = new AsemMetamodel();
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public AsemMetamodel getMetamodel() {
		return asemMetamodel;
	}
}