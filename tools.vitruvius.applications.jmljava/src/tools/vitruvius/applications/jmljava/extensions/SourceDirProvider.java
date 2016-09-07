package tools.vitruvius.applications.jmljava.extensions;

import org.eclipse.core.resources.IResource;

/**
 * Provider for directories of Eclipse projects. This does not have to be a
 * source directory, but only a folder, which can contain model files. Usually
 * one source dir provider returns all directories, which contain files of a
 * single type.
 */
public interface SourceDirProvider {

	/**
	 * @return The set of supported file extensions (e.g. "java").
	 */
	String[] getContainedExtensions();

	/**
	 * @return The set of directories, which contain files of the given type.
	 */
	IResource[] getSourceDirs();

}
