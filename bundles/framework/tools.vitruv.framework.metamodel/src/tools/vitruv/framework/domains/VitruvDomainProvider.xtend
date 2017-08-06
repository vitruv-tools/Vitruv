package tools.vitruv.framework.domains

interface VitruvDomainProvider<D extends VitruvDomain> {
	abstract def D getDomain();
}
