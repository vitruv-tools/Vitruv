package tools.vitruv.testutils.domains;

import allElementTypes.AllElementTypesPackage;
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;

public final class AllElementTypesDomain extends AbstractTuidAwareVitruvDomain {
	public static final String METAMODEL_NAME = "AllElementTypes";
	public static final String FILE_EXTENSION = "allElementTypes";
	
	AllElementTypesDomain() {
		super(METAMODEL_NAME, AllElementTypesPackage.eINSTANCE, 
				new AttributeTuidCalculatorAndResolver(AllElementTypesPackage.eNS_URI, AllElementTypesPackage.Literals.IDENTIFIED__ID.getName()), 
				FILE_EXTENSION);
	}

	@Override
	public boolean isUserVisible() {
		return false;
	}
	
}
