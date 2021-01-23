package tools.vitruv.dsls.commonalities.generator

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.commonalities.generator.domain.ConceptDomain
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
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

import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import javax.inject.Inject
import java.util.Set

@GenerationScoped
class GenerationContext {
	static class Factory extends InjectingFactoryBase {
		def create(IFileSystemAccess2 fsa, CommonalityFile commonalityFile) {
			return new GenerationContext(fsa, commonalityFile).injectMembers
		}
	}

	// TODO verify that this caching heuristic actually produces the desired results
	// see https://github.com/eclipse/xtext-core/issues/413
	static var int lastSeenResourceSetHash
	static var Set<String> generatedConcepts = emptySet

	@Accessors(PUBLIC_GETTER)
	val IFileSystemAccess2 fsa
	@Accessors(PUBLIC_GETTER)
	val CommonalityFile commonalityFile
	@Accessors(PUBLIC_GETTER)
	val boolean isNewResourceSet
	@Accessors(PUBLIC_GETTER)
	@Inject CommonalitiesGenerationSettings settings

	// TODO Cache for complete ResourceSet (but: how to known when to cleanup)?
	val Map<String, ConceptDomain> intermediateDomains = new HashMap

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

	private def boolean updateResourceSetContext() {
		val resourceSetHashCode = resourceSet.hashCode
		val isNewResourceSet = (resourceSetHashCode != lastSeenResourceSetHash);
		if (isNewResourceSet) {
			lastSeenResourceSetHash = resourceSetHashCode
			generatedConcepts = emptySet
		}
		return isNewResourceSet
	}

	def getCommonality() {
		commonalityFile.commonality
	}

	def getConcept() {
		commonalityFile.concept
	}

	def ResourceSet getResourceSet() {
		commonalityFile.eResource.resourceSet
	}

	def setGeneratedConcepts(Set<String> newGeneratedConcepts) {
		generatedConcepts = newGeneratedConcepts
	}

	def getGeneratedConcepts() {
		generatedConcepts
	}

	def reportGeneratedIntermediateMetamodel(String conceptName, EPackage intermediateMetamodelPackage) {
		intermediateDomains.put(conceptName, new ConceptDomain(conceptName, intermediateMetamodelPackage))
	}

	def getIntermediateMetamodelPackage(Concept concept) {
		concept.name.intermediateMetamodelPackage
	}

	def getIntermediateMetamodelPackage(String conceptName) {
		conceptName.vitruvDomain.metamodelRootPackage
	}
	
	def getIntermediateMetamodelUri(String conceptName) {
		fsa.getURI(conceptName.intermediateMetamodelFilePath)
	}

	def getIntermediateMetamodelRootClass(Concept concept) {
		return concept.name.intermediateMetamodelRootClass
	}

	def getIntermediateMetamodelRootClass(String conceptName) {
		conceptName.intermediateMetamodelPackage.getEClassifier(
			conceptName.intermediateMetamodelRootClassName.simpleName
		) as EClass
	}

	def getIntermediateMetamodelClass(Commonality commonality) {
		commonality.concept.intermediateMetamodelPackage.getEClassifier(
			commonality.intermediateMetamodelClassName.simpleName
		) as EClass
	}

	def dispatch EClass getChangeClass(Commonality commonality) {
		commonality.intermediateMetamodelClass
	}

	def dispatch EClass getChangeClass(ParticipationClass participationClass) {
		participationClass.superMetaclass.changeClass
	}

	def dispatch EClass getChangeClass(EClassAdapter eClassAdapter) {
		eClassAdapter.wrapped
	}

	def dispatch EClass getChangeClass(ResourceMetaclass resourceMetaclass) {
		ResourcesPackage.eINSTANCE.intermediateResourceBridge
	}

	def dispatch EClass getChangeClass(ClassLike classLike) {
		throw new IllegalStateException("Unhandled ClassLike: " + classLike.class.name)
	}

	def dispatch EStructuralFeature getCorrespondingEFeature(CommonalityAttribute attribute) {
		attribute.declaringCommonality.intermediateMetamodelClass.getEStructuralFeature(attribute.name)
	}

	def dispatch EStructuralFeature getCorrespondingEFeature(EFeatureAdapter adapter) {
		adapter.wrapped
	}

	def dispatch EStructuralFeature getCorrespondingEFeature(ParticipationAttribute participationAttribute) {
		participationAttribute.attribute.correspondingEFeature
	}

	def dispatch EStructuralFeature getCorrespondingEFeature(CommonalityReference reference) {
		reference.declaringCommonality.intermediateMetamodelClass.getEStructuralFeature(reference.name)
	}

	def dispatch EStructuralFeature getCorrespondingEFeature(Attribute attribute) {
		throw new IllegalStateException("Unhandled Attribute type: " + attribute.class.name)
	}

	def EAttribute getCorrespondingEAttribute(Attribute attribute) {
		attribute.correspondingEFeature as EAttribute
	}

	def EReference getCorrespondingEReference(Attribute attribute) {
		attribute.correspondingEFeature as EReference
	}

	def getCommonalityEFeature(CommonalityAttributeMapping mapping) {
		mapping.declaringAttribute.correspondingEFeature
	}

	// Null if the mapping does not specify any participation attribute
	def getParticipationEFeature(CommonalityAttributeMapping mapping) {
		mapping.participationAttribute?.correspondingEFeature
	}

	def getVitruvDomain(Domain domain) {
		domain.findVitruvDomain
	}

	private def dispatch findVitruvDomain(VitruvDomainAdapter adapter) {
		adapter.wrapped
	}

	private def dispatch findVitruvDomain(Concept concept) {
		concept.vitruvDomain
	}

	def getVitruvDomain(Concept concept) {
		concept.name.vitruvDomain
	}

	def getVitruvDomain(String cName) {
		intermediateDomains.computeIfAbsent(cName) [ conceptName | 
			val ePackage = resourceSet.getResource(conceptName.intermediateMetamodelUri, false).contents.head as EPackage
			checkState(ePackage !== null, '''No ePackage was registered for the concept ‹«conceptName»›!''')
			new ConceptDomain(conceptName, ePackage)
		]
	}
}
