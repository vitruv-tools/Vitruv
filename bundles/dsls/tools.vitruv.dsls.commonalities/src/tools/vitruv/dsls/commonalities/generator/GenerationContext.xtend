package tools.vitruv.dsls.commonalities.generator

import java.util.Collections
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.Concept
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.commonalities.language.elements.ClassLike
import tools.vitruv.dsls.commonalities.language.elements.Domain
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EFeatureAdapter
import tools.vitruv.dsls.commonalities.language.elements.ResourceMetaclass
import tools.vitruv.dsls.commonalities.language.elements.VitruvDomainAdapter
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@GenerationScoped
package class GenerationContext {

	static class Factory extends InjectingFactoryBase {
		def create(IFileSystemAccess2 fsa, CommonalityFile commonalityFile) {
			return new GenerationContext(fsa, commonalityFile).injectMembers
		}
	}

	// TODO verify that this caching heuristic actually produces the desired results
	// see https://github.com/eclipse/xtext-core/issues/413
	static var int lastSeenResourceSetHash
	static var Iterable<String> generatedConcepts = Collections.emptyList

	@Accessors(PACKAGE_GETTER)
	val IFileSystemAccess2 fsa
	@Accessors(PACKAGE_GETTER)
	val CommonalityFile commonalityFile
	@Accessors(PACKAGE_GETTER)
	val boolean isNewResourceSet

	// TODO cache for complete ResourceSet (but: how to known when to cleanup)? Or don't cache at all?
	val Map<String, EClass> intermediateModelRootClassCache = new HashMap
	val Map<Commonality, EClass> intermediateModelClassCache = new HashMap
	val Map<String, EPackage> intermediateModelPackageCache = new HashMap
	val Map<String, JvmGenericType> domainTypes = new HashMap
	val Map<String, JvmGenericType> domainProviderTypes = new HashMap

	private new(IFileSystemAccess2 fsa, CommonalityFile commonalityFile) {
		checkNotNull(fsa, "fsa is null")
		checkNotNull(commonalityFile, "commonalityFile is null")
		this.fsa = fsa
		this.commonalityFile = commonalityFile
		this.isNewResourceSet = updateResourceSetContext()
	}

	// Dummy constructor for Guice
	package new() {
		this.fsa = null
		this.commonalityFile = null
		this.isNewResourceSet = false
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def private boolean updateResourceSetContext() {
		val resourceSetHashCode = resourceSet.hashCode
		val isNewResourceSet = (resourceSetHashCode != lastSeenResourceSetHash);
		if (isNewResourceSet) {
			lastSeenResourceSetHash = resourceSetHashCode
			generatedConcepts = Collections.emptyList
		}
		return isNewResourceSet
	}

	def package setGeneratedConcepts(Iterable<String> newGeneratedConcepts) {
		generatedConcepts = newGeneratedConcepts
	}

	def package getGeneratedConcepts() {
		generatedConcepts
	}

	def package getIntermediateModelRootClass(Concept concept) {
		return concept.name.intermediateModelRootClass
	}

	def package getIntermediateModelRootClass(String conceptName) {
		intermediateModelRootClassCache.computeIfAbsent(conceptName, [
			conceptName.intermediateModelPackage.EClassifiers.findFirst [
				name == conceptName.intermediateModelRootClassName.simpleName
			] as EClass
		])
	}

	def package getIntermediateModelClass(Commonality commonality) {
		intermediateModelClassCache.computeIfAbsent(commonality, [
			commonality.concept.intermediateModelPackage.EClassifiers.findFirst [
				name == commonality.intermediateModelClassName.simpleName
			] as EClass
		])
	}

	def package dispatch EClass getChangeClass(Commonality commonality) {
		commonality.intermediateModelClass
	}

	def package dispatch EClass getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}

	def package dispatch EClass getChangeClass(EClassAdapter eClassAdapter) {
		eClassAdapter.wrapped
	}

	def package dispatch EClass getChangeClass(ResourceMetaclass resourceMetaclass) {
		ResourcesPackage.eINSTANCE.intermediateResourceBridge
	}

	def package dispatch EClass getChangeClass(ClassLike classLike) {
		throw new IllegalStateException("Unhandled ClassLike: " + classLike.class.name)
	}

	def package getIntermediateModelPackage(Concept concept) {
		concept.name.intermediateModelPackage
	}

	def package getIntermediateModelPackage(String conceptName) {
		intermediateModelPackageCache.computeIfAbsent(conceptName, [
			resourceSet.getResource(conceptName.intermediateModelOutputUri, false).contents.head as EPackage
		])
	}

	def package getConceptDomainType(Concept concept) {
		domainTypes.computeIfAbsent(concept.name, [ conceptName |
			findDomainJvmType(conceptName.conceptDomainClassName.qualifiedName)
		])
	}

	def package getConceptDomainProviderType(Concept concept) {
		domainProviderTypes.computeIfAbsent(concept.name, [ conceptName |
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

	def package dispatch EStructuralFeature getCorrespondingEFeature(CommonalityAttribute attribute) {
		attribute.containingCommonality.intermediateModelClass.getEStructuralFeature(attribute.name)
	}

	def package dispatch EStructuralFeature getCorrespondingEFeature(EFeatureAdapter adapter) {
		adapter.wrapped
	}

	def package dispatch EStructuralFeature getCorrespondingEFeature(ParticipationAttribute participationAttribute) {
		participationAttribute.attribute.correspondingEFeature
	}

	def package dispatch EStructuralFeature getCorrespondingEFeature(CommonalityReference reference) {
		reference.containingCommonality.intermediateModelClass.getEStructuralFeature(reference.name)
	}

	def package dispatch EStructuralFeature getCorrespondingEFeature(Attribute attribute) {
		throw new IllegalStateException("Unhandled Attribute type: " + attribute.class.name)
	}

	def package EAttribute getCorrespondingEAttribute(Attribute attribute) {
		attribute.correspondingEFeature as EAttribute
	}

	def package EReference getCorrespondingEReference(Attribute attribute) {
		attribute.correspondingEFeature as EReference
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

	def package getCommonalityEFeature(CommonalityAttributeMapping mapping) {
		mapping.declaringAttribute.correspondingEFeature
	}

	// Null if the mapping does not specify any participation attribute
	def package getParticipationEFeature(CommonalityAttributeMapping mapping) {
		mapping.participationAttribute?.correspondingEFeature
	}
}
