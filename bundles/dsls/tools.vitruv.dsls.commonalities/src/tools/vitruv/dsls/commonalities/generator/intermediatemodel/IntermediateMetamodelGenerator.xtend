package tools.vitruv.dsls.commonalities.generator.intermediatemodel

import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.function.Consumer
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import tools.vitruv.dsls.common.ClassNameGenerator
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import tools.vitruv.dsls.commonalities.generator.SubGenerator
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.api.generator.ReferenceClassNameAdapter
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

import static org.eclipse.emf.ecore.ETypedElement.UNBOUNDED_MULTIPLICITY

import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import javax.inject.Inject
import tools.vitruv.dsls.commonalities.language.elements.LeastSpecificType

@GenerationScoped
class IntermediateMetamodelGenerator implements SubGenerator {
	static val Logger log = Logger.getLogger(IntermediateMetamodelGenerator)
	static val NS_URI_PREFIX = URI.createURI('http://vitruv.tools/commonalities')

	@Inject extension GenerationContext generationContext

	override beforeGenerate() {
		if (isNewResourceSet) {
			val resourceSet = resourceSet
			val conceptToCommonalityFiles = resourceSet.resources
				.map [optionalContainedCommonalityFile]
				.filterNull
				.groupBy [concept.name]

			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.computeIfAbsent('ecore', [
				new XMLResourceFactoryImpl
			])

			conceptToCommonalityFiles.forEach [ concept, commonalityFiles |
				log.debug('''Generating intermediate metamodel for concept ‹«concept»› and commonalities «
					commonalityFiles.join(', ') ['''‹«commonality.name»›''']»''')

				val packageGenerator = generateCommonalityEPackage(concept, commonalityFiles, resourceSet)
				reportGeneratedIntermediateMetamodel(concept, packageGenerator.generatedEPackage)
				packageGenerator.link()
			]

			generatedConcepts = new HashSet(conceptToCommonalityFiles.keySet)
		}
	}

	override generate() {
		if (isNewResourceSet && settings.createEcoreFiles) {
			for(conceptName : generatedConcepts) {
				val intermediateModelUri = conceptName.intermediateMetamodelUri
				log.debug('''Saving generated intermediate metamodel at: «intermediateModelUri»''')
				resourceSet.getResource(intermediateModelUri, false).save(emptyMap)
			}
		}
	}
	
	private def generateCommonalityEPackage(String conceptName, Iterable<CommonalityFile> commonalityFiles,
		ResourceSet resourceSet) {
		val outputUri = conceptName.intermediateMetamodelUri
		val outputResource = resourceSet.getResource(outputUri, false) ?: resourceSet.createResource(outputUri)
		// Delete any previously existing intermediate metamodel:
		outputResource.contents.clear()

		val packageGenerator = new EPackageGenerator(conceptName, commonalityFiles, generationContext)
		val generatedPackage = packageGenerator.generateEPackage()
		outputResource.contents += generatedPackage
		return packageGenerator
	}

	@FinalFieldsConstructor
	private static class EPackageGenerator {
		val String conceptName
		val Iterable<CommonalityFile> commonalityFiles
		val extension GenerationContext
		val EPackage generatedEPackage = EcoreFactory.eINSTANCE.createEPackage
		val List<Runnable> linkCallbacks = new ArrayList

		private def <T> whenLinking(T object, Consumer<T> linker) {
			linkCallbacks.add [linker.accept(object)]
			return object
		}

		def link() {
			linkCallbacks.forEach[run]
		}

		private def generateEPackage() {
			generatedEPackage => [
				nsURI = NS_URI_PREFIX.appendSegment(conceptName).toString
				nsPrefix = conceptName.intermediateMetamodelPackageSimpleName
				name = conceptName.intermediateMetamodelPackageSimpleName
				EClassifiers += generateRootClass()
				EClassifiers += commonalityFiles.map[commonality].map[generateEClass]
				EFactoryInstance.referencedAs(conceptName.intermediateMetamodelFactoryClassName)
			]
		}

		private def generateEClass(Commonality commonality) {
			EcoreFactory.eINSTANCE.createEClass => [
				name = commonality.intermediateMetamodelClassName.simpleName
				ESuperTypes += IntermediateModelBasePackage.eINSTANCE.intermediate
				EStructuralFeatures += commonality.attributes.map[generateEFeature()]
				EStructuralFeatures += commonality.references.map[generateEReference()]
				referencedAs(commonality.intermediateMetamodelClassName)
			]
		}

		private def generateEFeature(CommonalityAttribute attribute) {
			switch attributeType : attribute.type {
				EDataTypeAdapter:
					EcoreFactory.eINSTANCE.createEAttribute => [
						EType = attributeType.getOrGenerateEDataType()
					]
				EClassAdapter:
					EcoreFactory.eINSTANCE.createEReference => [
						EType = attributeType.wrapped
					]
				LeastSpecificType:
					// type inference failed, but we still create a reference 
					// so generation can continue
					EcoreFactory.eINSTANCE.createEReference => [
						EType = EcorePackage.Literals.EOBJECT
					]
				default:
					throw new IllegalStateException('''The Attribute declaration ‹«attribute»› has the type «
						»‹«attributeType»› which does not correspond to an EClassifier!''')
			} => [
				name = attribute.name
				upperBound = if (attribute.isMultiValued) UNBOUNDED_MULTIPLICITY else 1
			]
		}

		private def generateEReference(CommonalityReference reference) {
			(EcoreFactory.eINSTANCE.createEReference => [
				name = reference.name
				upperBound = if (reference.isMultiValued) UNBOUNDED_MULTIPLICITY else 1
				containment = true
			]).whenLinking [
				EType = reference.referenceType.intermediateMetamodelClass
			]
		}

		private def getOrGenerateEDataType(EDataTypeAdapter dataTypeAdapter) {
			#[generatedEPackage, EcorePackage.eINSTANCE].flatMap[EClassifiers].findFirst [
				instanceClass == dataTypeAdapter.wrapped.instanceClass
			] ?: generateEDataType(dataTypeAdapter.wrapped)
		}

		private def generateEDataType(EDataType classifier) {
			val newDataType = EcoreFactory.eINSTANCE.createEDataType => [
				name = classifier.instanceClass.name.replace('.', '_')
				instanceClass = classifier.instanceClass
			]
			generatedEPackage.EClassifiers += newDataType
			return newDataType
		}

		private def generateRootClass() {
			EcoreFactory.eINSTANCE.createEClass => [
				name = conceptName.intermediateMetamodelRootClassName.simpleName
				ESuperTypes += IntermediateModelBasePackage.eINSTANCE.root
				referencedAs(conceptName.intermediateMetamodelRootClassName)
			]
		}
	}

	private static def referencedAs(EObject element, ClassNameGenerator className) {
		element.eAdapters += new ReferenceClassNameAdapter(className.qualifiedName)
	}
}
