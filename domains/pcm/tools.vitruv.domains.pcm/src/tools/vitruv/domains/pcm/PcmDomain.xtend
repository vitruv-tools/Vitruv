package tools.vitruv.domains.pcm

class PcmDomain {
	private val PcmMetamodel pcmMetamodel;
	
	public new() {
		pcmMetamodel = new PcmMetamodel();
	}
	
	// TODO HK Replace with generic type parameter when introducing parametrized super class
	def public PcmMetamodel getMetamodel() {
		return pcmMetamodel;
	}
}