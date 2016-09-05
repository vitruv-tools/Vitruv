package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.integration.transformations.packagemapping.java2pcm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.OperationInterface;

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.linkingintegration.tests.CodeIntegrationTestCBSNamespace;

public class IntegrationInterfaceMappingTransformationTest extends Java2PCMPackageIntegrationMappingTransformationTest{

	private static final String INTEGRATED_INTERFACE_NAME = "IntegratedInterface";
	private static final String NAME_OF_NOT_INTEGRATED_INTERFACE = "NotIntegratedInterface";

	@Test
	public void addInterfaceInIntegratedArea() throws Throwable{
		OperationInterface opInterface = this.createInterfaceInPackage(CodeIntegrationTestCBSNamespace.PACKAGE_NAME_OF_DISPLAY_COMPONENT, INTEGRATED_INTERFACE_NAME, false);
		
		assertEquals(opInterface, null);
		assertMessage(1, "Created class or interface in area with integrated object");
	}
	
	@Test
	public void addInterfaceInNonIntegratedArea() throws Throwable{
		this.testUserInteractor.addNextSelections(SELECT_NOTHING_DECIDE_LATER);
		createPackageWithPackageInfo(NAME_OF_NOT_INTEGRATED_PACKAGE);
		
		this.testUserInteractor.addNextSelections(0);
		OperationInterface opInterface = createInterfaceInPackage(NAME_OF_NOT_INTEGRATED_PACKAGE,NAME_OF_NOT_INTEGRATED_INTERFACE, true);
		
		this.assertOperationInterface(opInterface.getRepository__Interface(), opInterface, NAME_OF_NOT_INTEGRATED_INTERFACE);
		this.assertNoUserInteractingMessage();
	}
}
