package edu.kit.ipd.sdq.vitruvius.dsls.mapping.util;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.EclipseFileSystemAccess;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.util.PrependPathFSA;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class EclipseProjectHelper {
  public final String SRC_GEN_FOLDER_NAME = "src-gen";
  
  private final String JRE_CONTAINER = ("org.eclipse.jdt.launching.JRE_CONTAINER/" + "org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8");
  
  private final String REQUIRED_PLUGINS_CONTAINER = "org.eclipse.pde.core.requiredPlugins";
  
  private final String XTEXT_NATURE = "org.eclipse.xtext.ui.shared.xtextNature";
  
  private String projectName;
  
  private IFileSystemAccess rootFSA;
  
  private PrependPathFSA srcgenFSA;
  
  private IProject projectCache;
  
  private IJavaProject javaProjectCache;
  
  public EclipseProjectHelper(final String projectName) {
    this.projectName = projectName;
  }
  
  public IProject deleteProject() {
    try {
      IProject _xifexpression = null;
      IProject _project = this.getProject();
      boolean _exists = _project.exists();
      if (_exists) {
        IProject _xblockexpression = null;
        {
          IProject _project_1 = this.getProject();
          _project_1.delete(true, null);
          _xblockexpression = this.projectCache = null;
        }
        _xifexpression = _xblockexpression;
      }
      return _xifexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void addNature(final String nature) {
    try {
      IProject _project = this.getProject();
      final IProjectDescription description = _project.getDescription();
      String[] _natureIds = description.getNatureIds();
      boolean _contains = ((List<String>)Conversions.doWrapArray(_natureIds)).contains(nature);
      boolean _not = (!_contains);
      if (_not) {
        String[] _natureIds_1 = description.getNatureIds();
        Iterable<String> _plus = Iterables.<String>concat(((Iterable<? extends String>)Conversions.doWrapArray(_natureIds_1)), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(nature)));
        description.setNatureIds(((String[])Conversions.unwrapArray(_plus, String.class)));
      }
      IProject _project_1 = this.getProject();
      _project_1.setDescription(description, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void addBuilder(final String builderName) {
    try {
      IProject _project = this.getProject();
      final IProjectDescription description = _project.getDescription();
      ICommand[] _buildSpec = description.getBuildSpec();
      Stream<ICommand> _stream = ((List<ICommand>)Conversions.doWrapArray(_buildSpec)).stream();
      final Predicate<ICommand> _function = (ICommand it) -> {
        String _builderName = it.getBuilderName();
        return _builderName.equals(builderName);
      };
      boolean _anyMatch = _stream.anyMatch(_function);
      boolean _not = (!_anyMatch);
      if (_not) {
        final ICommand builderCommand = description.newCommand();
        builderCommand.setBuilderName(builderName);
        ICommand[] _buildSpec_1 = description.getBuildSpec();
        Iterable<ICommand> _plus = Iterables.<ICommand>concat(((Iterable<? extends ICommand>)Conversions.doWrapArray(_buildSpec_1)), Collections.<ICommand>unmodifiableList(CollectionLiterals.<ICommand>newArrayList(builderCommand)));
        description.setBuildSpec(((ICommand[])Conversions.unwrapArray(_plus, ICommand.class)));
      }
      IProject _project_1 = this.getProject();
      _project_1.setDescription(description, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void addXtextNatureAndBuilder() {
    this.addNature(this.XTEXT_NATURE);
    this.addBuilder("org.eclipse.xtext.ui.shared.xtextBuilder");
  }
  
  public void addPluginNatureAndBuilder() {
    this.addNature(IBundleProjectDescription.PLUGIN_NATURE);
    this.addBuilder("org.eclipse.pde.ManifestBuilder");
    this.addBuilder("org.eclipse.pde.SchemaBuilder");
  }
  
  public void addJavaCoreNatureAndBuilder() {
    this.addNature(JavaCore.NATURE_ID);
    this.addBuilder(JavaCore.BUILDER_ID);
  }
  
  public void setBinFolder(final String folderName) {
    try {
      IProject _project = this.getProject();
      final IFolder binFolder = _project.getFolder(folderName);
      boolean _exists = binFolder.exists();
      boolean _not = (!_exists);
      if (_not) {
        binFolder.create(false, true, null);
      }
      IJavaProject _javaProject = this.getJavaProject();
      IPath _fullPath = binFolder.getFullPath();
      _javaProject.setOutputLocation(_fullPath, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void addSourceFolder(final String folderName) {
    try {
      IProject _project = this.getProject();
      final IFolder sourceFolder = _project.getFolder(folderName);
      sourceFolder.create(false, true, null);
      IJavaProject _javaProject = this.getJavaProject();
      final IPackageFragmentRoot sourceRoot = _javaProject.getPackageFragmentRoot(sourceFolder);
      IJavaProject _javaProject_1 = this.getJavaProject();
      IClasspathEntry[] _rawClasspath = _javaProject_1.getRawClasspath();
      Stream<IClasspathEntry> _stream = ((List<IClasspathEntry>)Conversions.doWrapArray(_rawClasspath)).stream();
      final Predicate<IClasspathEntry> _function = (IClasspathEntry it) -> {
        IPath _path = it.getPath();
        IPath _path_1 = sourceRoot.getPath();
        return _path.equals(_path_1);
      };
      boolean _anyMatch = _stream.anyMatch(_function);
      boolean _not = (!_anyMatch);
      if (_not) {
        IJavaProject _javaProject_2 = this.getJavaProject();
        IJavaProject _javaProject_3 = this.getJavaProject();
        IClasspathEntry[] _rawClasspath_1 = _javaProject_3.getRawClasspath();
        IPath _path = sourceRoot.getPath();
        IClasspathEntry _newSourceEntry = JavaCore.newSourceEntry(_path);
        Iterable<IClasspathEntry> _plus = Iterables.<IClasspathEntry>concat(((Iterable<? extends IClasspathEntry>)Conversions.doWrapArray(_rawClasspath_1)), Collections.<IClasspathEntry>unmodifiableList(CollectionLiterals.<IClasspathEntry>newArrayList(_newSourceEntry)));
        _javaProject_2.setRawClasspath(((IClasspathEntry[])Conversions.unwrapArray(_plus, IClasspathEntry.class)), null);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void addContainerEntry(final String path) {
    try {
      final Path newPath = new Path(path);
      IJavaProject _javaProject = this.getJavaProject();
      IClasspathEntry[] _rawClasspath = _javaProject.getRawClasspath();
      Stream<IClasspathEntry> _stream = ((List<IClasspathEntry>)Conversions.doWrapArray(_rawClasspath)).stream();
      final Predicate<IClasspathEntry> _function = (IClasspathEntry it) -> {
        IPath _path = it.getPath();
        return _path.equals(newPath);
      };
      boolean _anyMatch = _stream.anyMatch(_function);
      boolean _not = (!_anyMatch);
      if (_not) {
        IJavaProject _javaProject_1 = this.getJavaProject();
        IJavaProject _javaProject_2 = this.getJavaProject();
        IClasspathEntry[] _rawClasspath_1 = _javaProject_2.getRawClasspath();
        IClasspathEntry _newContainerEntry = JavaCore.newContainerEntry(newPath);
        Iterable<IClasspathEntry> _plus = Iterables.<IClasspathEntry>concat(((Iterable<? extends IClasspathEntry>)Conversions.doWrapArray(_rawClasspath_1)), Collections.<IClasspathEntry>unmodifiableList(CollectionLiterals.<IClasspathEntry>newArrayList(_newContainerEntry)));
        _javaProject_1.setRawClasspath(((IClasspathEntry[])Conversions.unwrapArray(_plus, IClasspathEntry.class)), null);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void emptyClasspath() {
    try {
      IJavaProject _javaProject = this.getJavaProject();
      _javaProject.setRawClasspath(new IClasspathEntry[] {}, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * @see https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
   * @see http://architecturware.cvs.sourceforge.net/viewvc/architecturware/oaw_v4/core.plugin/plugin.oaw4/main/src/org/openarchitectureware/wizards/EclipseHelper.java?revision=1.13&view=markup
   */
  public IJavaProject createXtextPluginProject() {
    try {
      this.addXtextNatureAndBuilder();
      this.addJavaCoreNatureAndBuilder();
      this.addPluginNatureAndBuilder();
      this.setBinFolder("bin");
      this.emptyClasspath();
      this.addSourceFolder("src");
      this.addSourceFolder(this.SRC_GEN_FOLDER_NAME);
      this.addContainerEntry(this.REQUIRED_PLUGINS_CONTAINER);
      this.addContainerEntry(this.JRE_CONTAINER);
      IJavaProject _javaProject = this.getJavaProject();
      _javaProject.open(null);
      IJavaProject _javaProject_1 = this.getJavaProject();
      EclipseFileSystemAccess _eclipseFileSystemAccess = new EclipseFileSystemAccess(_javaProject_1);
      this.rootFSA = _eclipseFileSystemAccess;
      PrependPathFSA _prependPathFSA = new PrependPathFSA(this.rootFSA, this.SRC_GEN_FOLDER_NAME);
      this.srcgenFSA = _prependPathFSA;
      return this.getJavaProject();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public IJavaProject createJavaProject() {
    try {
      this.addJavaCoreNatureAndBuilder();
      this.setBinFolder("bin");
      this.emptyClasspath();
      this.addSourceFolder("src");
      this.addContainerEntry(this.JRE_CONTAINER);
      IJavaProject _javaProject = this.getJavaProject();
      _javaProject.open(null);
      IJavaProject _javaProject_1 = this.getJavaProject();
      EclipseFileSystemAccess _eclipseFileSystemAccess = new EclipseFileSystemAccess(_javaProject_1);
      this.rootFSA = _eclipseFileSystemAccess;
      return this.getJavaProject();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void synchronizeProject() {
    try {
      IProject _project = this.getProject();
      _project.refreshLocal(IResource.DEPTH_INFINITE, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void build() {
    try {
      IProject _project = this.getProject();
      _project.build(IncrementalProjectBuilder.FULL_BUILD, null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Deletes the project, and creates a new Java project
   */
  public void reinitializeXtextPluginProject() {
    this.deleteProject();
    this.createProject();
    this.createXtextPluginProject();
  }
  
  public void reinitializeJavaProject() {
    this.deleteProject();
    this.createProject();
    this.createJavaProject();
  }
  
  /**
   * Creates a new project
   */
  public void createProject() {
    try {
      IProject _project = this.getProject();
      _project.create(null);
      IProject _project_1 = this.getProject();
      _project_1.open(null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public IProject getProject() {
    IProject _elvis = null;
    if (this.projectCache != null) {
      _elvis = this.projectCache;
    } else {
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot _root = _workspace.getRoot();
      IProject _project = _root.getProject(this.projectName);
      _elvis = _project;
    }
    this.projectCache = _elvis;
    return this.projectCache;
  }
  
  public IProject getOrCreateProject() {
    IProject _elvis = null;
    if (this.projectCache != null) {
      _elvis = this.projectCache;
    } else {
      IWorkspace _workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot _root = _workspace.getRoot();
      IProject _project = _root.getProject(this.projectName);
      _elvis = _project;
    }
    this.projectCache = _elvis;
    boolean _exists = this.projectCache.exists();
    boolean _not = (!_exists);
    if (_not) {
      this.createProject();
    }
    return this.projectCache;
  }
  
  public IJavaProject getJavaProject() {
    IJavaProject _elvis = null;
    if (this.javaProjectCache != null) {
      _elvis = this.javaProjectCache;
    } else {
      IProject _project = this.getProject();
      IJavaProject _create = JavaCore.create(_project);
      _elvis = _create;
    }
    this.javaProjectCache = _elvis;
    return this.javaProjectCache;
  }
  
  /**
   * @see https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Class_Loading_in_a_running_plugin
   */
  public URLClassLoader getClassLoader() {
    try {
      URLClassLoader _xblockexpression = null;
      {
        final IJavaProject project = this.getJavaProject();
        final String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(project);
        final Function1<String, URL> _function = (String it) -> {
          try {
            Path _path = new Path(it);
            File _file = _path.toFile();
            URI _uRI = _file.toURI();
            return _uRI.toURL();
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        };
        final List<URL> urlList = ListExtensions.<String, URL>map(((List<String>)Conversions.doWrapArray(classPathEntries)), _function);
        IJavaProject _javaProject = this.getJavaProject();
        Class<? extends IJavaProject> _class = _javaProject.getClass();
        final ClassLoader parentClassLoader = _class.getClassLoader();
        _xblockexpression = new URLClassLoader(((URL[])Conversions.unwrapArray(urlList, URL.class)), parentClassLoader);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public IFileSystemAccess getRootFSA() {
    return this.rootFSA;
  }
  
  public PrependPathFSA getSrcGenFSA() {
    return this.srcgenFSA;
  }
}
