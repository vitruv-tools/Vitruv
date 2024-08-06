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

import edu.kit.ipd.sdq.commons.util.java.Pair;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
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

	@BeforeEach
	void setup(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder;
		resourceSet = withGlobalFactories(new ResourceSetImpl());
	}

	@Test
	public void testSingleResourceCopy() {
		Pair<XMLResource, Map<String, EObject>> umlResourcePair = createPopulatedUmlResourceAndIdMapping("my");
		ResourceSet copyResourceSet = new ResourceSetImpl();
		XMLResource copiedModel = (XMLResource) ResourceCopier.copyViewResource(umlResourcePair.get0(),
				copyResourceSet);
		validateIds(copiedModel, umlResourcePair.get1());
	}

	@Test
	public void testMultiResourceCopy() {
		Pair<XMLResource, Map<String, EObject>> umlResourcePair = createPopulatedUmlResourceAndIdMapping("my1");
		Pair<XMLResource, Map<String, EObject>> umlResourcePair2 = createPopulatedUmlResourceAndIdMapping("my2");
		Pair<XMLResource, Map<String, EObject>> umlResourcePair3 = createPopulatedUmlResourceAndIdMapping("my3");

		ResourceSet copyResourceSet = new ResourceSetImpl();
		Map<Resource, Resource> copiedModels = ResourceCopier.copyViewResources(
				List.of(umlResourcePair.get0(), umlResourcePair2.get0(), umlResourcePair3.get0()), copyResourceSet);
		XMLResource copiedModel = (XMLResource) copiedModels.get(umlResourcePair.get0());
		XMLResource copiedModel2 = (XMLResource) copiedModels.get(umlResourcePair2.get0());
		XMLResource copiedModel3 = (XMLResource) copiedModels.get(umlResourcePair3.get0());
		assertNotNull(copiedModel, "copy for uml model is missing");
		assertNotNull(copiedModel2, "copy for uml model 2 is missing");
		assertNotNull(copiedModel3, "copy for uml model 3 is missing");
		validateIds(copiedModel, umlResourcePair.get1());
		validateIds(copiedModel2, umlResourcePair2.get1());
		validateIds(copiedModel3, umlResourcePair3.get1());
	}

	private Pair<XMLResource, Map<String, EObject>> createPopulatedUmlResourceAndIdMapping(String name) {
		XMLResource umlResource = (XMLResource) resourceSet.createResource(getModelURI(name + ".uml_mockup"));
		UPackage uPackage1 = uml.Package();
		umlResource.getContents().add(uPackage1);
		uPackage1.setName("Package1");
		UClass uClass1 = uml.Class();
		uPackage1.getClasses().add(uClass1);

		UPackage uPackage2 = uml.Package();
		umlResource.getContents().add(uPackage2);
		uPackage2.setName("Package2");
		UClass uClass2 = uml.Class();
		uPackage2.getClasses().add(uClass2);

		Map<String, EObject> expectedIdMapping = Map.of( //
				name + "_package-1", uPackage1, //
				name + "_package-2", uPackage2, //
				name + "_class-1", uClass1, //
				name + "_class-2", uClass2 //
		);
		expectedIdMapping.forEach((id, obj) -> umlResource.setID(obj, id));
		return new Pair<XMLResource, Map<String, EObject>>(umlResource, expectedIdMapping);
	}

	private void validateIds(Resource copiedResource, Map<String, EObject> expectedIdMapping) {
		expectedIdMapping.forEach((id, object) -> {
			EObject copiedObject = copiedResource.getEObject(id);
			assertNotNull(copiedObject, "could not find element with id " + id);
			assertEquals(((Identified) object).getId(), ((Identified) copiedObject).getId(),
					"retrieved incorrect element for id " + id + "\nexpected: " + object + ", actual: " + copiedObject);
		});
	}

	protected URI getModelURI(String modelFileName) {
		File file = testProjectFolder.resolve("model").resolve(modelFileName).toFile();
		return createFileURI(file);
	}
}
