package tools.vitruvius.extensions.dslsruntime.response.helper

import org.eclipse.emf.ecore.EObject
import org.eclipse.core.resources.IFile
import tools.vitruvius.framework.util.bridges.EMFBridge
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import tools.vitruvius.framework.correspondence.CorrespondenceModel

public final class PersistenceHelper {
	private new() {}
	
	public static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject;
		while (result.eContainer() != null) {
			result = result.eContainer();
		}
		return result;
	}

	private static def URI getURIOfElementResourceFolder(EObject element) {
		return element.eResource().getURI().trimSegments(1);
	}

	private static def URI getURIOfElementProject(EObject element) {
		val IFile sourceModelFile = EMFBridge.getIFileForEMFUri(element.eResource().URI);
		val IProject projectSourceModel = sourceModelFile.getProject();
		var String srcFolderPath = projectSourceModel.getFullPath().toString();
		return URI.createPlatformResourceURI(srcFolderPath, true);
	}

	private static def URI appendPathToURI(URI baseURI, String relativePath, CorrespondenceModel correspondenceModel) {
		val newModelFileSegments = relativePath.split("/");
		if (!newModelFileSegments.last.contains(".")) {
			// No file extension was specified, add the first one that is valid for the metamodel
			val fileExtension = correspondenceModel.getMapping().getMetamodelB().
				getFileExtensions().get(0);
			newModelFileSegments.set(newModelFileSegments.size - 1, newModelFileSegments.last + "." + fileExtension);
		}
		return baseURI.appendSegments(newModelFileSegments);
	}

	public static def URI getURIFromSourceResourceFolder(EObject source, String relativePath, CorrespondenceModel correspondenceModel) {
		val baseURI = getURIOfElementResourceFolder(source);
		return baseURI.appendPathToURI(relativePath, correspondenceModel);
	}

	public static def URI getURIFromSourceProjectFolder(EObject source, String relativePath, CorrespondenceModel correspondenceModel) {
		val baseURI = getURIOfElementProject(source);
		return baseURI.appendPathToURI(relativePath, correspondenceModel);
	}

}
