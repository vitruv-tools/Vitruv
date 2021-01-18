package tools.vitruv.testutils.domains

import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain

package abstract class VitruvTestDomain extends AbstractTuidAwareVitruvDomain {

	new(String name, EPackage metamodelRootPackage, Iterable<EAttribute> identifyingAttributes,
		String... fileExtensions) {
		super(name, metamodelRootPackage,
			new AttributeTuidCalculatorAndResolver(metamodelRootPackage.nsURI, identifyingAttributes.map[name]),
			fileExtensions)
	}

	override isUserVisible() {
		false
	}

}
