package tools.vitruv.domains.sysml

class SysMlDomain {
	private val SysMlMetamodel sysMlMetamodel;
	
	public new() {
		sysMlMetamodel = new SysMlMetamodel();
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public SysMlMetamodel getMetamodel() {
		return sysMlMetamodel;
	}
}