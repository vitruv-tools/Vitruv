package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EcoreFactory
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.emf.ecore.EDataType

package class CommonalityIntermediateModelGenerator extends CommonalityFileGenerator {

	// it does not make *any* sense that all these URIs have the HTTP scheme!
	static val NS_URI_PREFIX = URI.createURI('http://vitruv.tools/commonalities')

	override generate() {
		val outputUri = fsa.getURI(commonality.name + ".ecore")

		newEcoreResource(outputUri) => [
			contents += generateCommonalityEPackage()
			save(Collections.emptyMap)
		]
	}

	def private newEcoreResource(URI destination) {
		val resultResourceSet = new ResourceSetImpl
		resultResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put('ecore', new XMLResourceFactoryImpl)
		resultResourceSet.createResource(destination)
	}

	def private generateCommonalityEPackage() {
		new EPackageGenerator(this).generateEPackage()
	}

	private static class EPackageGenerator {
		val extension CommonalityIntermediateModelGenerator parentGenerator
		val extension CommonalitiesLanguageGenerationContext generationContext
		val EPackage generatedEPackage = EcoreFactory.eINSTANCE.createEPackage

		private new(CommonalityIntermediateModelGenerator parentGenerator) {
			this.parentGenerator = parentGenerator
			this.generationContext = parentGenerator.generationContext
		}

		def private generateEPackage() {
			val commonalityEClass = generateEClass()
			commonality.associateWith(commonalityEClass)

			generatedEPackage => [
				nsURI = NS_URI_PREFIX.appendSegment(commonalityFile.concept.name).appendSegment(commonality.name).
					toString
				nsPrefix = commonality.name
				name = commonality.name
				EClassifiers += commonalityEClass
			]
			registerEPackage(commonalityFile.concept.name, generatedEPackage)
			return generatedEPackage
		}

		def private generateEClass() {
			EcoreFactory.eINSTANCE.createEClass => [
				name = commonality.name
				EStructuralFeatures += commonality.attributes.map [generateEAttribute]
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
			#[generatedEPackage, EcorePackage.eINSTANCE]
				.flatMap [EClassifiers]
				.findFirst[instanceClass == classifier.instanceClass]
				?: createNewEDataType(classifier)
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
}
