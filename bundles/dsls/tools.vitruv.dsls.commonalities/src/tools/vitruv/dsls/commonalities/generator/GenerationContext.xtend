package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.Domain
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EFeatureAdapter
import tools.vitruv.dsls.commonalities.language.elements.Metaclass
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.dsls.commonalities.language.elements.VitruvDomainAdapter
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class GenerationContext {
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var CommonalityFile commonalityFile
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var IFileSystemAccess2 fsa
	var Map<CommonalityFile, EClass> intermediateModelClassCache = new HashMap
	var Map<String, EPackage> intermediateModelPackageCache = new HashMap
	var Map<String, JvmGenericType> domainTypes = new HashMap
	var Map<String, JvmGenericType> domainProviderTypes = new HashMap

	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var Iterable<String> generatedConcepts = Collections.emptyList
	
	def package getGeneratedIntermediateModelClass(CommonalityFile commonalityFile) {
		intermediateModelClassCache.computeIfAbsent(commonalityFile, [
			commonalityFile.concept.generatedIntermediateModelPackage.EClassifiers.findFirst [
				name == commonalityFile.intermediateModelClass.simpleName
			] as EClass
		])
	}
	
	def package getGeneratedIntermediateModelClass(Commonality commonality) {
		commonality.containingCommonalityFile.generatedIntermediateModelClass
	}

	def package getChangeClass(CommonalityFile commonalityFile) {
		commonalityFile.generatedIntermediateModelClass
	}

	def package getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.findChangeClass
	}

	def package getChangeClass(Metaclass metaclass) {
		metaclass.findChangeClass
	}

	def private dispatch findChangeClass(Commonality commonality) {
		commonality.containingCommonalityFile.changeClass
	}

	def private dispatch findChangeClass(EClassAdapter eClassAdapter) {
		eClassAdapter.wrapped
	}

	def private dispatch findChangeClass(ResourceMetaclass resourceMetaclass) {
		ResourcesPackage.eINSTANCE.intermediateResourceBridge
	}

	def package getGeneratedIntermediateModelPackage(Concept concept) {
		concept.name.generatedIntermediateModelPackage
	}

	def package getConceptDomainType(CommonalityFile commonalityFile) {
		domainTypes.computeIfAbsent(commonalityFile.concept.name, [ conceptName |
			findDomainGeneratedType(conceptName.conceptDomainClass.qualifiedName)
		])
	}

	def package getConceptDomainProviderType(CommonalityFile commonalityFile) {
		domainProviderTypes.computeIfAbsent(commonalityFile.concept.name, [ conceptName |
			findDomainGeneratedType(conceptName.conceptDomainProvider.qualifiedName)
		])
	}

	def private findDomainGeneratedType(String name) {
		val domainTypeResource = commonalityFile.eResource.resourceSet.getResource(name.domainTypeUri, false)
		val result = domainTypeResource.contents.head
		if (result instanceof JvmGenericType) {
			return result
		}
		throw new IllegalStateException('''Could not find the generated JVM type “«name»”!''')
	}

	def package getCommonality() {
		commonalityFile.commonality
	}

	def package getGeneratedIntermediateModelPackage(String conceptName) {
		intermediateModelPackageCache.computeIfAbsent(conceptName, [
			resourceSet.getResource(conceptName.intermediateModelOutputUri, false).contents.head as EPackage
		])
	}

	def private ResourceSet getResourceSet() {
		commonalityFile.eResource.resourceSet
	}

	def package reportGeneratedConcept(String conceptName, EPackage conceptModel) {
		intermediateModelPackageCache.put(conceptName, conceptModel)
	}

	def package getConceptDomain(CommonalityFile commonalityFile) {
	}

	def package getIntermediateModelOutputUri(String conceptName) {
		fsa.getURI(conceptName + MODEL_OUTPUT_FILE_EXTENSION)
	}
	
	def package dispatch EStructuralFeature getEFeatureToReference(CommonalityAttribute attribute) {
		commonalityFile.generatedIntermediateModelClass.getEStructuralFeature(attribute.name)
	}

	// TODO reconsider when referencing of other commonalities is supported
	def package dispatch EStructuralFeature getEFeatureToReference(EFeatureAdapter adapter) {
		adapter.wrapped
	}

	def package dispatch EStructuralFeature getEFeatureToReference(ParticipationAttribute participationAttribute) {
		participationAttribute.attribute.EFeatureToReference
	}
	
	def package getVitruvDomain(Domain domain) {
		domain.findVitruvDomain
	}

	def private dispatch findVitruvDomain(VitruvDomainAdapter adapter) {
		adapter.wrapped
	}

	def private dispatch findVitruvDomain(Concept concept) {
		concept.vitruvDomain
	}
	
	def package dispatch EStructuralFeature getEFeatureToReference(CommonalityReference reference) {
		commonalityFile.generatedIntermediateModelClass.getEStructuralFeature(reference.name)
	}
	
	def package getVitruvDomain(Concept concept) {
		val ePackage = concept.name.generatedIntermediateModelPackage
		checkState(ePackage !== null, '''No ePackage was registered for the concept “«concept.name»”!''')
		new ConceptDomain(concept.name, ePackage)
	}
}
