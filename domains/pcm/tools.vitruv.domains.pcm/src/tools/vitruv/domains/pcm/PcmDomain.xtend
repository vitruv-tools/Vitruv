package tools.vitruv.domains.pcm

class PcmDomain {
	private static PcmDomain instance;
	private val PcmMetamodel pcMMetamodel;
	
	private new() {
		pcMMetamodel = new PcmMetamodel();
	}
	
	public static def PcmDomain getInstance() {
		if (instance === null) {
			instance = new PcmDomain();
		}
		return instance;
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public PcmMetamodel getMetamodel() {
		return pcMMetamodel;
	}
}