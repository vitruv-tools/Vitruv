package tools.vitruv.domains.java

class JavaDomain {
	private val JavaMetamodel javaMetamodel;
	
	public new() {
		javaMetamodel = new JavaMetamodel();
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public JavaMetamodel getMetamodel() {
		return javaMetamodel;
	}
}