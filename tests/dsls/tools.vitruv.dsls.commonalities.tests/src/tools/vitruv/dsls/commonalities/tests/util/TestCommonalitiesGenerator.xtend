package tools.vitruv.dsls.commonalities.tests.util

import com.google.inject.Provider
import java.nio.file.Path
import javax.inject.Inject
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.xbase.testing.CompilationTestHelper

import static java.nio.charset.StandardCharsets.UTF_8
import static org.eclipse.emf.common.util.URI.*

import static extension java.nio.file.Files.writeString
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil
import org.eclipse.xtext.generator.GeneratorDelegate
import org.eclipse.xtext.generator.GeneratorContext
import org.eclipse.xtext.generator.URIBasedFileSystemAccess
import org.eclipse.xtext.xbase.testing.InMemoryJavaCompiler
import org.eclipse.xtext.util.JavaVersion
import java.util.stream.Stream
import edu.kit.ipd.sdq.activextendannotations.CloseResource
import static extension java.nio.file.Files.walk
import org.eclipse.xtext.xbase.testing.JavaSource
import static extension java.nio.file.Files.readString
import static java.lang.System.lineSeparator
import static java.util.stream.Collectors.toList
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.jdt.core.compiler.CategorizedProblem
import static extension java.lang.reflect.Modifier.*
import java.util.List
import static com.google.common.base.Preconditions.checkState
import org.eclipse.core.runtime.Platform
import static extension tools.vitruv.testutils.change.processing.MetamodelRegisteringChangePropagationSpecification.registerMetamodelsBeforePropagating
import tools.vitruv.testutils.change.processing.CombinedChangePropagationSpecification
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.hasNoErrors

/**
 * Xtext’s {@link CompilationTestHelper} is bug-ridden and does not work with the Ecore generator.
 * So we roll our own.
 */
class TestCommonalitiesGenerator {
	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject IResourceServiceProvider.Registry resourceServiceRegistry

	def generate(Path testProject, Pair<String, CharSequence>... code) {
		checkState(!Platform.isRunning, '''«TestCommonalitiesGenerator.simpleName» can only be used in standalone mode!''')
		
		code.writeTo(testProject)
		
		loadResourceSet(testProject, code.map[key]) => [
			index()
			validate()
			generateInto(testProject)
		]
		
		compileGeneratedJava(testProject)
			.findAndCombineChangePropagationSpecifications()
	}

	def private writeTo(Iterable<Pair<String, CharSequence>> code, Path testProject) {
		for (sourceCode : code) {
			testProject.getSourcePath(sourceCode.key).writeString(sourceCode.value, UTF_8)
		}
	}
	
	def private loadResourceSet(Path testProject, Iterable<String> paths) {
		val resourceSet = resourceSetProvider.get()
		for (path : paths) {
			resourceSet.createResource(createFileURI(testProject.getSourcePath(path).toString))
		}
		resourceSet.resources.forEach[load(emptyMap)]
		resourceSet
	}

	def private void index(ResourceSet resourceSet) {
		val resourceDescriptions = resourceSet.resources.map [ resource |
			resourceServiceRegistry.getResourceServiceProvider(resource.URI)
				.resourceDescriptionManager
				.getResourceDescription(resource)
		]
		val index = new ResourceDescriptionsData(resourceDescriptions)
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index)
	}

	def private void validate(ResourceSet resourceSet) {
		for (sourceResource : resourceSet.resources.filter(XtextResource).toList) {
			sourceResource.resourceServiceProvider
				.resourceValidator
				.validate(sourceResource, CheckMode.ALL, CancelIndicator.NullImpl)
			assertThat(sourceResource, hasNoErrors)
		}
	}
	
	def private void generateInto(ResourceSet resourceSet, Path testProject) {
		val fsa = new URIBasedFileSystemAccess => [
			converter = resourceSet.URIConverter
			outputPath = testProject.resolve(IProjectUtil.SOURCE_GEN_FOLDER).toString
		]
		val context = new GeneratorContext() => [
			cancelIndicator = CancelIndicator.NullImpl
		]
		
		for (sourceResource : resourceSet.resources.filter(XtextResource).toList) {
			sourceResource.resourceServiceProvider
				.get(GeneratorDelegate)
				.generate(sourceResource, fsa, context)
		}
	}
	
	def private compileGeneratedJava(Path testProject) {
		val generatedSourcesDir = testProject.resolve(IProjectUtil.SOURCE_GEN_FOLDER)
		compileGeneratedJava(
			generatedSourcesDir
				.walk()
				.filter [toString.endsWith(".java")]
				.map [new RelativeAndAbsolutePath(generatedSourcesDir, it)]
		)
	}
	
	def private Iterable<? extends Class<?>> compileGeneratedJava(@CloseResource Stream<RelativeAndAbsolutePath> sourceFiles) {
		val compiler = new InMemoryJavaCompiler(class.classLoader, JavaVersion.JAVA8)
		val sourceFilePaths = sourceFiles.collect(toList())
		val result = compiler.compile(
			sourceFilePaths.map [new JavaSource(relative.toString, absolute.readString())]
		)
		// use the same class loader for all classes!
		val classLoader = result.classLoader
		if (result.compilationProblems.exists [isError]) {
			throw new AssertionError("compiling the generated code failed with these errors:" + lineSeparator 
				+ result.compilationProblems.filter [isError].join(lineSeparator) ['    • ' + format()])
		}
		sourceFilePaths.mapFixed [classLoader.loadClass(relative.className)]
	}
	
	def private format(extension CategorizedProblem problem) {
		'''«message» («new String(originatingFileName)»:«sourceLineNumber»)'''
	}
	
	def private findAndCombineChangePropagationSpecifications(Iterable<? extends Class<?>> sourceClasses) {
		sourceClasses.filter [
			allInterfaces.contains(ChangePropagationSpecification) 
				&& modifiers.isPublic && getDeclaredConstructor.modifiers.isPublic
		]
			.map [getDeclaredConstructor.newInstance as ChangePropagationSpecification]
			.groupBy [sourceDomain -> targetDomain]
			.entrySet
			.mapFixed [
				new CombinedChangePropagationSpecification(key.key, key.value, value)
					.registerMetamodelsBeforePropagating()
			]
	}
	
	def private static getSourcePath(Path testProject, String fileName) {
		testProject.resolve(fileName)
	}
	
	def private static getClassName(Path path) {
		path.join('.').replaceFirst("\\.java$", "")
	}
	
	def private static Iterable<Class<?>> getAllInterfaces(Class<?> clazz) {
		if (clazz.isInterface) {
			List.of(clazz) + clazz.interfaces.flatMap[allInterfaces]
		} else if (clazz.superclass != Object) {
			clazz.interfaces.flatMap[allInterfaces] + clazz.superclass.allInterfaces
		} else {
			clazz.interfaces.flatMap[allInterfaces]
		}
	}
	
	private static class RelativeAndAbsolutePath {
		val Path baseDir
		val Path path
		
		new(Path baseDir, Path path) {
			this.baseDir = baseDir
			this.path = if (path.isAbsolute) baseDir.relativize(path) else path
		}
		
		def getRelative() {
			path
		}
		
		def getAbsolute() {
			baseDir.resolve(path)
		}
	}
}
