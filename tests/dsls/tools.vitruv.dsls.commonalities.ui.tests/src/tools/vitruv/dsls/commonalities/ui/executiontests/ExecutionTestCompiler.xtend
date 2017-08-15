package tools.vitruv.dsls.commonalities.ui.executiontests

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.ArrayList
import java.util.stream.Collectors
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.compiler.batch.BatchCompiler
import org.eclipse.pde.internal.core.PDECore
import org.eclipse.pde.internal.core.natures.PDE
import org.eclipse.xtext.builder.JDTAwareEclipseResourceFileSystemAccess2
import org.eclipse.xtext.generator.GeneratorContext
import org.eclipse.xtext.generator.GeneratorDelegate
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.ui.XtextProjectHelper
import org.osgi.framework.FrameworkUtil
import org.osgi.framework.wiring.BundleWiring
import tools.vitruv.dsls.common.VitruviusDslsCommonConstants
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import static com.google.common.base.Preconditions.*
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.xtext.ui.util.JREContainerProvider
import java.util.Hashtable

@Singleton
class ExecutionTestCompiler {
	static val TO_COMPILE = #['Identified.com']
	static val String COMPLIANCE_LEVEL = "1.8";

	static val compilationPackageFolders = #['tools/vitruv', 'org/eclipse/xtext/xbase/lib', 'allElementTypes',
		'com/google/common/base', 'org/eclipse/emf/ecore', 'org/eclipse/emf/common/util', 'org/apache/log4j']

	@Inject ParseHelper<CommonalityFile> parseHelper
	@Inject Provider<GeneratorDelegate> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject Provider<JDTAwareEclipseResourceFileSystemAccess2> fsaProvider

	static val TEST_PROJECT_GENERATED_SOURCES_FOLDER_NAME = 'src-gen'
	static val TEST_PROJECT_SOURCES_FOLDER_NAME = 'src'
	static val TEST_PROJECT_COMPILATION_FOLDER = 'bin'

	var Iterable<Class<? extends ChangePropagationSpecification>> loadedChangePropagationClasses
	var compiled = false

	def getChangePropagationDefinitions() {
		if (!compiled) {
			val compiledFolder = compile()
			compiled = true

			val classLoader = new URLClassLoader(#[compiledFolder.toUri.toURL], ExecutionTestCompiler.classLoader)
			loadedChangePropagationClasses = Files.find(compiledFolder, Integer.MAX_VALUE, [ path, info |
				path.last.toString.contains('ChangePropagationSpecification')
			])
				.map [compiledFolder.relativize(it)]
				.map [toString.replace('.class', '').replace(File.separator, '.')]
				.map [classLoader.loadClass(it) as Class<? extends ChangePropagationSpecification>]
				.collect(Collectors.toList)
				
			checkState(loadedChangePropagationClasses.size > 0, 'Failed to load change propagations!')
		}

		return loadedChangePropagationClasses.mapFixed[newInstance]
	}

	def private compile() {

		val testProject = prepareTestProject()

		val resultResourceSet = resourceSetProvider.get()

		for (commonalityInputFile : TO_COMPILE) {
			val reactionFileContent = new String(
				ByteStreams.toByteArray(class.getResourceAsStream(commonalityInputFile)))
			val reactionFileUri = URI.createURI(class.getResource(commonalityInputFile).toString)
			parseHelper.parse(reactionFileContent, reactionFileUri, resultResourceSet)
		}

		val generator = generatorProvider.get()
		val fsa = fsaProvider.get() => [
			project = testProject
			monitor = new NullProgressMonitor
			setOutputPath('DEFAULT_OUTPUT', TEST_PROJECT_GENERATED_SOURCES_FOLDER_NAME)
		]

		val toGenerate = new ArrayList(resultResourceSet.resources)
		for (commonalityResource : toGenerate) {
			generator.generate(commonalityResource, fsa, new GeneratorContext)
		}

		val compilationOutputFolder = testProject.getFolder(TEST_PROJECT_COMPILATION_FOLDER).path
		val compilationInputFolder = testProject.getFolder(TEST_PROJECT_GENERATED_SOURCES_FOLDER_NAME).path
		compileGeneratedJavaClasses(compilationInputFolder, compilationOutputFolder)
		
		// testProject.build(IncrementalProjectBuilder.FULL_BUILD, null)

		return compilationOutputFolder
	}

	/**
	 * Sets up the test project. Applies all settings needed to make the
	 * project usable. Some of those settings are not required for the tests to
	 * work, but make it possible to look around and debug in the generated
	 * project.
	 */
	def private prepareTestProject() {
		val eclipseProject = ResourcesPlugin.workspace.root.getProject('commonalities-test') => [
			create(null as IProgressMonitor)
			open(null)
			setDescription(description => [
				natureIds = #[JavaCore.NATURE_ID, XtextProjectHelper.NATURE_ID, PDE.PLUGIN_NATURE]
			], null)
			createManifestMf()
		]
		val sourcesFolder = eclipseProject.createFolder(TEST_PROJECT_SOURCES_FOLDER_NAME)
		val generatedSourcesFolder = eclipseProject.createFolder(TEST_PROJECT_GENERATED_SOURCES_FOLDER_NAME)
		val generatedSourcesSourceFolder = JavaCore.newSourceEntry(generatedSourcesFolder.fullPath)
		val sourcesSourceFolder = JavaCore.newSourceEntry(sourcesFolder.fullPath)
		val requiredPluginsContainer = JavaCore.newContainerEntry(PDECore.REQUIRED_PLUGINS_CONTAINER_PATH)
		val jreContainer = JREContainerProvider.defaultJREContainerEntry
		val javaProjectBinFolder = eclipseProject.getFolder('bin')
		val projectClasspath = #[sourcesSourceFolder, generatedSourcesSourceFolder, jreContainer, requiredPluginsContainer]
		val compilerVersion = '1.8'
		JavaCore.create(eclipseProject) => [
			options = new Hashtable => [
				put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, compilerVersion)
				put(JavaCore.COMPILER_COMPLIANCE, compilerVersion)
				put(JavaCore.COMPILER_SOURCE, compilerVersion)
			]
			setRawClasspath(projectClasspath, javaProjectBinFolder.fullPath, true, null)
			save(null, true)
		]
		
		return eclipseProject
	}

	def private createManifestMf(IProject project) {
		val mf = '''
		Manifest-Version: 1.0
		Bundle-ManifestVersion: 2
		Bundle-Name: Commonalities Language Test Project
		Bundle-Vendor: Vitruv-Tools
		Bundle-Version: 1.0.0.qualifier
		Bundle-SymbolicName: commonalities-test; singleton:=true
		Bundle-ActivationPolicy: lazy
		Require-Bundle: tools.vitruv.extensions.dslsruntime.commonalities,
		  tools.vitruv.framework.testutils.domains,
		  tools.vitruv.framework.tests.util.metamodels,
		  tools.vitruv.framework.metamodel,
		  tools.vitruv.extensions.emf,
		  org.eclipse.xtext.xbase.lib,
		  «FOR mirbasedependency : VitruviusDslsCommonConstants.VITRUVIUS_DEPENDENCIES SEPARATOR ','»
		  «mirbasedependency»
		  «ENDFOR»
		Bundle-RequiredExecutionEnvironment: JavaSE-1.8
		'''
		(project.getFolder('META-INF') => [create(true, false, null)]).getFile('MANIFEST.MF').create(
			new ByteArrayInputStream(mf.bytes), true, null)
	}

	def private compileGeneratedJavaClasses(Path inputFolder, Path outputFolder) {
		// copy in compile dependencies
		val bundle = FrameworkUtil.getBundle(ExecutionTestCompiler)
		val availableClassFiles = bundle.adapt(BundleWiring).listResources('/', '*.class',
			BundleWiring.LISTRESOURCES_RECURSE)
		val neededClassFiles = availableClassFiles.filter [ classFile |
			compilationPackageFolders.findFirst[classFile.startsWith(it)] !== null
		]
		for (classFile : neededClassFiles) {
			val classContent = bundle.getResource(classFile).openConnection.inputStream
			val targetFile = inputFolder.resolve(classFile)
			Files.createDirectories(targetFile.parent)
			Files.createFile(targetFile)
			ByteStreams.copy(classContent, new FileOutputStream(targetFile.toFile))
		}

		// compile
		val out = new ByteArrayOutputStream
		val err = out
		val iFolder = inputFolder.toAbsolutePath.toString
		val oFolder = outputFolder.toAbsolutePath.toString
		val success = BatchCompiler.compile(
			#["-" + COMPLIANCE_LEVEL, "-d", iFolder, "-classpath", iFolder, "-proc:none", oFolder],
			new PrintWriter(out), new PrintWriter(err), null)

		if (!success) {
			throw new RuntimeException("Unable to compile the generated reactions: \n\n" + out.toString)
		}
	}
	
	def private createFolder(IProject project, String name) {
		project.getFolder(name) => [
			create(true, false, null)
		]
	}

	def private static getPath(IResource eclipseResource) {
		eclipseResource.rawLocation.toFile.toPath
	}
}
