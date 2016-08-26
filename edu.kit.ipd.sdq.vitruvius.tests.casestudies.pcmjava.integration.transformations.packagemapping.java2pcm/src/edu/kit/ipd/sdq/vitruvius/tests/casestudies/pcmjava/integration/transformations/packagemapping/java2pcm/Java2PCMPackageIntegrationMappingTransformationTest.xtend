package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.integration.transformations.packagemapping.java2pcm

import edu.kit.ipd.sdq.vitruvius.codeintegration.tests.CodeIntegrationTestCBSNamespace
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm.util.CodeIntegrationUtils
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.packagemapping.java2pcm.Java2PCMPackageMappingTransformationTest
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.junit.runner.Description
import java.util.Iterator
import java.util.Collection
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import org.palladiosimulator.pcm.repository.RepositoryComponent
import java.util.Set
import org.palladiosimulator.pcm.repository.Repository
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceModelUtil
import edu.kit.ipd.sdq.vitruvius.framework.change2commandtransformingprovider.AbstractChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.codeintegration.change2command.Java2PCMIntegrationChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.codeintegration.change2command.PCM2JavaIntegrationChange2CommandTransforming

class Java2PCMPackageIntegrationMappingTransformationTest extends Java2PCMPackageMappingTransformationTest {

	val public static String NAME_OF_NOT_INTEGRATED_PACKAGE = "nonIntegratedPackage"
	val public static String NAME_OF_INTEGRATED_CLASS = "IntegratedClass"
	val public static String NAME_OF_INTEGRATED_PACKAGE = "packageInIntegratedArea"
	val public static String INTEGRATED_METHOD_NAME = "integratedMethodName"
	val public static String NON_INTEGRATED_METHOD_NAME = "nonIntegradedMethodName"

	new() {
		super([| AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
		 	#[new Java2PCMIntegrationChange2CommandTransforming(), new PCM2JavaIntegrationChange2CommandTransforming()]
		)])
	}

	override protected void beforeTest(Description description) throws Throwable {
		super.beforeTest(description)
	}
	
	override protected void createTestProject(Description description) throws CoreException {
		val workspace = ResourcesPlugin.getWorkspace()
		this.currentTestProjectName = CodeIntegrationTestCBSNamespace.TEST_PROJECT_NAME
		CodeIntegrationUtils.importTestProjectFromBundleData(workspace,
			this.currentTestProjectName, CodeIntegrationTestCBSNamespace.TEST_BUNDLE_NAME,
			CodeIntegrationTestCBSNamespace.SOURCE_CODE_PATH) 
		
		this.currentTestProject = workspace.getRoot().getProject(CodeIntegrationTestCBSNamespace.TEST_PROJECT_NAME)
		CodeIntegrationUtils.integratProject(currentTestProject)
	}
	
	
	def protected void assertMessage(int expectedSize, String... expectedMessages) {
		val Collection<String> messageLog = super.testUserInteractor.getMessageLog();
		assertEquals("Size of message log is wrong", expectedSize, messageLog.size());
		
		val Iterator<String> iterator = messageLog.iterator();
		var int i = 0;
		while (iterator.hasNext()) {
			val String nextMessage = iterator.next();
			val String expectedNexMessage = expectedMessages.get(i++);
			assertTrue("The message '" + nextMessage + "' does not contain the expected message '" + expectedNexMessage +"'.",
					nextMessage.contains(expectedNexMessage));
		}
	}
	
	def protected void assertNoUserInteractingMessage() {
		assertMessage(0)
	}
	
	 def protected void assertNoComponentWithName(String nameOfComponent) throws Throwable {
    	val Set<RepositoryComponent> repoComponents = CorrespondenceModelUtil.getAllEObjectsOfTypeInCorrespondences(getCorrespondenceModel(), RepositoryComponent);
    	assertNoBasicComponentWithName(nameOfComponent, repoComponents);
    	val Set<Repository> repos = CorrespondenceModelUtil.getAllEObjectsOfTypeInCorrespondences(getCorrespondenceModel(), Repository);
    	repos.forEach[assertNoBasicComponentWithName(nameOfComponent, it.getComponents__Repository())];
	}

	def private void assertNoBasicComponentWithName(String nameOfComponent, Iterable<RepositoryComponent> repoComponents) {
		for (RepositoryComponent repoComponent : repoComponents) {
			assertTrue("basic component with name " + nameOfComponent + " found: " + repoComponent,!repoComponent.getEntityName().contains(nameOfComponent))
		}
	}
	
}
