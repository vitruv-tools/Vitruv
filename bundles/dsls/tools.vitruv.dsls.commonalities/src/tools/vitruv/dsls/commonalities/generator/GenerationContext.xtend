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

	// TODO verify that this caching heuristic actually produces the desired results
	// see https://github.com/eclipse/xtext-core/issues/413
	static var int lastSeenResourceSetHash
	static var Iterable<String> generatedConcepts = Collections.emptyList

	var isNewResourceSet = false
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var CommonalityFile commonalityFile
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var IFileSystemAccess2 fsa
	// TODO cache for complete ResourceSet (but: how to known when to cleanup)? Or don't cache at all?
	var Map<Commonality, EClass> intermediateModelClassCache = new HashMap
	var Map<String, EPackage> intermediateModelPackageCache = new HashMap
	var Map<String, JvmGenericType> domainTypes = new HashMap
	var Map<String, JvmGenericType> domainProviderTypes = new HashMap

	def package updateResourceSetContext() {
		val resourceSetHashCode = resourceSet.hashCode
		isNewResourceSet = (resourceSetHashCode != lastSeenResourceSetHash);
		if (isNewResourceSet) {
			lastSeenResourceSetHash = resourceSetHashCode
			generatedConcepts = Collections.emptyList
		}
	}

	def package setGeneratedConcepts(Iterable<String> newGeneratedConcepts) {
		generatedConcepts = newGeneratedConcepts
	}

	def package getGeneratedConcepts() {
		generatedConcepts
	}

	def package isNewResourceSet() {
		isNewResourceSet
	}

	def package getIntermediateModelClass(Commonality commonality) {
		intermediateModelClassCache.computeIfAbsent(commonality, [
			commonality.concept.intermediateModelPackage.EClassifiers.findFirst [
				name == commonality.intermediateModelClassName.simpleName
			] as EClass
		])
	}

	def package getChangeClass(Commonality commonality) {
		commonality.intermediateModelClass
	}

	def package getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.findChangeClass
	}

	def package getChangeClass(Metaclass metaclass) {
		metaclass.findChangeClass
	}

	def private dispatch findChangeClass(Commonality commonality) {
		commonality.changeClass
	}

	def private dispatch findChangeClass(EClassAdapter eClassAdapter) {
		eClassAdapter.wrapped
	}

	def private dispatch findChangeClass(ResourceMetaclass resourceMetaclass) {
		ResourcesPackage.eINSTANCE.intermediateResourceBridge
	}

	def package getIntermediateModelPackage(Concept concept) {
		concept.name.intermediateModelPackage
	}

	def package getIntermediateModelPackage(String conceptName) {
		intermediateModelPackageCache.computeIfAbsent(conceptName, [
			resourceSet.getResource(conceptName.intermediateModelOutputUri, false).contents.head as EPackage
		])
	}

	def package getConceptDomainType(CommonalityFile commonalityFile) {
		domainTypes.computeIfAbsent(commonalityFile.concept.name, [ conceptName |
			findDomainJvmType(conceptName.conceptDomainClassName.qualifiedName)
		])
	}

	def package getConceptDomainProviderType(CommonalityFile commonalityFile) {
		domainProviderTypes.computeIfAbsent(commonalityFile.concept.name, [ conceptName |
			findDomainJvmType(conceptName.conceptDomainProviderClassName.qualifiedName)
		])
	}

	def private findDomainJvmType(String name) {
		val domainTypeResource = resourceSet.getResource(name.domainTypeUri, false)
		val result = domainTypeResource.contents.head
		if (result instanceof JvmGenericType) {
			return result
		}
		throw new IllegalStateException('''Could not find the generated JVM type “«name»”!''')
	}

	def package getCommonality() {
		commonalityFile.commonality
	}

	def package getConcept() {
		commonalityFile.concept
	}

	def package ResourceSet getResourceSet() {
		commonalityFile.eResource.resourceSet
	}

	def package reportGeneratedConcept(String conceptName, EPackage conceptModel) {
		intermediateModelPackageCache.put(conceptName, conceptModel)
	}

	def package getIntermediateModelOutputUri(String conceptName) {
		fsa.getURI(conceptName + MODEL_OUTPUT_FILE_EXTENSION)
	}

	def package dispatch EStructuralFeature getEFeatureToReference(CommonalityAttribute attribute) {
		attribute.containingCommonality.intermediateModelClass.getEStructuralFeature(attribute.name)
	}

	def package dispatch EStructuralFeature getEFeatureToReference(EFeatureAdapter adapter) {
		adapter.wrapped
	}

	def package dispatch EStructuralFeature getEFeatureToReference(ParticipationAttribute participationAttribute) {
		participationAttribute.attribute.EFeatureToReference
	}

	def package dispatch EStructuralFeature getEFeatureToReference(CommonalityReference reference) {
		reference.containingCommonality.intermediateModelClass.getEStructuralFeature(reference.name)
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

	def package getVitruvDomain(Concept concept) {
		concept.name.vitruvDomain
	}

	def package getVitruvDomain(String conceptName) {
		val ePackage = conceptName.intermediateModelPackage
		checkState(ePackage !== null, '''No ePackage was registered for the concept “«conceptName»”!''')
		new ConceptDomain(conceptName, ePackage)
	}
}
