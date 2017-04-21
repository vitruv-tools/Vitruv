package tools.vitruv.framework.applications

import tools.vitruv.framework.applications.VitruvApplication

abstract class AbstractVitruvApplication implements VitruvApplication {
	
	override getVitruvDomains() {
		changePropagationSpecifications.map[#[sourceDomain, targetDomain]].flatten.toSet
	}
	
}