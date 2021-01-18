package tools.vitruv.testutils.domains;

import java.util.List;

import allElementTypes.AllElementTypesPackage;

public final class AllElementTypesDomain extends VitruvTestDomain {
	public static final String METAMODEL_NAME = "AllElementTypes";
	public static final String FILE_EXTENSION = "allElementTypes";

	AllElementTypesDomain() {
		super(METAMODEL_NAME, AllElementTypesPackage.eINSTANCE, List.of(AllElementTypesPackage.Literals.IDENTIFIED__ID),
				FILE_EXTENSION);
	}

}
