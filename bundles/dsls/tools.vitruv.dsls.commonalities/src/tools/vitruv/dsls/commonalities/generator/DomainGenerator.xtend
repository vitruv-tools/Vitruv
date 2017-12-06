package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
import java.util.Collections
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
import tools.vitruv.domains.emf.builder.VitruviusEmfBuilderApplicator
import tools.vitruv.extensions.dslruntime.commonalities.IntermediateVitruvDomain
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage
import tools.vitruv.extensions.dslruntime.commonalities.resources.ResourcesPackage
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*

package class DomainGenerator extends SubGenerator {

	@Inject JvmModelGenerator delegate
	@Inject JvmTypeReferenceBuilder.Factory typeReferenceFactory
	@Inject Provider<XtextResource> resourceProvider

	override beforeGenerate() {
		commonalityFile.eResource.resourceSet.resources += generatedConcepts.flatMap [
			#[newResource(conceptDomainClass.qualifiedName), newResource(conceptDomainProvider.qualifiedName)]
		]
	}

	override generate() {
		val typeReferenceBuilder = typeReferenceFactory.create(commonalityFile.eResource.resourceSet)
		generatedConcepts.flatMap [
			val domainType = createDomain(typeReferenceBuilder)
			val domainProviderType = createDomainProvider(typeReferenceBuilder, domainType)
			return #[domainType, domainProviderType]
		].forEach[generateType()]
	}

	def private createDomain(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder) {
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = conceptName.conceptDomainClass.simpleName
			packageName = conceptName.conceptDomainClass.packageName
			superTypes += IntermediateVitruvDomain.typeRef
			visibility = JvmVisibility.PUBLIC
			members += Arrays.asList(
				TypesFactory.eINSTANCE.createJvmConstructor => [
					visibility = JvmVisibility.PUBLIC
					body = '''
					super("«conceptName.conceptDomainName»",
						«'''«conceptName.intermediateModelClassesPrefix»Package'''».eINSTANCE,«
						// TODO remove once resource creation is handled by domains
						»
						«Collections.typeRef».singleton(«ResourcesPackage.typeRef».eINSTANCE),«
						// 'fullPath' for resource bridge resources
						// TODO remove once resource creation is handled by domains
						»
						new «AttributeTuidCalculatorAndResolver.typeRef»("", "«IntermediateModelBasePackage.eINSTANCE.intermediate_IntermediateId.name»"),
						"«conceptName.intermediateModelFileExtension»");'''
				],
				TypesFactory.eINSTANCE.createJvmOperation => [
					visibility = JvmVisibility.PUBLIC
					simpleName = 'getBuilderApplicator'
					returnType = VitruviusProjectBuilderApplicator.typeRef
					body = '''return new «VitruviusEmfBuilderApplicator.typeRef»();'''
				]
			)
		]
	}

	def private createDomainProvider(String conceptName, extension JvmTypeReferenceBuilder typeReferenceBuilder,
		JvmGenericType domainType) {
		TypesFactory.eINSTANCE.createJvmGenericType => [
			simpleName = conceptName.conceptDomainProvider.simpleName
			packageName = conceptName.conceptDomainProvider.packageName
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
		val resourceSet = commonalityFile.eResource.resourceSet
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
