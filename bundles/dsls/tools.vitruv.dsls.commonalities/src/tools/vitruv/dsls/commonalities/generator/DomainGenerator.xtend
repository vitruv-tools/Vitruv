package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
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
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class DomainGenerator extends SubGenerator {

	@Inject JvmModelGenerator delegate
	@Inject JvmTypeReferenceBuilder.Factory typeReferenceFactory
	@Inject Provider<XtextResource> resourceProvider

	override beforeGenerate() {
		if (!isNewResourceSet) return;
		resourceSet.resources += generatedConcepts.flatMap [
			#[newResource(conceptDomainClassName.qualifiedName), newResource(conceptDomainProviderClassName.qualifiedName)]
		]
	}

	override generate() {
		if (!isNewResourceSet) return
		val typeReferenceBuilder = typeReferenceFactory.create(resourceSet)
		generatedConcepts.flatMap [
			val domainType = createDomain(typeReferenceBuilder)
			val domainProviderType = createDomainProvider(typeReferenceBuilder, domainType)
			return #[domainType, domainProviderType]
		].forEach[generateType()]
	}

	def private createDomain(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder) {
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
						«'''«conceptName.intermediateModelPackageClassName.simpleName»'''».eINSTANCE,«
						»«
						// Tuid calculation: Uses the 'intermediateId' attribute
						// This attribute delegates to 'fullPath' for intermediate resource bridges
						// TODO remove once resource creation is handled by domains
						»
						new «AttributeTuidCalculatorAndResolver.typeRef»("«conceptName.intermediateModelPackage.nsURI»", «
							»"«IntermediateModelBasePackage.eINSTANCE.intermediate_IntermediateId.name»"),
						"«conceptName.intermediateModelFileExtension»");'''
				]
			)
		]
	}

	def private createDomainProvider(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder,
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

	def private generateType(JvmDeclaredType type) {
		val typeResource = resourceSet.getResource(type.qualifiedName.domainTypeUri, false)
		typeResource.contents += type
		delegate.doGenerate(typeResource, fsa)
	}

	def private static setBody(JvmMember member, StringConcatenationClient body) {
		member.eAdapters += new CompilationTemplateAdapter() => [compilationTemplate = body]
	}

	def private newResource(String name) {
		resourceProvider.get() => [
			URI = name.domainTypeUri
		]
	}
}
