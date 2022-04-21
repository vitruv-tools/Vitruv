package tools.vitruv.framework.views.util;

import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI;
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import uml_mockup.Identified;
import uml_mockup.UClass;
import uml_mockup.UPackage;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class XmiIdEdgeCaseTest {
	private ResourceSet resourceSet;
	private Path testProjectFolder;
	private XMLResource umlModel;
	private Map<String, EObject> expectedIdMapping;

	@BeforeEach
	void setup(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder;
		resourceSet = withGlobalFactories(new ResourceSetImpl());

		this.umlModel = (XMLResource) resourceSet.createResource(getModelURI("my.uml_mockup"));
		UPackage uPackage1 = uml.Package();
		umlModel.getContents().add(uPackage1);
		uPackage1.setName("Package1");
		UClass uClass1 = uml.Class();
		uPackage1.getClasses().add(uClass1);

		UPackage uPackage2 = uml.Package();
		umlModel.getContents().add(uPackage2);
		uPackage2.setName("Package2");
		UClass uClass2 = uml.Class();
		uPackage2.getClasses().add(uClass2);

		expectedIdMapping = Map.of( //
				"package-1", uPackage1, //
				"package-2", uPackage2, //
				"class-1", uClass1, //
				"class-2", uClass2 //
		);
		expectedIdMapping.forEach((id, obj) -> umlModel.setID(obj, id));
	}

	@Test
	public void testSingleResourceCopy() {
		ResourceSet copyResourceSet = new ResourceSetImpl();
		XMLResource copiedModel = (XMLResource) ResourceCopier.copyViewResource(umlModel, copyResourceSet);
		validateIds(copiedModel, expectedIdMapping);
	}

	@Test
	public void testMultiResourceCopy() {
		XMLResource umlModel2 = (XMLResource) resourceSet.createResource(getModelURI("my2.uml_mockup"));
		UPackage uPackage1 = uml.Package();
		umlModel2.getContents().add(uPackage1);
		uPackage1.setName("Package1");
		UClass uClass1 = uml.Class();
		uPackage1.getClasses().add(uClass1);

		UPackage uPackage2 = uml.Package();
		umlModel2.getContents().add(uPackage2);
		uPackage2.setName("Package2");
		UClass uClass2 = uml.Class();
		uPackage2.getClasses().add(uClass2);

		Map<String, EObject> expectedIdMapping2 = Map.of( //
				"2-package-1", uPackage1, //
				"2-package-2", uPackage2, //
				"2-class-1", uClass1, //
				"2-class-2", uClass2 //
		);
		expectedIdMapping2.forEach((id, obj) -> umlModel2.setID(obj, id));

		ResourceSet copyResourceSet = new ResourceSetImpl();
		Map<Resource, Resource> copiedModels = ResourceCopier.copyViewResources(List.of(umlModel, umlModel2),
				copyResourceSet);
		XMLResource copiedModel = (XMLResource) copiedModels.get(umlModel);
		XMLResource copiedModel2 = (XMLResource) copiedModels.get(umlModel2);
		assertNotNull(copiedModel, "copy for uml model is missing");
		assertNotNull(copiedModel2, "copy for uml model 2 is missing");
		validateIds(copiedModel, expectedIdMapping);
		validateIds(copiedModel2, expectedIdMapping2);
	}

	private void validateIds(Resource copiedResource, Map<String, EObject> expectedIdMapping) {
		expectedIdMapping.forEach((id, object) -> {
			EObject copiedObject = copiedResource.getEObject(id);
			assertNotNull(copiedObject, "could not find element with id " + id);
			assertEquals(((Identified) object).getId(), ((Identified) copiedObject).getId());
		});
	}

	protected URI getModelURI(String modelFileName) {
		File file = testProjectFolder.resolve("model").resolve(modelFileName).toFile();
		return createFileURI(file);
	}
}
