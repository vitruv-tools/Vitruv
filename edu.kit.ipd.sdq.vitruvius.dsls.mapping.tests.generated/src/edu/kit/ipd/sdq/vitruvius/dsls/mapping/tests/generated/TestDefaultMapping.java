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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;

import allElementTypes.AllElementTypesFactory;
import allElementTypes.Root;
import allElementTypes2.AllElementTypes2Factory;
import allElementTypes2.Root2;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappedCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import mappings.generated.mappings.defaultmapping.DefaultMapping_Mapping;
import pcm_mockup.Component;
import pcm_mockup.Repository;
import responses.ResponseChange2CommandTransformingProviding;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class TestDefaultMapping extends AbstractResponseTests {
	private final static String MODEL_PATH_PREFIX = "model/";

	private final static Logger LOGGER = Logger.getLogger(TestDefaultMapping.class);

	public TestDefaultMapping() {
		super(ResponseChange2CommandTransformingProviding::new);
	}

	@Override
	protected void initializeTestModel() {
		// do nothing
	}

	private final static String AET1_NSURI = "http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes";
	private final static String AET2_NSURI = "http://edu.kit.ipd.sdq.vitruvius.tests.metamodels.allElementTypes2";

	protected Collection<Pair<String, String>> getMetamodelURIsAndExtensions() {
		Set<Pair<String, String>> result = new HashSet<>();
		result.add(new Pair<>(AET1_NSURI, "aet1"));
		result.add(new Pair<>(AET2_NSURI, "aet2"));

		return result;
	}

	@Override
	protected MetaRepositoryImpl createMetaRepository() {
		return createEmptyMetaRepository(getMetamodelURIsAndExtensions().stream()
				.map(it -> createAttributeTUIDMetamodel(it.getFirst(), it.getSecond())).collect(Collectors.toList()));
	}

	private Root createAndSyncRoot1(String fileName) {
		Root root1 = AllElementTypesFactory.eINSTANCE.createRoot();
		createAndSychronizeModel(MODEL_PATH_PREFIX + fileName, root1);
		return root1;
	}
	
	private Root2 createAndSyncRoot2(String fileName) {
		Root2 root2 = AllElementTypes2Factory.eINSTANCE.createRoot2();
		createAndSychronizeModel(MODEL_PATH_PREFIX + fileName, root2);
		return root2;
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

	private MappedCorrespondenceInstance getMappedCorrespondenceInstance() throws Throwable {
		assertTrue("Correspondence instance should be of type " + MappedCorrespondenceInstance.class,
				getCorrespondenceInstance() instanceof MappedCorrespondenceInstance);

		return (MappedCorrespondenceInstance) getCorrespondenceInstance();
	}

	@Test
	public void testDefaultMappingCreatedOnce() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		testUserInteractor.addNextSelections(getLocalModelURI("aet1defaultRoot.aet1"));
		Root root1 = createAndSyncRoot1("createdRoot1a.aet1");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		testUserInteractor.addNextSelections(getLocalModelURI("aet2correspondsToDefaultRoot.aet2"));
		Root root2 = createAndSyncRoot1("createdRoot1b.aet1");
		assertTrue(testUserInteractor.isResourceQueueEmpty());

		Set<Correspondence> defaultCorrespondences = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(DefaultMapping_Mapping.INSTANCE);

		assertEquals("Exactly one instance of the defaultCorrespondence should be created",
				defaultCorrespondences.size(), 1);

		EList<EObject> elementsAet1 = head(defaultCorrespondences).getElementsForMetamodel(AET1_NSURI);
		EList<EObject> elementsAet2 = head(defaultCorrespondences).getElementsForMetamodel(AET2_NSURI);

		assertEquals("There should be exactly one element for AET 1", elementsAet1.size(), 1);
		assertEquals("There should be no element for AET 2", elementsAet2.size(), 0);

		EObject singleElementAet1 = head(elementsAet1);
		assertTrue("Element for AET 1 should be of type Root", singleElementAet1 instanceof Root);
		assertEquals("Root for AET 1 should have single attribute value 10",
				Integer.valueOf(10), (((Root) singleElementAet1).getSingleValuedEAttribute()));
	}
}
