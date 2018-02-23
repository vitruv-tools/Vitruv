package tools.vitruv.dsls.reactions.tests

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.google.inject.Provider
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.PrintWriter
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.compiler.batch.BatchCompiler
import org.eclipse.xtext.generator.JavaIoFileSystemAccess
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.util.ParseHelper
import org.osgi.framework.FrameworkUtil
import org.osgi.framework.wiring.BundleWiring
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

class TestReactionsCompiler {

	static val String COMPLIANCE_LEVEL = "1.8"

	protected static val DEFAULT_COMPILATION_PACKAGE_FOLDERS = #['tools/vitruv', 'org/eclipse/xtext/xbase/lib', 'allElementTypes',
		'com/google/common/base', 'org/eclipse/emf/ecore', 'org/eclipse/emf/common/util', 'org/apache/log4j']

	static val REACTIONS_BASE_PACKAGE = "mir.reactions"
	static val CHANGE_PROPAGATION_SPEC_FQN_SUFFIX = "ChangePropagationSpecification"

	@Inject ParseHelper<ReactionsFile> parseHelper
	@Inject Provider<IReactionsGenerator> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject Provider<JavaIoFileSystemAccess> fsaProvider

	val Iterable<String> inputReactionFiles
	val Iterable<String> changePropagationSegments

	var compiled = false
	var Supplier<Iterable<? extends ChangePropagationSpecification>> changePropagationSpecsSupplier

	new(Iterable<String> inputReactionFiles, Iterable<String> changePropagationSegments) {
		this.inputReactionFiles = inputReactionFiles
		this.changePropagationSegments = changePropagationSegments
	}

	def private compileReactions() {
		val outputFolder = Files.createTempDirectory('reactions-test-compile')
		outputFolder.toFile.deleteOnExit
		val fsa = fsaProvider.get()
		fsa.outputPath = outputFolder.toString

		val generator = generatorProvider.get()
		val resultResourceSet = resourceSetProvider.get()

		for (inputReactionFile : inputReactionFiles) {
			val reactionFileContent = new String(ByteStreams.toByteArray(class.getResourceAsStream(inputReactionFile)))
			val reactionFileUri = URI.createURI(class.getResource(inputReactionFile).toString)
			parseHelper.parse(reactionFileContent, reactionFileUri, resultResourceSet)
		}

		generator.addReactionsFiles(resultResourceSet)
		generator.generate(fsa)

		compileGeneratedJavaClasses(outputFolder)

		outputFolder
	}

	protected def getCompilationPackageFolders() {
		return DEFAULT_COMPILATION_PACKAGE_FOLDERS
	}

	def private compileGeneratedJavaClasses(Path outputFolder) {
		// copy in needed class files, including compile dependencies:
		val bundle = FrameworkUtil.getBundle(this.class)
		val availableClassFiles = bundle.adapt(BundleWiring).listResources('/', '*.class', BundleWiring.LISTRESOURCES_RECURSE)
		val compilationPackageFolders = getCompilationPackageFolders()
		val neededClassFiles = availableClassFiles.filter [ classFile |
			compilationPackageFolders.findFirst[classFile.startsWith(it)] !== null
		];
		for (classFile : neededClassFiles) {
			val classContent = bundle.getResource(classFile).openConnection.inputStream
			val targetFile = outputFolder.resolve(classFile)
			Files.createDirectories(targetFile.parent)
			Files.createFile(targetFile)
			ByteStreams.copy(classContent, new FileOutputStream(targetFile.toFile))
		}

		// compile:
		val out = new ByteArrayOutputStream
		val err = out
		val ioFolder = outputFolder.toAbsolutePath.toString
		val success = BatchCompiler.compile(#["-" + COMPLIANCE_LEVEL, "-d", ioFolder, "-classpath", ioFolder, "-proc:none", ioFolder],
			new PrintWriter(out), new PrintWriter(err), null)

		if (!success) {
			throw new RuntimeException("Unable to compile the generated reactions: \n\n" + out.toString)
		}
	}

	def getNewChangePropagationSpecifications() {
		if (!compiled) {
			compiled = true
			val compiledReactionsFolder = compileReactions()

			val url = compiledReactionsFolder.toUri.toURL
			val loader = new URLClassLoader(#[url], this.class.classLoader)

			val changePropagationSpecFQNs = changePropagationSegments.filterNull.map [
				REACTIONS_BASE_PACKAGE + "." + it.toFirstLower + "." + it.toFirstUpper + CHANGE_PROPAGATION_SPEC_FQN_SUFFIX
			]
			val classes = newLinkedHashSet()
			for (changePropagationSpecFQN : changePropagationSpecFQNs) {
				classes.add(loader.loadClass(changePropagationSpecFQN) as Class<? extends ChangePropagationSpecification>)
			}

			changePropagationSpecsSupplier = [classes.map[newInstance]]
		}
		changePropagationSpecsSupplier.get()
	}
}
