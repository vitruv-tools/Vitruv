package tools.vitruv.framework.testutils.domains;

import allElementTypes.AllElementTypesPackage;
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator;
import tools.vitruv.framework.domains.AbstractVitruvDomain;
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;

public final class AllElementTypesDomain extends AbstractVitruvDomain {
	public static final String METAMODEL_NAME = "AllElementTypes";
	public static final String FILE_EXTENSION = "allElementTypes";
	
	AllElementTypesDomain() {
		super(METAMODEL_NAME, AllElementTypesPackage.eINSTANCE, 
				new AttributeTuidCalculatorAndResolver(AllElementTypesPackage.eNS_URI, AllElementTypesPackage.Literals.IDENTIFIED__ID.getName()), 
				FILE_EXTENSION);
	}

	@Override
	public VitruviusProjectBuilderApplicator getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
}
