package tools.vitruv.dsls.commonalities.generator

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.CommonalityFile

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class IntermediateModelGenerator extends SubGenerator {

	static val NS_URI_PREFIX = URI.createURI('http://vitruv.tools/commonalities')

	// TODO verify that this caching heuristic actually produces the desired results
	// see https://github.com/eclipse/xtext-core/issues/413
	static var int lastSeenResourceSetHash
	var List<Resource> outputResources = Collections.emptyList

	override beforeGenerate() {
		val resourceSet = commonalityFile.eResource.resourceSet

		if (resourceSet.hashCode != lastSeenResourceSetHash) {
			val conceptToCommonalityFile = resourceSet.resources
				.map [containedCommonalityFile]
				.filterNull
				.groupBy [concept.name]
				
			outputResources = new ArrayList(conceptToCommonalityFile.size)
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.computeIfAbsent('ecore', [new XMLResourceFactoryImpl])

			conceptToCommonalityFile.entrySet.forEach [
				val concept = key
				val commonalityFiles = value

				val generatedPackage = generateCommonalityEPackage(concept, commonalityFiles, resourceSet)
				reportGeneratedConcept(concept, generatedPackage)
			]
			
			generatedConcepts = new HashSet(conceptToCommonalityFile.keySet)

			lastSeenResourceSetHash = resourceSet.hashCode
		}
	}

	override generate() {
		outputResources.forEach[save(Collections.emptyMap)]
	}

	def private generateCommonalityEPackage(String conceptName, Iterable<CommonalityFile> commonalityFiles,
		ResourceSet resourceSet) {
		val conceptIntermediateModelOutputUri = getIntermediateModelOutputUri(conceptName)
		val outputResource = resourceSet.getResource(conceptIntermediateModelOutputUri, false) ?:
			resourceSet.createResource(conceptIntermediateModelOutputUri)

		val generatedPackage = new EPackageGenerator(conceptName, commonalityFiles, generationContext).generateEPackage()
		outputResource.contents += generatedPackage
		outputResources += outputResource
		return generatedPackage
	}

	private static class EPackageGenerator {
		val EPackage generatedEPackage = EcoreFactory.eINSTANCE.createEPackage
		val Iterable<CommonalityFile> commonalityFiles
		val String conceptName
		val extension GenerationContext generationContext

		private new(String conceptName, Iterable<CommonalityFile> commonalityFiles, GenerationContext generationContext) {
			this.conceptName = conceptName
			this.commonalityFiles = commonalityFiles
			this.generationContext = generationContext
		}

		def private generateEPackage() {
			val commonalityEClasses = commonalityFiles.map[generateEClass()]
			
			generatedEPackage => [
				nsURI = NS_URI_PREFIX.appendSegment(conceptName).toString
				nsPrefix = conceptName.conceptPackageSimpleName
				name = conceptName.conceptPackageSimpleName
				EClassifiers += commonalityEClasses
			]
		}

		def private generateEClass(CommonalityFile commonalityFile) {
			val commonality = commonalityFile.commonality
			EcoreFactory.eINSTANCE.createEClass => [
				name = commonalityFile.intermediateModelClassName
				instanceClassName = commonalityFile.intermediateModelInstanceClassName
				EStructuralFeatures += commonality.attributes.map [generateEAttribute()]
			]
		}

		def private generateEAttribute(AttributeDeclaration attributeDeclaration) {
			val typeClassifer = getOrCreateDataType(attributeDeclaration.type)
			EcoreFactory.eINSTANCE.createEAttribute => [
				name = attributeDeclaration.name
				EType = typeClassifer
			]
		}

		def private getOrCreateDataType(EDataType classifier) {
			#[generatedEPackage, EcorePackage.eINSTANCE].flatMap[EClassifiers].findFirst [
				instanceClass == classifier.instanceClass
			] ?: createNewEDataType(classifier)
		}

		def private createNewEDataType(EDataType classifier) {
			val newDataType = EcoreFactory.eINSTANCE.createEDataType => [
				name = classifier.instanceClass.name.replace('.', '_')
				instanceClass = classifier.instanceClass
			]
			generatedEPackage.EClassifiers += newDataType
			return newDataType
		}
	}
	
	def private static containedCommonalityFile(Resource resource) {
		resource.contents.filter(CommonalityFile).head
	}
}
