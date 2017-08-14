package tools.vitruv.framework.testutils.domains;

import allElementTypes2.AllElementTypes2Package;
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator;
import tools.vitruv.framework.domains.AbstractVitruvDomain;
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;

public final class AllElementTypes2Domain extends AbstractVitruvDomain {
	public static final String METAMODEL_NAME = "AllElementTypes2";
	public static final String FILE_EXTENSION = "allElementTypes2";
	
	AllElementTypes2Domain() {
		super(METAMODEL_NAME, AllElementTypes2Package.eINSTANCE, 
				new AttributeTuidCalculatorAndResolver(AllElementTypes2Package.eNS_URI, AllElementTypes2Package.Literals.IDENTIFIED2__ID2.getName()), 
				FILE_EXTENSION);
	}

	@Override
	public VitruviusProjectBuilderApplicator getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
}
