package tools.vitruv.domains.demo

import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

abstract class VitruvDemoDomain extends AbstractTuidAwareVitruvDomain {

	new(String name, EPackage metamodelRootPackage, Iterable<EAttribute> identifyingAttributes, String... fileExtensions) {
		super(name, metamodelRootPackage,
			new AttributeTuidCalculatorAndResolver(metamodelRootPackage.nsURI, identifyingAttributes.map[name]),
			fileExtensions)
	}

	override isUserVisible() {
		false
	}

}
