package tools.vitruv.framework.vsum.accesscontrolsystem.accesscontrol.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

final class UMLHelper {
	
	private UMLHelper() {}
	
	static void createCorrespondentUMLResource(Resource resource, ResourceSet filtered,
			Map<EObject, EObject> correspondentObjects, Map<Resource, Resource> correspondentResources) {
		try {
			Resource copy = copyUmlModel(resource, filtered);
			correspondentResources.put(copy, resource);
			// copy and resource are 1:1 copies so we use the TreeIterator to add
			// correspondences between them
			Iterator<EObject> copyIterator = copy.getAllContents();
			Iterator<EObject> originalIterator = resource.getAllContents();
			while (copyIterator.hasNext()) {
				correspondentObjects.put(copyIterator.next(), originalIterator.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static boolean isWritableUmlResource(Resource resource) {
		return resource.getURI().fileExtension().equals("uml")
				|| resource.getURI().fileExtension().equals("uml_mockup");
	}

	static Resource copyUmlModel(Resource originalResource, ResourceSet newResourceSet) throws IOException {
		var originalURI = originalResource.getURI();
		var tempFilePath = Files.createTempFile(null, "." + originalURI.fileExtension());
		var tempURI = URI.createFileURI(tempFilePath.toString());
		originalResource.setURI(tempURI);
		originalResource.save(null);
		originalResource.setURI(originalURI);
		var viewResource = newResourceSet.getResource(tempURI, true);
		viewResource.setURI(originalURI);
		Files.delete(tempFilePath);
		EcoreUtil.resolveAll(viewResource);
		return viewResource;
	}
}
