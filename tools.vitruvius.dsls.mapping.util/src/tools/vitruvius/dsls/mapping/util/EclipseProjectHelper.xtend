package tools.vitruvius.dsls.mapping.util

import java.net.URLClassLoader
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.launching.JavaRuntime
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.xtext.generator.IFileSystemAccess

class EclipseProjectHelper {
	public final String SRC_GEN_FOLDER_NAME = "src-gen"
	
	private final String JRE_CONTAINER = "org.eclipse.jdt.launching.JRE_CONTAINER/"
		+ "org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"

	private final String REQUIRED_PLUGINS_CONTAINER = "org.eclipse.pde.core.requiredPlugins"
	private final String XTEXT_NATURE = "org.eclipse.xtext.ui.shared.xtextNature"
	
	private String projectName
	private IFileSystemAccess rootFSA
	private PrependPathFSA srcgenFSA
	
	private IProject projectCache
	private IJavaProject javaProjectCache
	
	
	new(String projectName) {
		this.projectName = projectName
	}
	
	public def deleteProject() {
		if (project.exists) {
			project.delete(true, null)
			projectCache = null
		}
	}
	
	public def addNature(String nature) {
		val description = project.description
		if (!description.natureIds.contains(nature)) {
			description.natureIds = description.natureIds + #[nature]
		}
		project.setDescription(description, null)
	}
	
	public def addBuilder(String builderName) {
		val description = project.description
		if (!description.buildSpec.stream.anyMatch[it.builderName.equals(builderName)]) {
			val builderCommand = description.newCommand
			builderCommand.builderName = builderName
			description.buildSpec = description.buildSpec + #[builderCommand]
		}
		project.setDescription(description, null)
	}
	
	public def addXtextNatureAndBuilder() {
		addNature(XTEXT_NATURE)
		addBuilder("org.eclipse.xtext.ui.shared.xtextBuilder")
	}
	
	public def addPluginNatureAndBuilder() {
		addNature(IBundleProjectDescription.PLUGIN_NATURE)
		addBuilder("org.eclipse.pde.ManifestBuilder")
		addBuilder("org.eclipse.pde.SchemaBuilder")
	}
	
	public def addJavaCoreNatureAndBuilder() {
		addNature(JavaCore.NATURE_ID)
		addBuilder(JavaCore.BUILDER_ID)
	}
	
	public def setBinFolder(String folderName) {
		// set bin folder
		val binFolder = project.getFolder(folderName);
		if (!binFolder.exists)
			binFolder.create(false, true, null);
		javaProject.setOutputLocation(binFolder.getFullPath(), null);
	}
	
	public def addSourceFolder(String folderName) {
		val sourceFolder = project.getFolder(folderName);
		sourceFolder.create(false, true, null);
		val sourceRoot = javaProject.getPackageFragmentRoot(sourceFolder);
		
		if (!javaProject.rawClasspath.stream.anyMatch[it.path.equals(sourceRoot.path)]) {
			javaProject.setRawClasspath(
				javaProject.rawClasspath + #[JavaCore.newSourceEntry(sourceRoot.path)], null
			)
		}
	}
	
	public def addContainerEntry(String path) {
		val newPath = new Path(path)
		if (!javaProject.rawClasspath.stream.anyMatch[it.path.equals(newPath)]) {
			javaProject.setRawClasspath(
				javaProject.rawClasspath + #[JavaCore.newContainerEntry(newPath)], null
			)
		}
	}
	
	public def emptyClasspath() {
		javaProject.setRawClasspath(#[], null)
	}
	
	/**
	 * @see https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
	 * @see http://architecturware.cvs.sourceforge.net/viewvc/architecturware/oaw_v4/core.plugin/plugin.oaw4/main/src/org/openarchitectureware/wizards/EclipseHelper.java?revision=1.13&view=markup
	 */
	public def createXtextPluginProject() {
		// set builders
		addXtextNatureAndBuilder
		addJavaCoreNatureAndBuilder
		addPluginNatureAndBuilder
		
		setBinFolder("bin")
		
		emptyClasspath()
		
		addSourceFolder("src")
		addSourceFolder(SRC_GEN_FOLDER_NAME)
		
		addContainerEntry(REQUIRED_PLUGINS_CONTAINER)
		addContainerEntry(JRE_CONTAINER)
		
		// TODO: create Monitor
		javaProject.open(null)
		
		this.rootFSA = new EclipseFileSystemAccess(javaProject)
		this.srcgenFSA = new PrependPathFSA(this.rootFSA, SRC_GEN_FOLDER_NAME)
		
		return javaProject
	}
	
	public def move(String newName) {
		val newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(newName)
		if (newProject.exists)
			throw new IllegalArgumentException("The target project already exists.")
		else {
			project.open(null)
			project.move(new Path("/" + newName), true, null)
		}
	}
	
	public def createJavaProject() {
		addJavaCoreNatureAndBuilder
		setBinFolder("bin")
		emptyClasspath()
		addSourceFolder("src")
		addContainerEntry(JRE_CONTAINER)
		
		javaProject.open(null)
		
		this.rootFSA = new EclipseFileSystemAccess(javaProject)
		return javaProject
	}
	
	public def synchronizeProject() {
		project.refreshLocal(IResource.DEPTH_INFINITE, null)
	}
	
	public def build() {
		project.build(IncrementalProjectBuilder.FULL_BUILD, null)
	}
	
	/** Deletes the project, and creates a new Java project */
	public def void reinitializeXtextPluginProject() {
		deleteProject
		createProject
		createXtextPluginProject
	}
	
	public def void reinitializeJavaProject() {
		deleteProject
		createProject
		createJavaProject
	}
	
	/** Creates a new project */
	def void createProject() {
		project.create(null)
		project.open(null)
	}
	
	public def getProject() {
		this.projectCache = this.projectCache ?: ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
		return this.projectCache
	}
	
	public def getOrCreateProject() {
		this.projectCache = this.projectCache ?: ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (!projectCache.exists) {
			createProject
		}
		
		return this.projectCache
	}
	
	public def getJavaProject() {
		this.javaProjectCache = this.javaProjectCache ?: JavaCore.create(getProject())
		return this.javaProjectCache
	}
	
	/**
	 * @see https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Class_Loading_in_a_running_plugin
	 */
	public def getClassLoader() {
		val project = javaProject
		val classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(project);
		val urlList = classPathEntries.map[new Path(it).toFile.toURI.toURL]
		val parentClassLoader = javaProject.class.classLoader
		
		new URLClassLoader(urlList, parentClassLoader)
	}
	
	public def IFileSystemAccess getRootFSA() {
		return rootFSA
	}
	
	public def PrependPathFSA getSrcGenFSA() {
		return srcgenFSA
	}
}