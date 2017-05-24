package tools.vitruv.extensions.dslsruntime.reactions.helper

import org.eclipse.emf.ecore.EObject
import org.eclipse.core.resources.IFile
import tools.vitruv.framework.util.bridges.EMFBridge
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import java.io.File

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
		val elementUri = element.eResource().URI;
		if (elementUri.isPlatform) {
			val IFile sourceModelFile = EMFBridge.getIFileForEMFUri(elementUri);
			val IProject projectSourceModel = sourceModelFile.getProject();
			var String srcFolderPath = projectSourceModel.getFullPath().toString();
			return URI.createPlatformResourceURI(srcFolderPath, true);
		} else if (elementUri.isFile) {
			// FIXME HK This is a prototypical implementation: It is not easy
			// to extract the project from a file URI.
			var shortenedUri = elementUri.trimSegments(1);
			var nextLevel = true;
			var packageInfoFound = false;
			while (nextLevel) {
				shortenedUri = shortenedUri.trimSegments(1);
				val files = new File(shortenedUri.toFileString).listFiles; 
				nextLevel = false;
				for (file : files) {
					if (file.name == "package-info.java") {
						nextLevel = true;
						packageInfoFound = true;
					}
				}
			}
			if (packageInfoFound) {
				shortenedUri = shortenedUri.trimSegments(1);
			}
			return shortenedUri 
			
		} else {
			throw new UnsupportedOperationException("Other URI types than file and platform are currently not supported");
		}
	}

	private static def URI appendPathToURI(URI baseURI, String relativePath) {
		val newModelFileSegments = relativePath.split("/");
		if (!newModelFileSegments.last.contains(".")) {
			throw new IllegalArgumentException("File extension must be specified");
		}
		return baseURI.appendSegments(newModelFileSegments);
	}

	public static def URI getURIFromSourceResourceFolder(EObject source, String relativePath) {
		val baseURI = getURIOfElementResourceFolder(source);
		return baseURI.appendPathToURI(relativePath);
	}

	public static def URI getURIFromSourceProjectFolder(EObject source, String relativePath) {
		val baseURI = getURIOfElementProject(source);
		return baseURI.appendPathToURI(relativePath);
	}

}
