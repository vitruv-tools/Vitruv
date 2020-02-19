package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.Collections
import org.eclipse.emf.codegen.ecore.generator.Generator
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.plugin.EcorePlugin
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.xtext.resource.XtextResourceSet
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.framework.util.VitruviusConstants

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class IntermediateModelCodeGenerator extends SubGenerator {

	static val GENERATED_CODE_COMPLIANCE_LEVEL = GenJDKLevel.JDK80_LITERAL
	static val GENERATED_CODE_FOLDER = "."
	static val INTERMEDIATEMODELBASE_GENMODEL_URI = EcorePlugin.getEPackageNsURIToGenModelLocationMap(true).get(IntermediateModelBasePackage.eINSTANCE.nsURI)
	@Lazy val GenModel intermediateModelBaseGenModel = resourceSet.getResource(INTERMEDIATEMODELBASE_GENMODEL_URI, true).contents.head as GenModel

	override generate() {
		if (!isNewResourceSet) return
		val generatedCodeDirectory = fsa.getURI(GENERATED_CODE_FOLDER)
		for (generatedConcept : generatedConcepts) {
			val generatedPackage = generatedConcept.intermediateModelPackage
			val generatedGenModel = generateGenModel(generatedPackage, generatedConcept, generatedCodeDirectory)
			generateModelCode(generatedGenModel)
		}
	}

	def private generateGenModel(EPackage generatedPackage, String conceptName, URI codeGenerationTargetFolder) {
		GenModelFactory.eINSTANCE.createGenModel() => [
			complianceLevel = GENERATED_CODE_COMPLIANCE_LEVEL
			modelDirectory = codeGenerationTargetFolder.normalized.path
			canGenerate = true
			modelName = conceptName
			usedGenPackages += intermediateModelBaseGenModel.genPackages
			initialize(Collections.singleton(generatedPackage))
			genPackages.head => [
				prefix = conceptName.intermediateModelClassesPrefix
				basePackage = intermediateModelPackagePrefix
				adapterFactory = false
			]
		]
	}

	def private normalized(URI uriFromFsa) {
		// The EMF generator only supports generating inside an Eclipse
		// project. 
		if (uriFromFsa.isPlatformResource) {
			return uriFromFsa.deresolve(URI.createURI(VitruviusConstants.platformResourcePrefix))
		}
		throw new IllegalArgumentException('''Intermediate model code generation is only possible inside an Eclipse project.''')
	}

	def private generateModelCode(GenModel generatedGenModel) {
		GeneratorAdapterFactory.Descriptor.Registry.INSTANCE.addDescriptor(GenModelPackage.eNS_URI,
			GenModelGeneratorAdapterFactory.DESCRIPTOR)

		val generator = new Generator() => [
			input = generatedGenModel
		]
		val result = generator.generate(generatedGenModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor)
		if (result.severity != Diagnostic.OK) {
			throw new RuntimeException(result.message)
		}

		// TODO Workaround: When running in Eclipse, Xtext uses JDT to lookup
		// JVM types. However, JDT keeps internal caches of its Java Model
		// (such as the contents of source folders, packages, etc.), which
		// don't seem to get updated in time, causing Xtext to not be able to
		// find the JVM types for the generated intermediate model code when we
		// generate the reactions code for our Commonalities.
		// Manually closing and reopening the JavaProject flushes these
		// internal caches, forcing JDT to freshly check for and parse
		// generated packages and source files when Xtext's JdtTypeProvider
		// requests a Java type lookup by name.
		val javaProject = resourceSet.javaProject
		if (javaProject !== null) {
			javaProject.close
			javaProject.open(null)
		}
	}

	// See: org.eclipse.xtext.common.types.xtext.ui.XtextResourceSetBasedProjectProvider
	def private IJavaProject getJavaProject(ResourceSet resourceSet) {
		if (resourceSet instanceof XtextResourceSet) {
			val xtextResourceSet = resourceSet as XtextResourceSet
			val Object context = xtextResourceSet.getClasspathURIContext()
			if (context instanceof IJavaProject) {
				return context as IJavaProject
			}
		}
		return null
	}
}
