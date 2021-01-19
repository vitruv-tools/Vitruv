package tools.vitruv.testutils.domains;

import java.util.List;

import pcm_mockup.Pcm_mockupPackage;

public final class PcmMockupDomain extends VitruvTestDomain {
	public static final String METAMODEL_NAME = "PcmMockup";
	public static final String FILE_EXTENSION = "pcm_mockup";

	PcmMockupDomain() {
		super(METAMODEL_NAME, Pcm_mockupPackage.eINSTANCE, List.of(Pcm_mockupPackage.Literals.IDENTIFIED__ID),
				FILE_EXTENSION);
	}

}