package tools.vitruv.domains.demo

import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.domains.AbstractVitruvDomain

abstract class VitruvDemoDomain extends AbstractVitruvDomain {

	new(String name, EPackage metamodelRootPackage, Iterable<EAttribute> identifyingAttributes, String... fileExtensions) {
		super(name, metamodelRootPackage, fileExtensions)
	}

	override isUserVisible() {
		false
	}

}
