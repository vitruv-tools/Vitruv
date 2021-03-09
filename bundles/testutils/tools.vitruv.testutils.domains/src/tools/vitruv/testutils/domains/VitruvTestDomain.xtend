package tools.vitruv.testutils.domains

import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EAttribute
import tools.vitruv.framework.domains.AbstractVitruvDomain

package abstract class VitruvTestDomain extends AbstractVitruvDomain {

	new(String name, EPackage metamodelRootPackage, Iterable<EAttribute> identifyingAttributes,
		String... fileExtensions) {
		super(name, metamodelRootPackage, fileExtensions)
	}

	override isUserVisible() {
		false
	}

}
