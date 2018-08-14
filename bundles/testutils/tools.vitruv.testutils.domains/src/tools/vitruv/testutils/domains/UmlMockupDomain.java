package tools.vitruv.testutils.domains;

import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator;
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain;
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator;
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver;
import uml_mockup.Uml_mockupPackage;

public final class UmlMockupDomain extends AbstractTuidAwareVitruvDomain {
	public static final String METAMODEL_NAME = "UmlMockup";
	public static final String FILE_EXTENSION = "uml_mockup";

	UmlMockupDomain() {
		super(METAMODEL_NAME, Uml_mockupPackage.eINSTANCE, new AttributeTuidCalculatorAndResolver(
				Uml_mockupPackage.eNS_URI, Uml_mockupPackage.Literals.IDENTIFIED__ID.getName()), FILE_EXTENSION);
	}

	@Override
	public VitruviusProjectBuilderApplicator getBuilderApplicator() {
		return new VitruviusEmfBuilderApplicator();
	}
	
	@Override
	public boolean isUserVisible() {
		return false;
	}
	
}
