package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import org.eclipse.xtext.generator.IFileSystemAccess

/**
 * This class implements the Decorater pattern to prepend the path
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
		fsa.deleteFile(prependPath + "/" + fileName)
	}
	
	override generateFile(String fileName, CharSequence contents) {
		fsa.generateFile(prependPath + "/" + fileName, contents)
	}
	
	override generateFile(String fileName, String outputConfigurationName, CharSequence contents) {
		fsa.generateFile(prependPath + "/" + fileName, outputConfigurationName, contents)
	}
	
}