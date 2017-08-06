package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import java.util.Collections
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.generator.Generator
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.common.util.URI

class CommonalityIntermediateModelCodeGenerator extends CommonalityFileGenerator {
	static val GENERATED_CODE_COMPLIANCE_LEVEL = GenJDKLevel.JDK80_LITERAL
	// as list of path segments, relative to the generated ecore file
	static val GENERATED_CODE_FOLDER = #["model-gen"]
	
	override generate() {
		val generatedPackage = commonality.generatedIntermediateModelClass.EPackage
		val generatedCodeDirectory = generatedPackage.eResource.URI.trimSegments(1).appendSegments(GENERATED_CODE_FOLDER)
		
		val generatedGenModel = generateGenModel(generatedPackage, generatedCodeDirectory)
		generateModelCode(generatedGenModel)
	}
	
	def private generateGenModel(EPackage generatedPackage, URI codeGenerationTargetFolder) {
		GenModelFactory.eINSTANCE.createGenModel() => [
			complianceLevel = GENERATED_CODE_COMPLIANCE_LEVEL
			modelDirectory = codeGenerationTargetFolder.toGeneratorUri().path()
			foreignModel += generatedPackage.eResource.URI.lastSegment
			modelName = commonality.name
			canGenerate = true
			initialize(Collections.singleton(generatedPackage))
			genPackages.get(0) => [
				prefix = commonality.name
				adapterFactory = false
			]
		]
	}
	
	def private toGeneratorUri(URI uriFromFsa) {
		// we currently only know how to convert platform:/resource URIs. If you
		// ever see another in the wild (which will break the generation),
		// use the debugger and learn how to transform it!
		if (uriFromFsa.isPlatformResource) {
			// does anybody know any meaningful constant to remove this
			// hardcoded string?
			return uriFromFsa.deresolve(URI.createURI("platform:/resource/"))
		}
		return uriFromFsa
	}

	def private generateModelCode(GenModel generatedGenModel) {
		// Globally register the default generator adapter factory for GenModel
		// elements (only needed in stand-alone).
		//
		GeneratorAdapterFactory.Descriptor.Registry.INSTANCE.addDescriptor(GenModelPackage.eNS_URI,
			GenModelGeneratorAdapterFactory.DESCRIPTOR)

		// Create the generator and set the model-level input object.
		//
		val generator = new Generator() => [
			input = generatedGenModel
		]
		// Generator model code.
		//
		val result = generator.generate(generatedGenModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor)
		println(result)
	}
	
}