package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.jdt.core.IClasspathEntry
import org.eclipse.jdt.core.JavaCore
import org.eclipse.pde.core.project.IBundleProjectDescription
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.core.resources.IResource

class EclipseProjectHelper {
	public final String SRC_GEN_FOLDER_NAME = "src-gen"
	
	private final String JRE_CONTAINER = "org.eclipse.jdt.launching.JRE_CONTAINER/"
		+ "org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7"

	private final String REQUIRED_PLUGINS_CONTAINER = "org.eclipse.pde.core.requiredPlugins"
	
	private String projectName
	private IFileSystemAccess rootFSA
	private PrependPathFSA srcgenFSA
	
	
	new(String projectName) {
		this.projectName = projectName
	}
	
	public def deleteProject() {
		val project = getProject()		

		if (project.exists)
			project.delete(true, null)
	}
	
	/**
	 * @see https://sdqweb.ipd.kit.edu/wiki/JDT_Tutorial:_Creating_Eclipse_Java_Projects_Programmatically
	 * @see http://architecturware.cvs.sourceforge.net/viewvc/architecturware/oaw_v4/core.plugin/plugin.oaw4/main/src/org/openarchitectureware/wizards/EclipseHelper.java?revision=1.13&view=markup
	 * TODO: Refactor god method
	 */
	public def createJavaProject() {
		val project = getProject()

		// add Java and plug-in natures
		// Xtext nature and builder are added for parsing Xtend
		val description = project.getDescription();
		description.setNatureIds(#[
				JavaCore.NATURE_ID,
				IBundleProjectDescription.PLUGIN_NATURE,
				"org.eclipse.xtext.ui.shared.xtextNature"
				
			]);
		project.setDescription(description, null);
		
		// set builders
		val xtextBuilderCommand = description.newCommand
		xtextBuilderCommand.builderName = "org.eclipse.xtext.ui.shared.xtextBuilder"
		
		val javaBuilderCommand = description.newCommand
		javaBuilderCommand.builderName = JavaCore.BUILDER_ID
		
		val manifestBuilderCommand = description.newCommand
		manifestBuilderCommand.builderName = "org.eclipse.pde.ManifestBuilder"
		
		val schemaBuilderCommand = description.newCommand
		schemaBuilderCommand.builderName = "org.eclipse.pde.SchemaBuilder"
		
		description.buildSpec = #[
			xtextBuilderCommand,
			javaBuilderCommand,
			manifestBuilderCommand,
			schemaBuilderCommand
		]
		
		// create Java project
		val javaProject = JavaCore.create(project);
		
		// set bin folder
		val binFolder = project.getFolder("bin");
		binFolder.create(false, true, null);
		javaProject.setOutputLocation(binFolder.getFullPath(), null);
		
		// add src and src-gen folder
		val sourceFolder = project.getFolder("src");
		sourceFolder.create(false, true, null);
		val sourceRoot = javaProject.getPackageFragmentRoot(sourceFolder);
		
		val sourceGenFolder = project.getFolder(SRC_GEN_FOLDER_NAME);
		sourceGenFolder.create(false, true, null);
		val sourceGenRoot = javaProject.getPackageFragmentRoot(sourceGenFolder);
		
		val IClasspathEntry[] classpathEntries = #[
			JavaCore.newSourceEntry(sourceRoot.getPath),
			JavaCore.newSourceEntry(sourceGenRoot.getPath),
			JavaCore.newContainerEntry(new Path(REQUIRED_PLUGINS_CONTAINER)),
			JavaCore.newContainerEntry(new Path(JRE_CONTAINER))
		]
		
		javaProject.setRawClasspath(classpathEntries, null);
		
		// TODO: create Monitor
		javaProject.open(null)
		
		this.rootFSA = new EclipseFileSystemAccess(javaProject)
		this.srcgenFSA = new PrependPathFSA(this.rootFSA, SRC_GEN_FOLDER_NAME)
		
		return javaProject
	}
	
	public def synchronizeProject() {
		project.refreshLocal(IResource.DEPTH_INFINITE, null)
	}
	
	/** Deletes the project, and creates a new Java project */
	public def reinitializeProject() {
		deleteProject
		createProject
		createJavaProject
	}
	
	/** Creates a new project */
	def createProject() {
		val project = getProject()
		
		project.create(null)
		project.open(null)
	}
	
	public def getProject() {
		val root = ResourcesPlugin.getWorkspace().getRoot();
		val project = root.getProject(projectName);
		
		return project
	}
	
	public def IFileSystemAccess getRootFSA() {
		return rootFSA
	}
	
	public def PrependPathFSA getSrcGenFSA() {
		return srcgenFSA
	}
}