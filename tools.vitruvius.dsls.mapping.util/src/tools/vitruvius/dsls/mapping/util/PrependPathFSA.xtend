package tools.vitruvius.dsls.mapping.util

import org.eclipse.xtext.generator.IFileSystemAccess

/**
 * This class implements the Decorator pattern to prepend the path
 * of every access to an {@link IFileSystemAccess} instance with a
 * path fragment 
 */
class PrependPathFSA implements IFileSystemAccess {
	private IFileSystemAccess fsa
	private String prependPath
	
	new(IFileSystemAccess wrappedFSA, String prependPath) {
		this.prependPath = prependPath
		this.fsa = wrappedFSA
	}
	
	override deleteFile(String fileName) {
		fsa.deleteFile(fileName.prependedPath)
	}
	
	override generateFile(String fileName, CharSequence contents) {
		fsa.generateFile(fileName.prependedPath, contents)
	}
	
	override generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		fsa.generateFile(fileName.prependedPath, outputConfigurationName, contents)
	}
	
	public def getPrependedPath(String fileName) {
		return prependPath + "/" + fileName
	}
	
}