package tools.vitruv.extensions.dslsruntime.reactions.helper

import org.eclipse.emf.ecore.EObject
import org.eclipse.core.resources.IFile
import tools.vitruv.framework.util.bridges.EMFBridge
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI

public final class PersistenceHelper {
	private new() {
	}

	static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject
		while (result.eContainer !== null) {
			result = result.eContainer
		}
		result
	}

	private static def URI getURIOfElementResourceFolder(EObject element) {
		element.eResource.URI.trimSegments(1)
	}

	private static def URI getURIOfElementProject(EObject element) {
		val IFile sourceModelFile = EMFBridge::getIFileForEMFUri(element.eResource.URI)
		val IProject projectSourceModel = sourceModelFile.project
		var String srcFolderPath = projectSourceModel.fullPath.toString
		URI::createPlatformResourceURI(srcFolderPath, true)
	}

	private static def URI appendPathToURI(URI baseURI, String relativePath) {
		val newModelFileSegments = relativePath.split("/")
		if (!newModelFileSegments.last.contains(".")) {
			throw new IllegalArgumentException("File extension must be specified")
		}
		baseURI.appendSegments(newModelFileSegments)
	}

	public static def URI getURIFromSourceResourceFolder(EObject source, String relativePath) {
		val baseURI = getURIOfElementResourceFolder(source)
		baseURI.appendPathToURI(relativePath)
	}

	public static def URI getURIFromSourceProjectFolder(EObject source, String relativePath) {
		val baseURI = getURIOfElementProject(source)
		baseURI.appendPathToURI(relativePath)
	}
}
