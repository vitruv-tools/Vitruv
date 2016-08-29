package edu.kit.ipd.sdq.vitruvius.dsls.response.tests

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import java.util.Arrays
import java.util.HashSet
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping
import allElementTypes.Root
import allElementTypes.AllElementTypesPackage
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertArrayEquals
import java.util.List
import allElementTypes.Identified
import java.util.function.Supplier
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

abstract class AbstractAllElementTypesResponseTests extends AbstractResponseTests {
	protected static val MODEL_FILE_EXTENSION = "minimalAllElements";
	
	new(Supplier<? extends Change2CommandTransformingProviding> change2CommandTransformingProvidingSupplier) {
		super(change2CommandTransformingProvidingSupplier)
	}
	
	protected override createMetaRepository() {
		val metarepository = new MetaRepositoryImpl();
		// Simple changes model
		val metamodelUri = VURI.getInstance(AllElementTypesPackage.eNS_URI);
		val metamodelNSUris = new HashSet<String>();
		metamodelNSUris.addAll(Arrays.asList(AllElementTypesPackage.eNS_URI));
		val String[] fileExtensions = newArrayOfSize(1);
		fileExtensions.set(0, MODEL_FILE_EXTENSION);
		val minimalMetamodel = new Metamodel(metamodelNSUris, metamodelUri, fileExtensions);
		metarepository.addMetamodel(minimalMetamodel);

		val minimalMinimalMapping = new Mapping(minimalMetamodel, minimalMetamodel);
		metarepository.addMapping(minimalMinimalMapping);
		return metarepository;
	}

	protected override void assertModelsEqual(String modelNameWithExtension1, String modelNameWithExtension2) {
		val testResourceSet = new ResourceSetImpl();
		val root = testResourceSet.getResource(modelNameWithExtension1.modelVURI.getEMFUri(), true).contents.get(0) as Root;
		val root2 = testResourceSet.getResource(modelNameWithExtension2.modelVURI.getEMFUri(), true).contents.get(0) as Root;
		assertEquals(root.id, root2.id);
		assertEquals(root.singleValuedEAttribute, root2.singleValuedEAttribute);
		assertEquals(root.singleValuedContainmentEReference?.id, root2.singleValuedContainmentEReference?.id);
		assertEquals(root.singleValuedNonContainmentEReference?.id, root2.singleValuedNonContainmentEReference?.id);
		assertArrayEquals(root.multiValuedEAttribute.toArray, root2.multiValuedEAttribute.toArray);
		assertIdentifableListsEquals(root.multiValuedContainmentEReference, root2.multiValuedContainmentEReference);
		assertIdentifableListsEquals(root.multiValuedNonContainmentEReference, root2.multiValuedNonContainmentEReference);
		assertTrue(EcoreUtil.equals(root, root2));
	}
	
	private static def void assertIdentifableListsEquals(List<? extends Identified> list1, List<? extends Identified> list2) {
		assertEquals(list1.size, list2.size);
		for (var i = 0; i < list1.size; i++) {
			assertEquals(list1.get(i).id, list2.get(i).id);
		}
	}
}