package tools.vitruvius.dsls.mapping.util

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.core.resources.IFolder

class EclipseFileSystemAccess implements IFileSystemAccess {
	private IJavaProject javaProject
	
	private def void createRecursively(IFolder folder) {
	    val parent = folder.parent
	    if (!parent.exists) {
	    	if (!(parent instanceof IFolder))
	    		throw new IllegalStateException("Parent not a folder: " + parent)
	    	else
	    		createRecursively(parent as IFolder)
	    }
	    
	    if (!folder.exists()) {
	        folder.create(false, false, null);
	    }
	}

	new(IJavaProject project) {
		this.javaProject = project
	}
	
	override deleteFile(String fileName) {
		javaProject
			.project
			.getFile(fileName)
			.delete(false, null)
	}
	
	override generateFile(String fileName, CharSequence contents) {
		val uri = URI.createURI(fileName).trimSegments(1)
		
		if (!uri.empty) {
			val folder =
				javaProject
					.project
					.getFolder(uri.toFileString)
				
			createRecursively(folder)
		}

		val file =
			javaProject
				.project
				.getFile(fileName)
				
		file.create(new ByteArrayInputStream(
				contents
					.toString
					.getBytes(StandardCharsets.UTF_8)
				), true, null)
	}
	
	override generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		generateFile(fileName, contents)
	}
	
}