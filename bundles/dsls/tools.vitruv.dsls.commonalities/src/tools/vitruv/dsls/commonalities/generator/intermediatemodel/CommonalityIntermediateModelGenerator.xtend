package tools.vitruv.dsls.commonalities.generator.intermediatemodel

import org.eclipse.xtext.generator.IGenerator2
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.emf.ecore.EcoreFactory
import tools.vitruv.dsls.commonalities.generator.GeneratorException
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.CommonalityFile
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.Commonality
import tools.vitruv.dsls.commonalities.commonalitiesLanguage.Attribute
import static extension tools.vitruv.dsls.commonalities.modelextension.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.util.With.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import java.util.Collections

class CommonalityIntermediateModelGenerator implements IGenerator2, IGenerator {

	override afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// nothing to do (for now)
	}

	override beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		// nothing to do (for now)
	}

	override doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context) {
		if (input.contents.length == 0) {
			throw new GeneratorException('Input resource is empty.')
		}
		if (input.contents.length > 1) {
			throw new GeneratorException('''The input resource may only contain one element (found «input.contents.length»)''')
		}
		val inputObject = input.contents.get(0)
		if (!(inputObject instanceof CommonalityFile)) {
			throw new GeneratorException('''The input resource does not contain a Commonality file (but a «inputObject.class.simpleName»''')
		}
		val commonalityFile = (inputObject as CommonalityFile)
		val outputUri = fsa.getURI(commonalityFile.commonality.name + ".ecore")
		
		newEcoreResource(outputUri).with [
			contents += generateCommonalityEPackage(commonalityFile.commonality)
			save(Collections.emptyMap)
		]
	}

	def private newEcoreResource(URI destination) {
		val resultResourceSet = new ResourceSetImpl
		resultResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put('ecore', new XMLResourceFactoryImpl)
		resultResourceSet.createResource(destination)
	}

	override doGenerate(Resource input, IFileSystemAccess fsa) {
		println()
	}

	def private generateCommonalityEPackage(Commonality inputCommonality) {
		EcoreFactory.eINSTANCE.createEPackage.with [
			name = inputCommonality.name
			EClassifiers += EcoreFactory.eINSTANCE.createEClass.with [
				name = inputCommonality.name
				EStructuralFeatures += inputCommonality.attributes.map[generateEAttribute]
			]
		]
	}

	def private generateEAttribute(Attribute commonalityAttribute) {
		EcoreFactory.eINSTANCE.createEAttribute.with [
			name = commonalityAttribute.name
			EType = commonalityAttribute.type
		]
	}
}
