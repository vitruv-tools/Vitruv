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

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure.TestPCM2UMLBodyChange2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import pcm_mockup.Component;
import pcm_mockup.Pcm_mockupFactory;
import pcm_mockup.Repository;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class TestMockupUMLPCMBody extends AbstractResponseTests {
	private final static String MODEL_PATH_PREFIX = "model/";

	private final static Logger LOGGER = Logger.getLogger(TestMockupUMLPCMBody.class);

	public TestMockupUMLPCMBody() {
		super(TestPCM2UMLBodyChange2CommandTransformingProviding::new);
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

	private Repository createAndSyncRepository(String name, String fileName) {
		Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository();
		repo.setName(name);
		createAndSychronizeModel(MODEL_PATH_PREFIX + fileName, repo);

		return repo;
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
	public void checkBodyConstraint() throws Throwable {
		System.out.println("Test checkBodyConstraint()");
		
		testUserInteractor.addNextSelections(getLocalModelURI("pkg.muml"));
		Repository repo = createAndSyncRepository("TestRepository", "repo.mpcm");
		assertTrue(testUserInteractor.isResourceQueueEmpty());
		
		Set<List<EObject>> correspondingEObjects = getCorrespondenceInstance().getCorrespondingEObjects(asList(repo));

		assertEquals("#[new repository] must correspond with exactly one object", 1, correspondingEObjects.size());
		assertEquals("corresponding object list must have size one", 1, head(correspondingEObjects).size());
		assertTrue("corresponding object must be a repository",
				head(head(correspondingEObjects)) instanceof UPackage);
		UPackage upkg = (UPackage) (head(head(correspondingEObjects)));

		assertEquals(upkg.getName(), "TestPackage");
	}
}
