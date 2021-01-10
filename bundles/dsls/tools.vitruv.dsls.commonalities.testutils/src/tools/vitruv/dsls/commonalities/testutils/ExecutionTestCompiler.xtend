package tools.vitruv.dsls.commonalities.testutils

import com.google.inject.Inject
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.ArrayList
import java.util.HashSet
import java.util.function.Consumer
import org.apache.log4j.Logger
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.Platform
import org.eclipse.jdt.core.JavaCore
import org.eclipse.osgi.internal.framework.EquinoxBundle
import org.eclipse.osgi.storage.BundleInfo.Generation
import org.eclipse.pde.core.target.ITargetLocation
import org.eclipse.pde.core.target.LoadTargetDefinitionJob
import org.eclipse.pde.internal.core.natures.PDE
import org.eclipse.pde.internal.core.target.TargetPlatformService
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.XtextProjectHelper
import tools.vitruv.dsls.commonalities.generator.CommonalitiesGenerationSettings
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

import static com.google.common.base.Preconditions.*
import static java.util.stream.Collectors.toList
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace
import static tools.vitruv.testutils.TestLauncher.currentTestLauncher

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.testutils.CommonalitiesProjectSetup.*
import static extension tools.vitruv.dsls.common.ui.PluginProjectExtensions.*
import static extension tools.vitruv.dsls.common.ui.ProjectAccess.*
import edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil
import edu.kit.ipd.sdq.activextendannotations.CloseResource

@FinalFieldsConstructor
final class ExecutionTestCompiler {
	static val Logger logger = Logger.getLogger(ExecutionTestCompiler)

	var Iterable<Class<? extends ChangePropagationSpecification>> loadedChangePropagationClasses
	var compiled = false
	val Class<?> commonalitiesOwningClass
	val Path compilationProjectDir
	val CommonalitiesGenerationSettings generationSettings
	val Iterable<String> commonalityFilePaths
	val Iterable<String> domainDependencies

	def getChangePropagationSpecifications() {
		if (!compiled) {
			val compiledFolder = compile(prepareWorkspace())
			compiled = true

			val classLoader = new URLClassLoader(#[compiledFolder.toUri.toURL], commonalitiesOwningClass.classLoader)
			loadedChangePropagationClasses = Files.find(compiledFolder, Integer.MAX_VALUE, [ path, info |
				val fileName = path.fileName.toString
				fileName.contains('ChangePropagationSpecification') && fileName.endsWith('.class')
			]).map[compiledFolder.relativize(it)].map[toString.replace('.class', '').replace(File.separator, '.')].map [
				classLoader.loadClass(it) as Class<? extends ChangePropagationSpecification>
			].collect(toList)
		}

		checkState(loadedChangePropagationClasses.size > 0, "Failed to load change propagations!")

		return loadedChangePropagationClasses.mapFixed[declaredConstructor.newInstance].groupBy [
			new Pair(it.sourceDomain, it.targetDomain)
		].entrySet.mapFixed [
			val sourceDomain = it.key.key;
			val targetDomain = it.key.value;
			new CombinedChangePropagationSpecification(sourceDomain, targetDomain, it.value)
		]
	}

	private def Path compile(@CloseResource AutoCloseable workspacePreparation) {
		val testProject = prepareTestProject()
		setGenerationSettings()

		// copy in the source files
		for (commonalityFile : commonalityFilePaths) {
			val commonalityFileInputStream = commonalitiesOwningClass.getResourceAsStream(commonalityFile)
			if (commonalityFileInputStream === null) {
				throw new IllegalArgumentException("Could not find commonality file at: " + commonalityFile)
			}
			testProject.sourceFolder.createFile(Path.of(commonalityFile).fileName.toString, commonalityFileInputStream)
		}

		testProject.refresh()
		// build the files.
		// The build order is different from what Eclipse would do by
		// default. The order is important to get a build without errors.
		testProject.build(PDE.MANIFEST_BUILDER_ID)
		testProject.build(PDE.SCHEMA_BUILDER_ID)

		logger.trace("Xtext Build")
		testProject.build(XtextProjectHelper.BUILDER_ID)
		testProject.refresh()

		logger.trace("Java Build")
		testProject.build(JavaCore.BUILDER_ID)

		return testProject.binFolder.path
	}

	private def AutoCloseable prepareWorkspace() {
		closeWelcomePage()

		return if (workspace.description.autoBuilding) {
			workspace.description = workspace.description => [autoBuilding = false]
			[workspace.description = workspace.description => [autoBuilding = true]]
		} else {
			[]
		}
	}

	/**
	 * Sets up the test project. Applies all settings needed to make the
	 * project usable. Some of those settings are not required for the tests to
	 * work, but make it possible to look around and debug in the generated
	 * project.
	 */
	private def prepareTestProject() {
		setTargetPlatform()

		val testProject = IProjectUtil.createProjectAt('''«commonalitiesOwningClass.simpleName»-Commonalities''',
			compilationProjectDir).setupAsCommonalitiesProject()
		val testPluginProject = testProject.pluginProject
		for (domainDependency : domainDependencies) {
			testPluginProject.addRequiredBundle(domainDependency)
		}
		testPluginProject.apply(new NullProgressMonitor)

		return testProject
	}

	private def setGenerationSettings() {
		switch (currentTestLauncher) {
			case ECLIPSE:
				// always generate reactions when run from Eclipse, as they are helpful for debugging.
				generationSettings.createReactionFiles = true
			case SUREFIRE:
				// never create reactions when run from Maven because it is unnecessary and logs errors.
				generationSettings.createReactionFiles = false
			case UNKNOWN: {
			} // use default 
		}
	}

	/**
	 * Sets a target platform in the test platform. This is required to run the
	 * tests with tycho.
	 * 
	 * Taken from http://git.eclipse.org/c/gmf-tooling/org.eclipse.gmf-tooling.git/tree/tests/org.eclipse.gmf.tests/src/org/eclipse/gmf/tests/Utils.java#n146
	 * 
	 * Necessary because of this bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=343156
	 */
	@SuppressWarnings('restriction')
	static def void setTargetPlatform() {
		val tpService = TargetPlatformService.getDefault()
		val bundles = Platform.getBundle('org.eclipse.core.runtime').getBundleContext().getBundles()
		val bundleContainers = new ArrayList<ITargetLocation>()
		val dirs = new HashSet<File>()

		for (bundle : bundles) {
			val bundleImpl = bundle as EquinoxBundle
			val generation = bundleImpl.getModule().getCurrentRevision().getRevisionInfo() as Generation
			val file = generation.getBundleFile().getBaseFile()
			val folder = file.getParentFile()
			if (!dirs.contains(folder)) {
				dirs.add(folder)
				bundleContainers.add(tpService.newDirectoryLocation(folder.getAbsolutePath()))
			}
		}
		val targetDef = tpService.newTarget() => [
			name = 'Test Target Platform'
			targetLocations = bundleContainers
			arch = Platform.OSArch
			OS = Platform.OS
			WS = Platform.WS
			NL = Platform.NL
		]
		tpService.saveTargetDefinition(targetDef)

		val job = new LoadTargetDefinitionJob(targetDef)
		job.schedule()
		job.join()
	}

	@Accessors
	static class Parameters {
		var Object commonalitiesOwner
		var Path compilationProjectDir
		var Iterable<String> commonalities = null
		var Iterable<String> domainDependencies = null
	}

	static class Factory {
		@Inject CommonalitiesGenerationSettings generationSettings
		var parameters = new Parameters

		def setParameters(Consumer<ExecutionTestCompiler.Parameters> configurer) {
			configurer.accept(parameters)
			this
		}

		def createCompiler(Consumer<ExecutionTestCompiler.Parameters> configurer) {
			setParameters(configurer)
			return new ExecutionTestCompiler(
				parameters.commonalitiesOwner.class,
				parameters.compilationProjectDir,
				generationSettings,
				parameters.commonalities,
				parameters.domainDependencies
			)
		}
	}
}
