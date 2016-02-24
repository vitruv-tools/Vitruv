package edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated;

import static edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil.createAttributeTUIDMetamodel;
import static edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil.createEmptyMetaRepository;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import pcm_mockup.Component;
import pcm_mockup.Repository;
import responses.ResponseChange2CommandTransformingProviding;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class TestMockupUMLPCM extends AbstractResponseTests {
	private final static String MODEL_PATH_PREFIX = "model/";
	
	private final static Logger LOGGER = Logger.getLogger(TestMockupUMLPCM.class);

	public TestMockupUMLPCM() {
		super(ResponseChange2CommandTransformingProviding::new);
	}

	@Override
	protected void initializeTestModel() {
		// do nothing
	}

	protected Collection<Pair<String, String>> getMetamodelURIsAndExtensions() {
		Set<Pair<String, String>> result = new HashSet<>();
		result.add(new Pair<>("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.pcm_mockup", "mpcm"));
		result.add(new Pair<>("http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.uml_mockup", "muml"));

		return result;
	}

	@Override
	protected MetaRepositoryImpl createMetaRepository() {
		return createEmptyMetaRepository(getMetamodelURIsAndExtensions().stream()
				.map(it -> createAttributeTUIDMetamodel(it.getFirst(), it.getSecond())).collect(Collectors.toList()));
	}

	private UPackage createAndSyncPackage(String name, String fileName) {
		UPackage upkg = Uml_mockupFactory.eINSTANCE.createUPackage();
		upkg.setName(name);
		createAndSychronizeModel(MODEL_PATH_PREFIX + fileName, upkg);

		return upkg;
	}

	private static <T> List<T> asList(T in) {
		List<T> result = new ArrayList<>();
		result.add(in);
		return result;
	}
	
	private TestUserInteractor testUserInteractor;
	
	@Override
	public void beforeTest(Description description) throws Throwable {
		super.beforeTest(description);
		LOGGER.trace("Test setupTestUserInteractor()");
		
		this.testUserInteractor = new TestUserInteractor();
		setUserInteractor(testUserInteractor);
	}
	
	private URI getLocalModelURI(String modelFileName) {
		return getModelVURI(MODEL_PATH_PREFIX + modelFileName).getEMFUri();
	}

	@Test
	public void createPackageAndSync() throws Throwable {
		LOGGER.trace("Test createPackageAndSync()");
		
		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(upkg));

		assertTrue("#[new package] must correspond with exactly one object list", correspondingEObjects.size() == 1);
		assertTrue("corresponding object list must have size one", head(correspondingEObjects).size() == 1);
		assertTrue("corresponding object must be a repository", head(head(correspondingEObjects)) instanceof Repository);
	}
	
	@Test
	public void createPackageAndSyncWithName() throws Throwable {
		LOGGER.trace("Test createPackageAndSyncWithName()");
		
		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(upkg));

		assertTrue("#[new package] must correspond with exactly one object", correspondingEObjects.size() == 1);
		assertTrue("corresponding object list must have size one", head(correspondingEObjects).size() == 1);
		assertTrue("corresponding object must be a repository", head(head(correspondingEObjects)) instanceof Repository);
		Repository repository = (Repository) (head(head(correspondingEObjects)));

		assertEquals("the package and the repository must have the same name", upkg.getName(), repository.getName());
	}
	
	@Test
	public void createPackageWithNestedClass() throws Throwable {
		LOGGER.trace("Test createPackageWithNestedClass()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
		clazz.setName("TestNestedClass");
		upkg.getClasses().add(clazz);
		saveAndSynchronizeChanges(upkg);

		assertTrue("Package must have exactly one nested class", upkg.getClasses().size() == 1);
		UClass clazzFromUpkg = head(upkg.getClasses());
		assertEquals("Nested class must has the name that was set for synchronization", clazzFromUpkg.getName(), "TestNestedClass");
		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(clazzFromUpkg));
		assertEquals("Nested class must correspond with exactly one object list", correspondingEObjects.size(), 1);
		assertEquals("Corresponding object list must have size one", head(correspondingEObjects).size(), 1);
		assertTrue("Corresponding object must be a component", head(head(correspondingEObjects)) instanceof Component);
		Component component = (Component) head(head(correspondingEObjects));
		assertEquals("The nested component and the class must have the same name", component.getName(), clazzFromUpkg.getName());

		// already tested in other test
//		val repository = (getCorrespondenceInstance.getCorrespondingEObjects(#[upkg])).head
//		assertEquals("The parent of the component must correspond to the package", component.eContainer, repository)
	}
	
	@Test
	public void createPackageWithTwoNestedClasses() throws Throwable {
		LOGGER.trace("Test createPackageWithTwoNestedClasses()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
		clazz.setName("TestNestedClass");
		upkg.getClasses().add(clazz);
		saveAndSynchronizeChanges(upkg);

		assertTrue("Package must have exactly one nested class", upkg.getClasses().size() == 1);
		UClass clazzFromUpkg = head(upkg.getClasses());
		assertEquals("Nested class must has the name that was set for synchronization", clazzFromUpkg.getName(), "TestNestedClass");
		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(clazzFromUpkg));
		assertTrue("Nested class must correspond with exactly one object list", correspondingEObjects.size() == 1);
		assertTrue("Corresponding object list must have size one", head(correspondingEObjects).size() == 1);
		assertTrue("Corresponding object must be a component", head(head(correspondingEObjects)) instanceof Component);
		Component component = (Component) head(head(correspondingEObjects));
		assertEquals("The nested component and the class must have the same name", component.getName(), clazzFromUpkg.getName());
		
		
		UClass clazz2 = Uml_mockupFactory.eINSTANCE.createUClass();
		clazz2.setName("TestNestedClass2");
		upkg.getClasses().add(clazz2);
		saveAndSynchronizeChanges(upkg);

		assertTrue("Package must have exactly two nested classes", upkg.getClasses().size() == 2);
		UClass clazz2FromUpkg = upkg.getClasses().get(1);
		assertEquals("Nested class 2 must has the name that was set for synchronization", clazz2FromUpkg.getName(), "TestNestedClass2");
		Set<List<EObject>> correspondingEObjects2 = getCorrespondenceInstance().getCorrespondingEObjects(asList(clazz2FromUpkg));
		assertTrue("Nested class 2 must correspond with exactly one object list", correspondingEObjects2.size() == 1);
		assertTrue("Corresponding object list must have size one", head(correspondingEObjects2).size() == 1);
		assertTrue("Corresponding object must be a component", head(head(correspondingEObjects2)) instanceof Component);
		Component component2 = (Component) head(head(correspondingEObjects2));
		assertEquals("The nested component and the class must have the same name", component2.getName(), clazz2FromUpkg.getName());

		// already tested in other test
//		val repository = (getCorrespondenceInstance.getCorrespondingEObjects(#[upkg])).head
//		assertEquals("The parent of the component must correspond to the package", component.eContainer, repository)
	}

}
