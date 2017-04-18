package tools.vitruv.framework.testutils.domains;

import allElementTypes.AllElementTypesPackage;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;

public final class AllElementTypesDomain extends Metamodel {
	public static final String METAMODEL_NAME = "AllElementTypes";
	public static final String FILE_EXTENSION = "allElementTypes";
	
	AllElementTypesDomain() {
		super(METAMODEL_NAME, AllElementTypesPackage.eINSTANCE, 
				new AttributeTuidCalculatorAndResolver(AllElementTypesPackage.eNS_URI, AllElementTypesPackage.Literals.IDENTIFIED__ID.getName()), 
				FILE_EXTENSION);
	}
	
}
