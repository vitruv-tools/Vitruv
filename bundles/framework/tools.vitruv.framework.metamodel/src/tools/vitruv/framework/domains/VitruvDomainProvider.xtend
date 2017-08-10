package tools.vitruv.framework.domains

interface VitruvDomainProvider<D extends VitruvDomain> {
	def D getDomain()
	
	/**
	 * Gets the string that code can use to reference a provider that will
	 * return the same domain as this provider.
	 */
	def String getCanonicalNameForReference() {
		this.class.canonicalName
	}
}
