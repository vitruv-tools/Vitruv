package tools.vitruv.testutils.domains;

import java.util.List;

import allElementTypes2.AllElementTypes2Package;

public final class AllElementTypes2Domain extends VitruvTestDomain {
	public static final String METAMODEL_NAME = "AllElementTypes2";
	public static final String FILE_EXTENSION = "allElementTypes2";

	AllElementTypes2Domain() {
		super(METAMODEL_NAME, AllElementTypes2Package.eINSTANCE,
				List.of(AllElementTypes2Package.Literals.IDENTIFIED2__ID2), FILE_EXTENSION);
	}

}
