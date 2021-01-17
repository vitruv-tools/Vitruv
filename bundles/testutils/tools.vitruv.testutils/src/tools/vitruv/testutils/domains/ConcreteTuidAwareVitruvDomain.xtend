package tools.vitruv.testutils.domains

import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import org.eclipse.emf.ecore.EPackage
import java.util.Set
import tools.vitruv.framework.tuid.TuidCalculatorAndResolver

class ConcreteTuidAwareVitruvDomain extends AbstractTuidAwareVitruvDomain {
	new(String name, EPackage metamodelRootPackage, TuidCalculatorAndResolver tuidCalculator,
		String... fileExtensions) {
		super(name, metamodelRootPackage, tuidCalculator, fileExtensions)
	}

	new(String name, EPackage metamodelRootPackage, Set<EPackage> furtherRootPackages,
		TuidCalculatorAndResolver tuidCalculator, String... fileExtensions) {
		super(name, metamodelRootPackage, furtherRootPackages, tuidCalculator, fileExtensions)
	}

}
