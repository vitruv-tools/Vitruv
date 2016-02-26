package edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated;

import static edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil.createAttributeTUIDMetamodel;
import static edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil.createEmptyMetaRepository;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure.TestPCM2UMLChildChange2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import pcm_mockup.Component;
import pcm_mockup.Repository;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class TestMockupUMLPCMChild extends AbstractResponseTests {
	private final static String MODEL_PATH_PREFIX = "model/";

	private final static Logger LOGGER = Logger.getLogger(TestMockupUMLPCMChild.class);

	public TestMockupUMLPCMChild() {
		super(TestPCM2UMLChildChange2CommandTransformingProviding::new);
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
		System.out.println("Test setupTestUserInteractor()");

		this.testUserInteractor = new TestUserInteractor();
		setUserInteractor(testUserInteractor);
	}

	private URI getLocalModelURI(String modelFileName) {
		return getModelVURI(MODEL_PATH_PREFIX + modelFileName).getEMFUri();
	}

	@Test
	public void createPackageAndSync() throws Throwable {
		System.out.println("Test createPackageAndSync()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(upkg));

		assertEquals("#[new package] must correspond with exactly one object list", 1, correspondingEObjects.size());
		assertEquals("corresponding object list must have size one", 1, head(correspondingEObjects).size());
		assertTrue("corresponding object must be a repository",
				head(head(correspondingEObjects)) instanceof Repository);
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}

	@Test
	public void createPackageAndSyncWithName() throws Throwable {
		System.out.println("Test createPackageAndSyncWithName()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(upkg));

		assertEquals("#[new package] must correspond with exactly one object", 1, correspondingEObjects.size());
		assertEquals("corresponding object list must have size one", 1, head(correspondingEObjects).size());
		assertTrue("corresponding object must be a repository",
				head(head(correspondingEObjects)) instanceof Repository);
		Repository repository = (Repository) (head(head(correspondingEObjects)));

		assertEquals("the package and the repository must have the same name", upkg.getName(), repository.getName());
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}

	private UClass addNestedClassAndSync(final UPackage upkg, final String className) {
		UClass clazz = Uml_mockupFactory.eINSTANCE.createUClass();
		clazz.setName(className);
		upkg.getClasses().add(clazz);
		saveAndSynchronizeChanges(upkg);
		return clazz;
	}

	@Test
	public void createPackageWithNestedClass() throws Throwable {
		System.out.println("Test createPackageWithNestedClass()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		UClass clazz = addNestedClassAndSync(upkg, "TestNestedClass");

		assertUPackageHasNestedClassCount(upkg, 1);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}

	private void assertUPackageHasNestedClassCount(UPackage upkg, int classCount) {
		assertEquals("Package must have exactly one nested class", classCount, upkg.getClasses().size());
	}
	
	private void assertCorrespondingRepositoryHasCorrectComponentCount(UPackage upkg) throws Throwable {
		Repository repo = (Repository) head(head(getCorrespondenceInstance().getCorrespondingEObjects(asList(upkg))));
		
		assertEquals("UPackage and Repository must have same child count", upkg.getClasses().size(), repo.getComponents().size());
	}

	private void assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(UPackage upkg, int index, String name)
			throws Throwable {
		UClass clazzFromUpkg = upkg.getClasses().get(index);
		assertEquals("Nested class must has the name that was set for synchronization", clazzFromUpkg.getName(),
				name);
		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance()
				.getCorrespondingEObjects(asList(clazzFromUpkg));
		assertEquals("Nested class must correspond with exactly one object list", 1, correspondingEObjects.size());
		assertEquals("Corresponding object list must have size one", 1, head(correspondingEObjects).size());
		assertTrue("Corresponding object must be a component", head(head(correspondingEObjects)) instanceof Component);
		Component component = (Component) head(head(correspondingEObjects));
		assertEquals("The nested component and the class must have the same name", component.getName(),
				clazzFromUpkg.getName());
	}

	@Test
	public void createPackageWithTwoNestedClasses() throws Throwable {
		System.out.println("Test createPackageWithTwoNestedClasses()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());
		
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);

		UClass clazz = addNestedClassAndSync(upkg, "TestNestedClass");
		
		assertUPackageHasNestedClassCount(upkg, 1);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
		
		UClass clazz2 = addNestedClassAndSync(upkg, "TestNestedClass2");

		assertUPackageHasNestedClassCount(upkg, 2);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 1, "TestNestedClass2");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}
	
	@Test
	public void createPackageWithTwoNestedClassesThenChangeName() throws Throwable {
		System.out.println("Test createPackageWithTwoNestedClasses()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());
		
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);

		UClass clazz = addNestedClassAndSync(upkg, "TestNestedClass");
		
		assertUPackageHasNestedClassCount(upkg, 1);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
		
		UClass clazz2 = addNestedClassAndSync(upkg, "TestNestedClass2");

		assertUPackageHasNestedClassCount(upkg, 2);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 1, "TestNestedClass2");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
		
		clazz.setName("TestNestedClass_ChangedName");
		saveAndSynchronizeChanges(clazz);
		
		assertUPackageHasNestedClassCount(upkg, 2);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass_ChangedName");
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 1, "TestNestedClass2");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}

	@Ignore
	@Test
	public void removeSecondNestedClassFromPackage() throws Throwable {
		System.out.println("Test createPackageWithTwoNestedClasses()");

		testUserInteractor.addNextSelections(getLocalModelURI("repo.mpcm"));
		UPackage upkg = createAndSyncPackage("TestPackage", "pkg.muml");
		assertTrue(testUserInteractor.isResourceQueueEmpty());
		
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);

		UClass clazz = addNestedClassAndSync(upkg, "TestNestedClass");
		
		assertUPackageHasNestedClassCount(upkg, 1);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
		
		UClass clazz2 = addNestedClassAndSync(upkg, "TestNestedClass2");

		assertUPackageHasNestedClassCount(upkg, 2);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass");
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 1, "TestNestedClass2");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
		
		upkg.getClasses().remove(0);
		saveAndSynchronizeChanges(upkg);
		
		assertUPackageHasNestedClassCount(upkg, 1);
		assertUPackageHasUClassAtIndexWithNameAndCorrespondingComponent(upkg, 0, "TestNestedClass2");
		assertCorrespondingRepositoryHasCorrectComponentCount(upkg);
	}

}
