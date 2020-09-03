package tools.vitruv.domains.insurance

import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import edu.kit.ipd.sdq.metamodels.insurance.InsurancePackage
import tools.vitruv.domains.insurance.tuid.InsuranceTuidCalculatorAndResolver

class InsuranceDomain extends AbstractTuidAwareVitruvDomain {
	static final String METAMODEL_NAME = "Insurance";
	static val FILE_EXTENSION = "insurance"
	public static val NAMESPACE_URIS = InsurancePackage.eINSTANCE.nsURIsRecursive;

	package new() {
		super(METAMODEL_NAME, InsurancePackage.eINSTANCE, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new InsuranceTuidCalculatorAndResolver(InsurancePackage.eNS_URI);
	}

	override getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
	override boolean isUserVisible() {
		return false;
	}
	
}
