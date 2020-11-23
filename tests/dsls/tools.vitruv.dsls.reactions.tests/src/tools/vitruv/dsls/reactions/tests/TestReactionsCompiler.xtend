package tools.vitruv.dsls.reactions.tests

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.google.inject.Provider
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.net.URLClassLoader
import java.nio.file.Path
import java.util.function.Consumer
import java.util.function.Supplier
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.compiler.batch.BatchCompiler
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.generator.JavaIoFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.util.ParseHelper
import org.osgi.framework.FrameworkUtil
import org.osgi.framework.wiring.BundleWiring
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

import static com.google.common.base.Preconditions.checkState
import static java.nio.file.Files.createDirectories
import static java.nio.file.Files.newOutputStream
import static java.nio.file.Files.readString
import static java.nio.file.StandardOpenOption.CREATE_NEW
import static org.osgi.framework.wiring.BundleWiring.LISTRESOURCES_RECURSE

@FinalFieldsConstructor
class TestReactionsCompiler {
	static val String COMPLIANCE_LEVEL = "1.8"

	protected static val COMPILATION_PACKAGE_FOLDERS = #['tools/vitruv', 'org/eclipse/xtext/xbase/lib',
		'allElementTypes', 'com/google/common/base', 'org/eclipse/emf/ecore', 'org/eclipse/emf/common/util',
		'org/apache/log4j']

	static val REACTIONS_BASE_PACKAGE = "mir.reactions"
	static val CHANGE_PROPAGATION_SPEC_FQN_SUFFIX = "ChangePropagationSpecification"

	val Class<?> reactionsOwner
	val Path compilationProjectFolder
	val Iterable<String> inputReactionFiles
	val Iterable<String> changePropagationSegments

	val ParseHelper<ReactionsFile> parseHelper
	val Provider<IReactionsGenerator> generatorProvider
	val Provider<XtextResourceSet> resourceSetProvider
	val Provider<JavaIoFileSystemAccess> fsaProvider

	var compiled = false
	var Supplier<Iterable<ChangePropagationSpecification>> changePropagationSpecsSupplier

	def private compileReactions() {
		val sourceFolder = createDirectories(compilationProjectFolder.resolve("src"))
		val generatedSourceFolder = createDirectories(compilationProjectFolder.resolve("src-gen"))
		val fsa = fsaProvider.get()
		fsa.outputPath = generatedSourceFolder.toString

		val generator = generatorProvider.get()
		val resultResourceSet = resourceSetProvider.get()

		for (inputReactionFile : inputReactionFiles) {
			val reactionFileStream = reactionsOwner.getResourceAsStream(inputReactionFile)
			val srcFile = sourceFolder.resolve(Path.of(inputReactionFile).fileName)
			ByteStreams.copy(reactionFileStream, newOutputStream(srcFile, CREATE_NEW))

			val reactionFileContent = readString(srcFile)
			val reactionFileUri = URI.createFileURI(srcFile.toString)
			parseHelper.parse(reactionFileContent, reactionFileUri, resultResourceSet)
		}

		generator.addReactionsFiles(resultResourceSet)
		generator.generate(fsa)

		return compileGeneratedJavaClasses(generatedSourceFolder)
	}

	def private compileGeneratedJavaClasses(Path generatedSourceFolder) {
		// copy in needed class files, including compile dependencies:
		val bundle = FrameworkUtil.getBundle(reactionsOwner)
		val availableClassFiles = bundle.adapt(BundleWiring).listResources('/', '*.class', LISTRESOURCES_RECURSE)
		val neededClassFiles = availableClassFiles.filter [ classFile |
			COMPILATION_PACKAGE_FOLDERS.findFirst[classFile.startsWith(it)] !== null
		]
		val dependenciesFolder = compilationProjectFolder.resolve("lib")
		for (classFile : neededClassFiles) {
			val classContent = bundle.getResource(classFile).openConnection.inputStream
			val targetFile = dependenciesFolder.resolve(classFile)
			createDirectories(targetFile.parent)
			ByteStreams.copy(classContent, newOutputStream(targetFile, CREATE_NEW))
		}

		// compile:
		val outputFolder = compilationProjectFolder.resolve("out")
		val out = new ByteArrayOutputStream
		val err = out
		val success = BatchCompiler.compile(
			#["-" + COMPLIANCE_LEVEL, "-d", outputFolder.toString, "-classpath", dependenciesFolder.toString,
				"-proc:none", generatedSourceFolder.toString], new PrintWriter(out), new PrintWriter(err), null)

		checkState(success, "Unable to compile the generated reactions: \n\n" + out.toString)
		return outputFolder
	}

	def static getBatchCompilerString(Path path) {
		path.toString.replace('[', '\\[').replace(']', '\\]')
	}

	def Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		if (!compiled) {
			compiled = true
			val outputFolder = compileReactions()

			val loader = new URLClassLoader(#[outputFolder.toUri.toURL], reactionsOwner.classLoader)
			val changePropagationSpecFQNs = changePropagationSegments.filterNull.map [
				REACTIONS_BASE_PACKAGE + "." + it.toFirstLower + "." + it.toFirstUpper +
					CHANGE_PROPAGATION_SPEC_FQN_SUFFIX
			]
			val classes = changePropagationSpecFQNs.map [ spec |
				loader.loadClass(spec) as Class<? extends ChangePropagationSpecification>
			].toSet

			changePropagationSpecsSupplier = [classes.map[declaredConstructor.newInstance]]
		}
		changePropagationSpecsSupplier.get()
	}

	@Accessors
	static class TestReactionsCompilerParameters {
		var Object reactionsOwner
		var Path compilationProjectDir
		var Iterable<String> reactions = null
		var Iterable<String> changePropagationSegments = null
	}

	static class Factory {
		@Inject ParseHelper<ReactionsFile> parseHelper
		@Inject Provider<IReactionsGenerator> generatorProvider
		@Inject Provider<XtextResourceSet> resourceSetProvider
		@Inject Provider<JavaIoFileSystemAccess> fsaProvider
		var parameters = new TestReactionsCompilerParameters

		def setParameters(Consumer<TestReactionsCompiler.TestReactionsCompilerParameters> configurer) {
			configurer.accept(parameters)
		}

		def createCompiler(Consumer<TestReactionsCompiler.TestReactionsCompilerParameters> configurer) {
			setParameters(configurer)
			return new TestReactionsCompiler(
				parameters.reactionsOwner.class,
				parameters.compilationProjectDir,
				parameters.reactions,
				parameters.changePropagationSegments,
				parseHelper,
				generatorProvider,
				resourceSetProvider,
				fsaProvider
			)
		}
	}
}
