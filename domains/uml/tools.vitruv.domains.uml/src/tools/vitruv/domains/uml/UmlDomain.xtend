package tools.vitruv.domains.uml

class UmlDomain {
	private val UmlMetamodel umlMetamodel;
	
	public new() {
		umlMetamodel = new UmlMetamodel();
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public UmlMetamodel getMetamodel() {
		return umlMetamodel;
	}
}