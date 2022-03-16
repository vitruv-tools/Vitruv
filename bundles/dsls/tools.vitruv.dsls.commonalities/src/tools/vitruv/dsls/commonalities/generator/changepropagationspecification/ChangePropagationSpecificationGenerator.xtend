package tools.vitruv.dsls.commonalities.generator.changepropagationspecification

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
import java.util.Set
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.xbase.compiler.CompilationTemplateAdapter
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import tools.vitruv.dsls.commonalities.generator.SubGenerator
import tools.vitruv.framework.propagation.impl.CompositeChangePropagationSpecification

import static extension tools.vitruv.dsls.commonalities.generator.changepropagationspecification.ChangePropagationSpecificationConstants.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import java.util.HashSet
import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import tools.vitruv.dsls.reactions.language.toplevelelements.TopLevelElementsFactory
import tools.vitruv.dsls.reactions.codegen.helper.ClassNamesGenerators
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import tools.vitruv.framework.domains.VitruvDomainProvider
import org.eclipse.emf.common.util.URI

class ChangePropagationSpecificationGenerator implements SubGenerator {
	@Inject extension GenerationContext generationContext
	@Inject JvmModelGenerator delegate
	@Inject Provider<XtextResource> resourceProvider
	@Inject JvmTypeReferenceBuilder.Factory typeReferenceFactory

	override beforeGenerate() {
		if (isNewResourceSet) {
			resourceSet.resources += domainPairsForChangePropagation.map [
				getChangePropagationSpecificationName(key, value).newResource
			]
			resourceSet.resources += newResource(changePropagationSpecificationProviderName)
		}
	}

	override generate() {
		if (isNewResourceSet) {
			val changePropagationSpecifications = domainPairsForChangePropagation.map [ fromToDomain |
				generateChangePropagationSpecification(fromToDomain.key, fromToDomain.value)
			]
			changePropagationSpecifications.forEach[generateType()]
			changePropagationSpecifications.generateChangePropagationSpecificationsProvider().generateType()
		}
	}

	private def JvmGenericType generateChangePropagationSpecification(VitruvDomain fromDomain, VitruvDomain toDomain) {
		val extension typeReferenceBuilder = typeReferenceFactory.create(resourceSet)
		val dummyReactionsSegments = getReactionsSegmentNames(fromDomain, toDomain).map [ segmentName |
			TopLevelElementsFactory.eINSTANCE.createReactionsSegment() => [name = segmentName]
		]
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = getChangePropagationSpecificationName(fromDomain, toDomain)
			packageName = changePropagationSpecificationPackageName
			superTypes += CompositeChangePropagationSpecification.typeRef
			visibility = JvmVisibility.PUBLIC
			members += Arrays.asList(
				TypesFactory.eINSTANCE.createJvmConstructor => [
					visibility = JvmVisibility.PUBLIC
					body = '''
					super(new «fromDomain.domainProvider.canonicalNameForReference»().getDomain(),
						new «toDomain.domainProvider.canonicalNameForReference»().getDomain());
					«FOR reactionsSegment : dummyReactionsSegments»addChangeMainprocessor(new «
							ClassNamesGenerators.getChangePropagationSpecificationClassNameGenerator(reactionsSegment).qualifiedName»());
					«ENDFOR»'''
				]
			)
		]
	}

	private def JvmGenericType generateChangePropagationSpecificationsProvider(
		Iterable<JvmGenericType> changePropagationSpecifications) {
		val extension typeReferenceBuilder = typeReferenceFactory.create(resourceSet)
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = changePropagationSpecificationProviderName
			packageName = changePropagationSpecificationPackageName
			visibility = JvmVisibility.PUBLIC
			final = true
			members += Arrays.asList(
				TypesFactory.eINSTANCE.createJvmConstructor => [
					visibility = JvmVisibility.PRIVATE
				],
				TypesFactory.eINSTANCE.createJvmField => [
					visibility = JvmVisibility.PRIVATE
					static = true
					type = Set.typeRef(ChangePropagationSpecification.typeRef)
					simpleName = "changePropagationSpecifications"
					body = '''
					«Set».of(«FOR changePropagationSpecification : changePropagationSpecifications SEPARATOR ", "»
						new «changePropagationSpecification.qualifiedName»()«ENDFOR»)'''
				],
				TypesFactory.eINSTANCE.createJvmOperation => [
					visibility = JvmVisibility.PUBLIC
					static = true
					returnType = Set.typeRef(ChangePropagationSpecification.typeRef)
					simpleName = "getChangePropagationSpecifications"
					body = '''return new «HashSet»(changePropagationSpecifications);'''
				]
			)
		]
	}

	private def generateType(JvmDeclaredType type) {
		val typeResource = resourceSet.getResource(type.simpleName.changePropagationSpecificationUri, false)
		typeResource.contents += type
		delegate.doGenerate(typeResource, fsa)
	}

	private def Set<Pair<? extends VitruvDomain, ? extends VitruvDomain>> getDomainPairsForChangePropagation() {
		commonalityFiles.flatMap [ file |
			file.commonality.participations.flatMap [
				val firstPackage = domain.vitruvDomain
				val secondPackage = file.concept.vitruvDomain
				#[firstPackage -> secondPackage, secondPackage -> firstPackage]
			]
		].toSet
	}

	private def Set<String> getReactionsSegmentNames(VitruvDomain fromDomain, VitruvDomain toDomain) {
		commonalityFiles.filter [ file |
			val commonalityDomain = file.concept.vitruvDomain
			return fromDomain == commonalityDomain || toDomain == commonalityDomain
		].flatMap [ file |
			file.commonality.participations.map [
				val participationDomain = domain.vitruvDomain
				return switch (participationDomain) {
					case fromDomain:
						getReactionsSegmentFromParticipationToCommonalityName(file.commonality, it)
					case toDomain:
						getReactionsSegmentFromCommonalityToParticipationName(file.commonality, it)
					default:
						null
				}
			].filterNull
		].toSet
	}

	private def getCommonalityFiles() {
		resourceSet.resources.map[optionalContainedCommonalityFile].filterNull
	}
	
	private def VitruvDomainProvider<?> getDomainProvider(VitruvDomain domain) {
		if (VitruvDomainProviderRegistry.hasDomainProvider(domain.name)) {
			VitruvDomainProviderRegistry.getDomainProvider(domain.name)
		} else {
			domain.name.vitruvDomain.provider
		}
	}
	
	private static def setBody(JvmMember member, StringConcatenationClient body) {
		member.eAdapters += new CompilationTemplateAdapter() => [compilationTemplate = body]
	}

	private def newResource(String changePropagationSpecificationName) {
		resourceProvider.get() => [
			URI = changePropagationSpecificationName.changePropagationSpecificationUri
		]
	}

	private def getChangePropagationSpecificationUri(String changePropagationSpecificationName) {
		// When calling this in beforeGenerate, the generated files folder does not exist yet,
		// which leads to the URI not having a trailing slash in comparison to the URI generated
		// when calling this in the generate method. In consequence, calling this method with the
		// same argument leads to different URIs, which do not resolve to the same resource in the
		// same resource set. To ensure consistent URIs, we add the trailing slash if missing.
		fsa.getURI(".").ensureHasTrailingSlash().appendSegment(
			changePropagationSpecificationPackageName + "." + changePropagationSpecificationName + ".java"
		)
	}
	
	private def ensureHasTrailingSlash(URI uri) {
		val uriString = uri.toString
		if (!uriString.endsWith("/")) {
			URI.createURI(uriString + "/")
		} else {
			uri
		}
	}

}
