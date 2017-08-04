package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EcoreFactory
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.CommonalityDeclaration

package class CommonalityIntermediateModelGenerator extends CommonalityFileGenerator {

	override generate() {
		val outputUri = fsa.getURI(commonalityFile.commonality.name + ".ecore")

		newEcoreResource(outputUri) => [
			contents += generateCommonalityEPackage(commonalityFile.commonality)
			save(Collections.emptyMap)
		]
	}

	def private newEcoreResource(URI destination) {
		val resultResourceSet = new ResourceSetImpl
		resultResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put('ecore', new XMLResourceFactoryImpl)
		resultResourceSet.createResource(destination)
	}

	def private generateCommonalityEPackage(CommonalityDeclaration inputCommonality) {
		val commonalityEClass = EcoreFactory.eINSTANCE.createEClass => [
			name = inputCommonality.name
			EStructuralFeatures += inputCommonality.attributes.map[generateEAttribute]
		]
		inputCommonality.associateWith(commonalityEClass)
		EcoreFactory.eINSTANCE.createEPackage => [
			name = inputCommonality.name
			EClassifiers += commonalityEClass
		]
	}

	def private generateEAttribute(AttributeDeclaration attributeDeclaration) {
		EcoreFactory.eINSTANCE.createEAttribute => [
			name = attributeDeclaration.name
			EType = attributeDeclaration.type
		]
	}
}
