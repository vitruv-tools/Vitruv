package tools.vitruv.extensions.dslsruntime.reactions.helper

import org.eclipse.emf.ecore.EObject
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.util.TimeZone
import java.util.Locale

public final class PersistenceHelper {
	private new() {}
	
	public static def EObject getModelRoot(EObject modelObject) {
		var result = modelObject;
		while (result.eContainer() !== null) {
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
			val IFile sourceModelFile = URIUtil.getIFileForEMFUri(elementUri);
			val IProject projectSourceModel = sourceModelFile.getProject();
			var String srcFolderPath = projectSourceModel.getFullPath().toString();
			return URI.createPlatformResourceURI(srcFolderPath, true);
		} else if (elementUri.isFile) {
			// FIXME HK This is a prototypical implementation: It is not easy
			// to extract the project from a file URI.
			var shortenedUri = elementUri//.trimSegments(1);
			val possibleDirectories = #["src", "src-gen", "xtend-gen", "model", "models", "code", "repository"];
			// Remove last segment as long as we are not in src directory or in the test project directory
			while (!possibleDirectories.contains(shortenedUri.lastSegment)
				&& !isUriTestProject(shortenedUri)) {
				shortenedUri = shortenedUri.trimSegments(1);
			}
			// We are not in the test root folder yet, so trim another segment as we are in one of the possible directories
			if (!isUriTestProject(shortenedUri)) {
				shortenedUri = shortenedUri.trimSegments(1);
			}
			return shortenedUri 
			
		} else {
			throw new UnsupportedOperationException("Other URI types than file and platform are currently not supported");
		}
	}
	
	private static def boolean isUriTestProject(URI uri) {
		val lastSegment = uri.lastSegment;
		if (uri.lastSegment === null) {
			throw new IllegalStateException("The URI " + uri + " is empty");
		}
		// TODO This is really hacky:
		// Test projects contain the time zone identifier in short and English, so check for it
		val timezoneID = TimeZone.^default.getDisplayName(true, TimeZone.SHORT, Locale.ENGLISH);
		return lastSegment.contains(timezoneID);
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
	
	/**
	 * Returns the URI of the project folder, relative as specified in <code>relativePath</code>
	 * to the project root, determined from the element <code>source</code>.
	 * 
	 * @param source -
	 * 		An {@link EObject} that is persisted within a resource of the project
	 * @param relativePath -
	 * 		The relative path within the project to get the {@link URI} for, using "/" as separator char
	 * 
	 * @returns the {@link URI} of the folder within the project of the given element
	 */
	public static def URI getURIFromSourceProjectFolder(EObject source, String relativePath) {
		val baseURI = getURIOfElementProject(source);
		return baseURI.appendPathToURI(relativePath);
	}

}
