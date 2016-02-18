package edu.kit.ipd.sdq.vitruvius.dsls.response.tests.pcmjava

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractPCMJavaTests
import org.junit.Test
import org.palladiosimulator.pcm.repository.RepositoryFactory
import org.palladiosimulator.pcm.repository.Repository

class PCMJavaTests extends AbstractPCMJavaTests {
	private static final String TEST_REPOSITORY_NAME = "test_repo"
	
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
		assertModelExists(PCMJavaHelper.getCorrespondingJavaHelperQualifiedName(repositoryRootElement).javaClassPathForFQN);	
	}
	
	@Test
	public def void testCreateBasicComponent() {
		val component = RepositoryFactory.eINSTANCE.createBasicComponent();
		repositoryRootElement.components__Repository += component;
		component.entityName = "MyComponent";
		saveAndSynchronizeChanges(repositoryRootElement);
		assertModelExists(PCMJavaHelper.getCorrespondingJavaClassQualifiedName(component).javaClassPathForFQN);	
	}
	
}