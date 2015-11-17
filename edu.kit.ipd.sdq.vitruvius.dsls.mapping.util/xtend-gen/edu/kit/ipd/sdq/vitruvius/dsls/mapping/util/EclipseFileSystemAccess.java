package edu.kit.ipd.sdq.vitruvius.dsls.mapping.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class EclipseFileSystemAccess implements IFileSystemAccess {
  private IJavaProject javaProject;
  
  private void createRecursively(final IFolder folder) {
    try {
      final IContainer parent = folder.getParent();
      boolean _exists = parent.exists();
      boolean _not = (!_exists);
      if (_not) {
        if ((!(parent instanceof IFolder))) {
          throw new IllegalStateException(("Parent not a folder: " + parent));
        } else {
          this.createRecursively(((IFolder) parent));
        }
      }
      boolean _exists_1 = folder.exists();
      boolean _not_1 = (!_exists_1);
      if (_not_1) {
        folder.create(false, false, null);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public EclipseFileSystemAccess(final IJavaProject project) {
    this.javaProject = project;
  }
  
  @Override
  public void deleteFile(final String fileName) {
    try {
      IProject _project = this.javaProject.getProject();
      IFile _file = _project.getFile(fileName);
      _file.delete(false, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void generateFile(final String fileName, final CharSequence contents) {
    try {
      URI _createURI = URI.createURI(fileName);
      final URI uri = _createURI.trimSegments(1);
      boolean _isEmpty = uri.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        IProject _project = this.javaProject.getProject();
        String _fileString = uri.toFileString();
        final IFolder folder = _project.getFolder(_fileString);
        this.createRecursively(folder);
      }
      IProject _project_1 = this.javaProject.getProject();
      final IFile file = _project_1.getFile(fileName);
      String _string = contents.toString();
      byte[] _bytes = _string.getBytes(StandardCharsets.UTF_8);
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(_bytes);
      file.create(_byteArrayInputStream, true, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void generateFile(final String fileName, final String outputConfigurationName, final CharSequence contents) {
    this.generateFile(fileName, contents);
  }
}
