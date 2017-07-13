package tools.vitruv.domains.families

import tools.vitruv.framework.tuid.TuidCalculatorAndResolver
import tools.vitruv.framework.domains.AbstractVitruvDomain
import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage
import tools.vitruv.domains.families.tuid.FamiliesTuidCalculatorAndResolver
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator

class FamiliesDomain extends AbstractVitruvDomain {
	private static final String METAMODEL_NAME = "Families";
	private static val FILE_EXTENSION = "families"
	public static val NAMESPACE_URIS = FamiliesPackage.eINSTANCE.nsURIsRecursive;

	package new() {
		super(METAMODEL_NAME, FamiliesPackage.eINSTANCE, generateTuidCalculator(), FILE_EXTENSION);
	}

	def protected static TuidCalculatorAndResolver generateTuidCalculator() {
		return new FamiliesTuidCalculatorAndResolver(FamiliesPackage.eNS_URI);
	}

	override getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
}
