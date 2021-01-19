package tools.vitruv.testutils.domains;

import java.util.List;

import uml_mockup.Uml_mockupPackage;

public final class UmlMockupDomain extends VitruvTestDomain {
	public static final String METAMODEL_NAME = "UmlMockup";
	public static final String FILE_EXTENSION = "uml_mockup";

	UmlMockupDomain() {
		super(METAMODEL_NAME, Uml_mockupPackage.eINSTANCE, List.of(Uml_mockupPackage.Literals.IDENTIFIED__ID),
				FILE_EXTENSION);
	}

}
