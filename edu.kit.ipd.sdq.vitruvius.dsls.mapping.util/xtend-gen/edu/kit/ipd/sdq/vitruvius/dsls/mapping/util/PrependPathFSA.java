package edu.kit.ipd.sdq.vitruvius.dsls.mapping.util;

import org.eclipse.xtext.generator.IFileSystemAccess;

/**
 * This class implements the Decorator pattern to prepend the path
 * of every access to an {@link IFileSystemAccess} instance with a
 * path fragment
 */
@SuppressWarnings("all")
public class PrependPathFSA implements IFileSystemAccess {
  private IFileSystemAccess fsa;
  
  private String prependPath;
  
  public PrependPathFSA(final IFileSystemAccess wrappedFSA, final String prependPath) {
    this.prependPath = prependPath;
    this.fsa = wrappedFSA;
  }
  
  @Override
  public void deleteFile(final String fileName) {
    String _prependedPath = this.getPrependedPath(fileName);
    this.fsa.deleteFile(_prependedPath);
  }
  
  @Override
  public void generateFile(final String fileName, final CharSequence contents) {
    String _prependedPath = this.getPrependedPath(fileName);
    this.fsa.generateFile(_prependedPath, contents);
  }
  
  @Override
  public void generateFile(final String fileName, final String outputConfigurationName, final CharSequence contents) {
    String _prependedPath = this.getPrependedPath(fileName);
    this.fsa.generateFile(_prependedPath, outputConfigurationName, contents);
  }
  
  public String getPrependedPath(final String fileName) {
    return ((this.prependPath + "/") + fileName);
  }
}
