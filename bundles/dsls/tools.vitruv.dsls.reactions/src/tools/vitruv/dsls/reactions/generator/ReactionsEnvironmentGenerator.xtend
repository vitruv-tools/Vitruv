package tools.vitruv.dsls.reactions.generator

import static extension tools.vitruv.dsls.reactions.codegen.helper.ReactionsLanguageHelper.*;
import tools.vitruv.framework.domains.VitruvDomain
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess2
import edu.kit.ipd.sdq.commons.util.java.Pair
import static extension tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators.*
import static extension tools.vitruv.dsls.common.helper.JavaFileGenerator.*
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import org.eclipse.xtext.util.RuntimeIOException
import java.util.function.Supplier
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.nio.charset.StandardCharsets
import java.io.PipedInputStream
import java.io.PipedOutputStream
import tools.vitruv.dsls.reactions.generator.ChangePropagationFileParser.ParseException
import java.io.IOException
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import java.util.concurrent.Executors
import java.util.concurrent.ExecutionException
import java.util.concurrent.Callable
import java.util.Arrays
import tools.vitruv.dsls.common.helper.JavaImportHelper
import tools.vitruv.framework.change.processing.impl.CompositeDecomposingChangePropagationSpecification

class ReactionsEnvironmentGenerator {

	static val MARKER_UNIQUE_PART = "generated executor list"
	public static val EXECUTORS_MARKER_START = "// begin " + MARKER_UNIQUE_PART
	public static val EXECUTORS_MARKER_END = "// end " + MARKER_UNIQUE_PART
	public static val VERSION_MARKER = "Generated from template version"
	public static val CHANGE_PROPAGATION_FILE_CHARSET = StandardCharsets.UTF_8
	public static val EXECUTOR_LIST_INDENT = 2

	private static val executor = Executors.newCachedThreadPool
	
	def generateEnvironment(ResourceSet resourceSet, IFileSystemAccess2 fsa) {
		generateEnvironment(resourceSet.resources.map[optionalReactionsFile].filterNull, fsa)
	}

	def private generateEnvironment(Iterable<ReactionsFile> resources, IFileSystemAccess2 fsa) {
		resources.flatMap[reactionsSegments].groupBy[sourceTargetPair].entrySet.forEach [
			ensureChangePropagationSpecification(key, value, fsa)
		]
	}

	def private ensureChangePropagationSpecification(Pair<VitruvDomain, VitruvDomain> modelCombination,
		List<ReactionsSegment> segments, IFileSystemAccess2 fsa) {
		val specificationPath = modelCombination.changePropagationSpecificationClassNameGenerator.qualifiedName.javaFilePath
		val executors = segments.map [executorClassNameGenerator.qualifiedName]
		var generateNew = false
		try {
			val fileContent = fsa.readBinaryFile(specificationPath)

			val newFileContentTarget = new PipedOutputStream
			val newFileContentSource = new PipedInputStream(newFileContentTarget)
			val parseTask = new Callable<Void>() {
				override call() throws IOException, ParseException {
					val parser = new ChangePropagationFileParser(fileContent, newFileContentTarget)
					parser.changeExecutors(executors, [shouldKeepExecutor(it, fsa)])
					null
				}
			}
			val parserTask = executor.submit(parseTask)
			fsa.generateFile(specificationPath, newFileContentSource)
			parserTask.get // check for exceptions

		} catch (RuntimeIOException ioException) {
			// the file cannot be read. We assume it doesn’t exist.
			generateNew = true
		} catch (ExecutionException parserException) {
			// error while re-writing the file or malformed file. We override.
			generateNew = true
		}
		if (generateNew) {
			fsa.generateFile(specificationPath, generateChangePropagationSpecification(modelCombination, executors))
		}
	}

	def private shouldKeepExecutor(String executorName, IFileSystemAccess2 fsa) {
		fsa.isFile('''«executorName.replace('.', FSA_SEPARATOR)».java''')
	}

	/**
	 * Increase this number whenever making changes to the template below!
	 * Otherwise, existing change propagation files will not get updated!
	 */
	public static val TEMPLATE_VERSION = 1

	def private generateChangePropagationSpecification(Pair<VitruvDomain, VitruvDomain> modelPair,
		List<String> executorsNames) {
		val extension ih = new JavaImportHelper();
		val changePropagationSpecificationNameGenerator = modelPair.changePropagationSpecificationClassNameGenerator
		'''
			/**
			 * The {@link «CompositeDecomposingChangePropagationSpecification»} for transformations between the metamodels «modelPair.first.name» and «modelPair.second.name».
			 * To add further change processors override the setup method.
			 *
			 * <p> This file is generated! Do not edit it but extend it by inheriting from it!
			 * 
			 * <p> «VERSION_MARKER» «TEMPLATE_VERSION»
			 */
			public class «changePropagationSpecificationNameGenerator.simpleName» extends «CompositeDecomposingChangePropagationSpecification.typeRef» {
				
				private final «List.typeRef»<«Supplier.typeRef»<? extends «ChangePropagationSpecification.typeRef»>> executors = «Arrays.typeRef».asList(
					«EXECUTORS_MARKER_START»
					«FOR executorName : executorsNames SEPARATOR ',\n'»
						«executorName»::new
					«ENDFOR»
					«EXECUTORS_MARKER_END»
				);
				
				public «changePropagationSpecificationNameGenerator.simpleName»() {
					super(new «modelPair.first.providerForDomain.canonicalNameForReference.typeRef»().getDomain(), 
						new «modelPair.second.providerForDomain.canonicalNameForReference.typeRef»().getDomain());
					setup();
				}
				
				/**
				 * Adds the reactions change processors to this {@link «changePropagationSpecificationNameGenerator.simpleName»}.
				 * For adding further change processors overwrite this method and call the super method at the right place.
				 */
				protected void setup() {
					for (final «Supplier.typeRef»<? extends «ChangePropagationSpecification.typeRef»> executorSupplier : executors) {
						this.addChangeMainprocessor(executorSupplier.get());
					}	
				}
			}
		'''.generateClass(changePropagationSpecificationNameGenerator.packageName, ih)
	}
}
