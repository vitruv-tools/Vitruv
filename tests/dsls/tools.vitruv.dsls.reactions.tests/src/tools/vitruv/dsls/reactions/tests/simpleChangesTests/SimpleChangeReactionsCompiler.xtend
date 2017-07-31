package tools.vitruv.dsls.reactions.tests.simpleChangesTests

import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.generator.ReactionsGenerator
import com.google.inject.Provider
import java.util.function.Supplier
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.nio.file.Files
import org.eclipse.core.runtime.Platform
import org.osgi.framework.wiring.BundleWiring
import com.google.common.io.ByteStreams
import java.io.FileOutputStream
import javax.tools.ToolProvider
import java.net.URLClassLoader
import java.nio.file.Path
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.generator.JavaIoFileSystemAccess
import org.eclipse.emf.common.util.URI

class SimpleChangeReactionsCompiler {
	static val INPUT_REACTION_FILES = #["SimpleChangesTests.reactions", "SimpleChangesRootTests.reactions"]

	static val SIMPLE_CHANGES_PROPAGATION_SPEC_FQN = "mir.reactions.AllElementTypesToAllElementTypesChangePropagationSpecification"
	static var Supplier<? extends ChangePropagationSpecification> SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER

	static val compilationPackageFolders = #['tools/vitruv', 'org/eclipse/xtext/xbase', 'allElementTypes',
		'com/google/common', 'org/eclipse/emf', 'org/apache/log4j']

	static var compiled = false

	@Inject ParseHelper<ReactionsFile> parseHelper
	@Inject Provider<ReactionsGenerator> generatorProvider
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
		generator.addReactionFiles(resultResourceSet)
		generator.generate(fsa)

		compileGeneratedJavaClasses(outputFolder)

		outputFolder
	}

	def private compileGeneratedJavaClasses(Path outputFolder) {
		// copy in compile dependencies
		val bundle = Platform.getBundle('tools.vitruv.dsls.reactions.tests')
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

		// write a list of all java files
		val compileList = outputFolder.resolve('compile.list')
		Files.write(compileList, [
			Files.find(outputFolder, Integer.MAX_VALUE, [path, x|path.toString.endsWith('.java')]).map[toString].
				iterator
		] as Iterable<String>)

		// compile
		val in = new ByteArrayInputStream(#[])
		val out = new ByteArrayOutputStream
		val err = out
		val compiler = ToolProvider.getSystemJavaCompiler()
		val result = compiler.run(in, out, err, '@' + compileList.toString, '-classpath', outputFolder.toString)

		if (result !== 0) {
			throw new RuntimeException("Unable to compile the generated reactions: \n\n" + out.toString)
		}
	}

	def newConcreteChangePropagationSpecification() {
		if (!compiled) {
			compiled = true
			val compiledReactionsFolder = compileTestReactions()

			val url = compiledReactionsFolder.toUri.toURL
			val loader = new URLClassLoader(#[url], SimpleChangeReactionsCompiler.classLoader)

			val clazz = loader.loadClass(SIMPLE_CHANGES_PROPAGATION_SPEC_FQN) as Class<? extends ChangePropagationSpecification>
			SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER = [clazz.newInstance]
		}
		SIMPLE_CHANGES_PROPGATION_SPEC_SUPLLIER.get()
	}
}
