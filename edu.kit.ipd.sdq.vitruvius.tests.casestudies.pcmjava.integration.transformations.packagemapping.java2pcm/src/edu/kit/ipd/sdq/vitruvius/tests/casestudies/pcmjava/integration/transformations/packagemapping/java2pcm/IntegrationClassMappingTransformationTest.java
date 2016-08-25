package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.integration.transformations.packagemapping.java2pcm;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.BasicComponent;

import edu.kit.ipd.sdq.vitruvius.codeintegration.tests.CodeIntegrationTestCBSNamespace;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.CompilationUnitManipulatorHelper;

public class IntegrationClassMappingTransformationTest extends Java2PCMPackageIntegrationMappingTransformationTest {

	@Test
	public void testAddClassInIntegratedArea() throws Throwable {
		this.createClassInPackage(NAME_OF_INTEGRATED_CLASS,
				CodeIntegrationTestCBSNamespace.PACKAGE_NAME_OF_DISPLAY_COMPONENT);

		assertMessage(1, "Created class or interface in area with integrated object");
		assertNoComponentWithName(NAME_OF_INTEGRATED_CLASS);
	}

	@Test
	public void testAddClassInNonIntegratedArea() throws Throwable {
		this.testUserInteractor.addNextSelections(SELECT_BASIC_COMPONENT);
		final BasicComponent bc = super.createSecondPackage(BasicComponent.class, NAME_OF_NOT_INTEGRATED_PACKAGE);

		// add Class in package that should correspond to a basic component
		// after it has been added
		this.testUserInteractor.addNextSelections(0);
		BasicComponent bcForClass = super.addClassInSecondPackage(BasicComponent.class);

		super.assertRepositoryAndPCMName(bc.getRepository__RepositoryComponent(), bc, bcForClass.getEntityName());
		assertNoUserInteractingMessage();
	}

}