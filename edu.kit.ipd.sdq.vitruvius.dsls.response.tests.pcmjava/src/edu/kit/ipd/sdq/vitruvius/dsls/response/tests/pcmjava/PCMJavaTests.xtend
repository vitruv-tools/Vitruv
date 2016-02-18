package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractPCMJavaTests
import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Repository
import static org.junit.Assert.*;
import org.emftext.language.java.containers.CompilationUnit

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
	
	private static def getJavaClassPathForFQN(String fqn) {
		return "src/" + fqn.replace(".", "/") + "." + "java";
	}
	
	override protected initializeTestModel() {
		val root = RepositoryFactory.eINSTANCE.createRepository();
		root.entityName = TEST_REPOSITORY_NAME;
		createAndSychronizeModel(TEST_REPOSITORY_NAME.projectRepositoryPath, root);
	}
	
	@Test
	public def void testCreateRepository() {
		val helper = PCMJavaHelper.repositoryToJavaHelper;
		val repository = repositoryRootElement;
		checkCompilationUnitConsistencyAndName(helper.getQualifiedClassName(repository).javaClassPathForFQN, helper.getClassName(repository));	
	}
	
	@Test
	public def void testCreateBasicComponent() {
		val helper = PCMJavaHelper.componentToClass;
		val component = RepositoryFactory.eINSTANCE.createBasicComponent();
		repositoryRootElement.components__Repository += component;
		component.entityName = "MyComponent";
		saveAndSynchronizeChanges(repositoryRootElement);
		checkCompilationUnitConsistencyAndName(helper.getQualifiedClassName(component).javaClassPathForFQN, helper.getClassName(component));
	}
	
	@Test
	public def void testCreateInterface() {
		val helper = PCMJavaHelper.interfaceToInterface;
		val interf = RepositoryFactory.eINSTANCE.createInfrastructureInterface();
		repositoryRootElement.interfaces__Repository += interf;
		interf.entityName = "MyInterface";
		saveAndSynchronizeChanges(repositoryRootElement);
		checkCompilationUnitConsistencyAndName(helper.getQualifiedClassName(interf).javaClassPathForFQN, helper.getClassName(interf));	
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