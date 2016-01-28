package edu.kit.ipd.sdq.vitruvius.dsls.response.tests

import edu.kit.ipd.sdq.vitruvius.dsls.response.tests.AbstractResponseTests
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.metarepository.MetaRepositoryImpl
import java.util.Arrays
import java.util.HashSet
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping
import allElementTypes.Root
import allElementTypes.AllElementTypesPackage
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertArrayEquals
import java.util.List
import allElementTypes.Identified
import java.util.function.Supplier
import responses.ResponseChange2CommandTransformingProviding
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl

abstract class AbstractAllElementTypesResponseTests extends AbstractResponseTests {
	private static val MODEL_FILE_EXTENSION = "minimalAllElements";
	
	new(Change2CommandTransformingProviding change2CommandTransformingProviding) {
		super(new Supplier<ResponseChange2CommandTransformingProviding>() {
			override get() {
				return new ResponseChange2CommandTransformingProviding();
			}
		})
	}
	
	private def String getModelPath(String modelName) {
		return this.currentTestProjectName + "/model/" + modelName + "." + MODEL_FILE_EXTENSION
	}
	
	protected def Root getRoot(String modelName) {
		return getRoot(modelName, false);
	}
	
	protected def Root getRoot(String modelName, boolean forceReload) {
		return modelName.getModelResource(forceReload)?.allContents.next as Root;
	}
	
	protected def VURI getModelVURI(String modelName) {
		return VURI.getInstance(modelName.modelPath);	
	}
	
	protected def Resource createModelResource(String modelName) {
		return this.resourceSet.createResource(modelName.modelVURI.getEMFUri());
	}

	protected def Resource getModelResource(String modelName, boolean forceReload) {
		var resource = this.resourceSet.getResource(modelName.modelVURI.getEMFUri(), false);
		if (forceReload && resource != null) {
			resource.unload;
		}
		resource = this.resourceSet.getResource(modelName.modelVURI.getEMFUri(), true);
		return resource;
	}
	
	protected def Resource getModelResource(String modelName) {
		return getModelResource(modelName, false);
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
	
	protected def void assertModelsEqual(String modelName1, String modelName2) {
		val testResourceSet = new ResourceSetImpl();
		val root = testResourceSet.getResource(modelName1.modelVURI.getEMFUri(), true).contents.get(0) as Root;
		val root2 = testResourceSet.getResource(modelName2.modelVURI.getEMFUri(), true).contents.get(0) as Root;
		assertEquals(root.id, root2.id);
		assertEquals(root.singleValuedEAttribute, root2.singleValuedEAttribute);
		assertEquals(root.singleValuedContainmentEReference?.id, root2.singleValuedContainmentEReference?.id);
		assertEquals(root.singleValuedNonContainmentEReference?.id, root2.singleValuedNonContainmentEReference?.id);
		assertArrayEquals(root.multiValuedEAttribute.toArray, root2.multiValuedEAttribute.toArray);
		assertIdentifableListsEquals(root.multiValuedContainmentEReference, root2.multiValuedContainmentEReference);
		assertIdentifableListsEquals(root.multiValuedNonContainmentEReference, root2.multiValuedNonContainmentEReference);
	}
	
	private static def void assertIdentifableListsEquals(List<? extends Identified> list1, List<? extends Identified> list2) {
		assertEquals(list1.size, list2.size);
		for (var i = 0; i < list1.size; i++) {
			assertEquals(list1.get(i).id, list2.get(i).id);
		}
	}
}