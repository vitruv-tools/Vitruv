package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import com.google.inject.Provider
import java.util.function.Supplier
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.nio.file.Files
import org.osgi.framework.wiring.BundleWiring
import com.google.common.io.ByteStreams
import java.io.FileOutputStream
import java.net.URLClassLoader
import java.nio.file.Path
import java.io.ByteArrayOutputStream
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.generator.JavaIoFileSystemAccess
import org.eclipse.emf.common.util.URI
import org.eclipse.jdt.core.compiler.batch.BatchCompiler
import java.io.PrintWriter
import org.osgi.framework.FrameworkUtil
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator

class SimpleChangeReactionsCompiler {
	static val INPUT_REACTION_FILES = #["SimpleChangesTests.reactions", "SimpleChangesRootTests.reactions"]

	static val SIMPLE_CHANGES_PROPAGATION_SPEC_FQN = "mir.reactions.AllElementTypesToAllElementTypesChangePropagationSpecification"
	static var Supplier<? extends ChangePropagationSpecification> SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER
	static val String COMPLIANCE_LEVEL = "1.8";

	static val compilationPackageFolders = #['tools/vitruv', 'org/eclipse/xtext/xbase/lib', 'allElementTypes',
		'com/google/common/base', 'org/eclipse/emf/ecore', 'org/eclipse/emf/common/util', 'org/apache/log4j']

	static var compiled = false

	@Inject ParseHelper<ReactionsFile> parseHelper
	@Inject Provider<IReactionsGenerator> generatorProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject Provider<JavaIoFileSystemAccess> fsaProvider

	def private compileTestReactions() {
		val outputFolder = Files.createTempDirectory('reactions-test-compile')
		outputFolder.toFile.deleteOnExit
		val fsa = fsaProvider.get()
		fsa.outputPath = outputFolder.toString

		val generator = generatorProvider.get()
		val resultResourceSet = resourceSetProvider.get()
		for (reactionFile : INPUT_REACTION_FILES) {
			val reactionFileContent = new String(ByteStreams.toByteArray(class.getResourceAsStream(reactionFile)))
			val reactionFileUri = URI.createURI(class.getResource(reactionFile).toString)
			parseHelper.parse(reactionFileContent, reactionFileUri, resultResourceSet)
		}
		generator.addReactionsFiles(resultResourceSet)
		generator.generate(fsa)

		compileGeneratedJavaClasses(outputFolder)

		outputFolder
	}

	def private compileGeneratedJavaClasses(Path outputFolder) {
		// copy in compile dependencies
		val bundle = FrameworkUtil.getBundle(SimpleChangeReactionsCompiler)
		val availableClassFiles = bundle.adapt(BundleWiring).listResources('/', '*.class',
			BundleWiring.LISTRESOURCES_RECURSE)
		val neededClassFiles = availableClassFiles.filter [ classFile |
			compilationPackageFolders.findFirst[classFile.startsWith(it)] !== null
		]
		for (classFile : neededClassFiles) {
			val classContent = bundle.getResource(classFile).openConnection.inputStream
			val targetFile = outputFolder.resolve(classFile)
			Files.createDirectories(targetFile.parent)
			Files.createFile(targetFile)
			ByteStreams.copy(classContent, new FileOutputStream(targetFile.toFile))
		}

		// compile
		val out = new ByteArrayOutputStream
		val err = out
		val ioFolder = outputFolder.toAbsolutePath.toString
		val success = BatchCompiler.compile(#["-" + COMPLIANCE_LEVEL, "-d", ioFolder, "-classpath", ioFolder, "-proc:none", ioFolder],
			new PrintWriter(out), new PrintWriter(err), null)

		if (!success) {
			throw new RuntimeException("Unable to compile the generated reactions: \n\n" + out.toString)
		}
	}

	def newConcreteChangePropagationSpecification() {
		if (!compiled) {
			compiled = true
			val compiledReactionsFolder = compileTestReactions()

			val url = compiledReactionsFolder.toUri.toURL
			val loader = new URLClassLoader(#[url], SimpleChangeReactionsCompiler.classLoader)

			val clazz = loader.loadClass(
				SIMPLE_CHANGES_PROPAGATION_SPEC_FQN) as Class<? extends ChangePropagationSpecification>
			SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER = [clazz.newInstance]
		}
		SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER.get()
	}
}
