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
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.tests.generated.testinfrastructure.AllElementTypesChange2CommandTransformingProviding;
import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.tests.TestUserInteractor;
import mappings.generated.mappings.aet0.Aet0_Mapping;
import pcm_mockup.Component;
import pcm_mockup.Repository;
import responses.ResponseChange2CommandTransformingProviding;
import uml_mockup.UClass;
import uml_mockup.UPackage;
import uml_mockup.Uml_mockupFactory;

public class TestAllElementTypes extends AbstractResponseTests {
	private final static String MODEL_PATH_PREFIX = "model/";

	private final static Logger LOGGER = Logger.getLogger(TestAllElementTypes.class);

	public TestAllElementTypes() {
		super(AllElementTypesChange2CommandTransformingProviding::new);
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
	public void testRootXbasePropagation() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		Root root1 = createAndSyncRoot1("createdRoot1a.aet1");

		Set<Correspondence> defaultCorrespondences = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);

		assertEquals("Exactly one instance of the Aet0-Mapping should be created",
				defaultCorrespondences.size(), 1);

		EList<EObject> elementsAet1 = head(defaultCorrespondences).getElementsForMetamodel(AET1_NSURI);
		EList<EObject> elementsAet2 = head(defaultCorrespondences).getElementsForMetamodel(AET2_NSURI);

		assertEquals("There should be exactly one element for AET 1", elementsAet1.size(), 1);
		assertEquals("There should be exactly one element for AET 2", elementsAet2.size(), 1);
	}
	
	@Test
	public void testRootXbasePropagationSingleToSum() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		Root root1 = createAndSyncRoot1("createdRoot1a.aet1");
		
		root1.setSingleValuedEAttribute(100);
		
		saveAndSynchronizeChanges(root1);

		Set<Correspondence> defaultCorrespondences = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);

		assertEquals("Exactly one instance of the Aet0-Mapping should be created",
				defaultCorrespondences.size(), 1);

		EList<EObject> elementsAet1 = head(defaultCorrespondences).getElementsForMetamodel(AET1_NSURI);
		EList<EObject> elementsAet2 = head(defaultCorrespondences).getElementsForMetamodel(AET2_NSURI);

		assertEquals("There should be exactly one element for AET 1", elementsAet1.size(), 1);
		assertEquals("There should be exactly one element for AET 2", elementsAet2.size(), 1);
	}
	
	@Test
	public void testRootXbasePropagationSumToSingle() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		Root2 root2 = createAndSyncRoot2("createdRoot2a.aet2");
		
		List<Integer> list = root2.getMultiValuedEAttribute2();
		list.clear();
		list.add(10);
		list.add(20);
		list.add(30);
		
		saveAndSynchronizeChanges(root2);

		Set<Correspondence> defaultCorrespondences = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);

		assertEquals("Exactly one instance of the Aet0-Mapping should be created",
				defaultCorrespondences.size(), 1);

		EList<EObject> elementsAet1 = head(defaultCorrespondences).getElementsForMetamodel(AET1_NSURI);
		EList<EObject> elementsAet2 = head(defaultCorrespondences).getElementsForMetamodel(AET2_NSURI);

		assertEquals("There should be exactly one element for AET 1", elementsAet1.size(), 1);
		assertEquals("There should be exactly one element for AET 2", elementsAet2.size(), 1);
	}
	
	@Test
	public void testRootXbasePropagationSumToSingleAfterRemove() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		Root2 root2 = createAndSyncRoot2("createdRoot2a.aet2");
		
		List<Integer> list = root2.getMultiValuedEAttribute2();
		list.clear();
		list.add(10);
		list.add(20);
		list.add(30);
		saveAndSynchronizeChanges(root2);
		
		list.remove(2);
		saveAndSynchronizeChanges(root2);

		Set<Correspondence> defaultCorrespondences = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);

		assertEquals("Exactly one instance of the Aet0-Mapping should be created",
				defaultCorrespondences.size(), 1);

		EList<EObject> elementsAet1 = head(defaultCorrespondences).getElementsForMetamodel(AET1_NSURI);
		EList<EObject> elementsAet2 = head(defaultCorrespondences).getElementsForMetamodel(AET2_NSURI);

		assertEquals("There should be exactly one element for AET 1", elementsAet1.size(), 1);
		assertEquals("There should be exactly one element for AET 2", elementsAet2.size(), 1);
	}
	
	private void testAllAet0sForSumEquality() throws Throwable {
		Set<Correspondence> aet0s = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);
	
		for (Correspondence corr : aet0s) {
			EList<EObject> elementsAet1 = corr.getElementsForMetamodel(AET1_NSURI);
			EList<EObject> elementsAet2 = corr.getElementsForMetamodel(AET2_NSURI);
		
			assertEquals("There should be exactly one element for AET 1 for " + corr.toString(), elementsAet1.size(), 1);
			assertEquals("There should be exactly one element for AET 2 for " + corr.toString(), elementsAet2.size(), 1);
			
			Integer rootSingleValue = ((Root) elementsAet1.get(0)).getSingleValuedEAttribute();
			List<Integer> root2MultiValued = ((Root2) elementsAet2.get(0)).getMultiValuedEAttribute2();
			Integer root2Sum = 0;
			for (int value : root2MultiValued) {
				root2Sum += value;
			}
			
			assertEquals("Both elements should have equal singleValue and sum", rootSingleValue, root2Sum);
		}
	}
	
	private void testAet0sNumber(int number) throws Throwable {
		Set<Correspondence> aet0s = getMappedCorrespondenceInstance()
				.getCorrespondencesForMapping(Aet0_Mapping.INSTANCE);
	
		assertEquals("Exactly " + number + " instance(s) of the Aet0-Mapping should exist",
				aet0s.size(), number);
	}
	
	@Test
	public void testSumToSingleAfterRemoveTwoMappingInstances() throws Throwable {
		LOGGER.trace("Test testDefaultMappingCreatedOnce()");

		Root2 root2a = createAndSyncRoot2("createdRoot2a.aet2");
		testAet0sNumber(1);
		testAllAet0sForSumEquality();
		
		testUserInteractor.addNextSelections(getLocalModelURI("synchronizedRoot1b.aet1"));
		Root2 root2b = createAndSyncRoot2("createdRoot2b.aet2");
		assertTrue(testUserInteractor.isResourceQueueEmpty());
		
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
		
		List<Integer> list2a = root2a.getMultiValuedEAttribute2();
		List<Integer> list2b = root2b.getMultiValuedEAttribute2();
		
		list2a.clear();
		list2a.add(10);
		list2a.add(20);
		saveAndSynchronizeChanges(root2a);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
		
		list2b.clear();
		list2b.add(100);
		saveAndSynchronizeChanges(root2b);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();

		list2a.add(30);
		saveAndSynchronizeChanges(root2a);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();

		list2b.add(200);
		list2b.add(300);
		saveAndSynchronizeChanges(root2b);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
		
		list2a.remove(2);
		saveAndSynchronizeChanges(root2a);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();

		list2b.add(400);
		saveAndSynchronizeChanges(root2b);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
		
		list2b.remove(2);
		saveAndSynchronizeChanges(root2b);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
		
		list2a.clear();
		saveAndSynchronizeChanges(root2a);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();

		list2b.clear();
		saveAndSynchronizeChanges(root2b);
		testAet0sNumber(2);
		testAllAet0sForSumEquality();
	}
}
