package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses

import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Repository
import static org.junit.Assert.*;
import org.emftext.language.java.containers.CompilationUnit
import org.palladiosimulator.pcm.repository.RepositoryComponent
import org.palladiosimulator.pcm.repository.Interface
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.PCM2JavaHelper
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.responses.PCMJavaChange2CommandTransformingProviding

class PCMJavaTests extends AbstractPCMJavaTests {
	private static final String TEST_REPOSITORY_NAME = "testRepo"
	
	new() {
		super([| new PCMJavaChange2CommandTransformingProviding()]);
	}

	private def Repository getRepositoryRootElement() {
		return TEST_REPOSITORY_NAME.projectRepositoryPath.root as Repository;
	}
	
	private static def getProjectRepositoryPath(String modelName) {
		return "model/" + modelName + "." + "repository";
	}
	
	override protected initializeTestModel() {
		val root = RepositoryFactory.eINSTANCE.createRepository();
		root.entityName = TEST_REPOSITORY_NAME;
		createAndSychronizeModel(TEST_REPOSITORY_NAME.projectRepositoryPath, root);
	}
	
	@Test
	public def void testCreateRepository() {
		val repository = repositoryRootElement;
		val repositoryPath = PCM2JavaHelper.buildJavaFilePath("package-info", #[repository.entityName]);
		assertModelExists(repositoryPath);
		val rootObject = repositoryPath.root;
		assertTrue(rootObject instanceof org.emftext.language.java.containers.Package);
	}
	
	@Test
	public def void testCreateBasicComponent() {
		val component = RepositoryFactory.eINSTANCE.createBasicComponent();
		repositoryRootElement.components__Repository += component;
		component.entityName = "MyComponent";
		saveAndSynchronizeChanges(repositoryRootElement);
		checkCompilationUnitConsistencyAndName(component.javaPath, component.entityName + "Impl");
	}
	
	private def String getJavaPath(RepositoryComponent component) {
		return PCM2JavaHelper.buildJavaFilePath(component.entityName + "Impl", #[component.repository__RepositoryComponent.entityName, component.entityName])
	}
	
	private def String getJavaPath(Interface interf) {
		return PCM2JavaHelper.buildJavaFilePath(interf.entityName, #[interf.repository__Interface.entityName, "contracts"])
	}
	
	@Test
	public def void testCreateInterface() {
		val interf = RepositoryFactory.eINSTANCE.createInfrastructureInterface();
		repositoryRootElement.interfaces__Repository += interf;
		interf.entityName = "MyInterface";
		saveAndSynchronizeChanges(repositoryRootElement);
		checkCompilationUnitConsistencyAndName(interf.javaPath, interf.entityName);	
	}
	
	private def checkCompilationUnitConsistencyAndName(String compilationUnitPath, String expectedName) {
		assertModelExists(compilationUnitPath);
		val javaRootEObject = compilationUnitPath.root;
		assertTrue(javaRootEObject instanceof CompilationUnit);
		val compilationUnit = javaRootEObject as CompilationUnit;
		assertEquals(1, compilationUnit.classifiers.size);
		val clazz = compilationUnit.classifiers.get(0);
		assertEquals(expectedName, clazz.name);
	}
}