package tools.vitruv.dsls.commonalities.generator.domain

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
import org.apache.log4j.Logger
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
import tools.vitruv.dsls.commonalities.generator.SubGenerator
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.framework.domains.VitruvDomainProvider

import static extension tools.vitruv.dsls.commonalities.generator.domain.ConceptDomainConstants.*
import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.generator.GenerationContext

@GenerationScoped
class ConceptDomainGenerator implements SubGenerator {
	static val Logger log = Logger.getLogger(ConceptDomainGenerator)

	@Inject extension GenerationContext
	@Inject JvmModelGenerator delegate
	@Inject JvmTypeReferenceBuilder.Factory typeReferenceFactory
	@Inject Provider<XtextResource> resourceProvider

	override beforeGenerate() {
		if (!isNewResourceSet) return;
		resourceSet.resources += generatedConcepts.flatMap [
			#[
				newResource(conceptDomainClassName.qualifiedName),
				newResource(conceptDomainProviderClassName.qualifiedName)
			]
		]
	}

	override generate() {
		if (!isNewResourceSet) return;
		val typeReferenceBuilder = typeReferenceFactory.create(resourceSet)
		generatedConcepts.flatMap [ concept |
			log.debug('''Generating domain and domain provider for concept '«concept»'.''')
			val domainType = concept.createDomain(typeReferenceBuilder)
			val domainProviderType = concept.createDomainProvider(typeReferenceBuilder, domainType)
			return #[domainType, domainProviderType]
		].forEach[generateType()]
	}

	private def createDomain(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder) {
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = conceptName.conceptDomainClassName.simpleName
			packageName = conceptName.conceptDomainClassName.packageName
			superTypes += IntermediateVitruvDomain.typeRef
			visibility = JvmVisibility.PUBLIC
			members += Arrays.asList(
				TypesFactory.eINSTANCE.createJvmConstructor => [
					visibility = JvmVisibility.PUBLIC
					body = '''
					super("«conceptName.conceptDomainName»",
						«'''«conceptName.intermediateMetamodelPackageClassName.simpleName»'''».eINSTANCE,
						"«conceptName.intermediateModelFileExtension»");'''
				]
			)
		]
	}

	private def createDomainProvider(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder,
		JvmGenericType domainType) {
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = conceptName.conceptDomainProviderClassName.simpleName
			packageName = conceptName.conceptDomainProviderClassName.packageName
			superTypes += VitruvDomainProvider.typeRef(domainType.typeRef)
			visibility = JvmVisibility.PUBLIC
			members += Arrays.asList(
				TypesFactory.eINSTANCE.createJvmField => [
					visibility = JvmVisibility.PRIVATE
					static = true
					type = domainType.typeRef
					simpleName = 'instance'
				],
				TypesFactory.eINSTANCE.createJvmOperation => [
					visibility = JvmVisibility.PUBLIC
					synchronized = true
					simpleName = 'getDomain'
					returnType = domainType.typeRef
					body = '''
						if (instance == null) {
							instance = new «domainType.typeRef»();
						}
						return instance;
					'''
				]
			)
		]
	}

	private def generateType(JvmDeclaredType type) {
		val typeResource = resourceSet.getResource(type.qualifiedName.domainTypeUri, false)
		typeResource.contents += type
		delegate.doGenerate(typeResource, fsa)
	}

	private static def setBody(JvmMember member, StringConcatenationClient body) {
		member.eAdapters += new CompilationTemplateAdapter() => [compilationTemplate = body]
	}

	private def newResource(String qualifiedClassName) {
		resourceProvider.get() => [
			URI = qualifiedClassName.domainTypeUri
		]
	}
}
