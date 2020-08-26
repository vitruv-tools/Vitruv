package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import edu.kit.ipd.sdq.commons.util.java.Pair
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.*
import tools.vitruv.framework.domains.VitruvDomainProviderRegistry
import tools.vitruv.dsls.reactions.api.generator.ReferenceClassNameAdapter
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClassifier
import tools.vitruv.dsls.reactions.reactionsLanguage.Reaction
import tools.vitruv.dsls.reactions.reactionsLanguage.Routine
import tools.vitruv.dsls.reactions.reactionsLanguage.RoutineOverrideImportPath
import static extension tools.vitruv.dsls.reactions.util.ReactionsLanguageUtil.*;

final class ReactionsLanguageHelper {
	private new() {
	}

	public static def dispatch String getXBlockExpressionText(XExpression expression) '''
	{
		«NodeModelUtils.getNode(expression).text»
	}'''

	public static def dispatch String getXBlockExpressionText(XBlockExpression expression) {
		NodeModelUtils.getNode(expression).text;
	}

	private static def getOptionalReferenceAdapter(EObject element) {
		element.eAdapters.findFirst [isAdapterForType(ReferenceClassNameAdapter)] as ReferenceClassNameAdapter
	}

	public static def getJavaClassName(EClassifier eClassifier) {
		eClassifier.optionalReferenceAdapter?.qualifiedNameForReference ?: eClassifier.instanceClassName
	}
	
	public static def getRuntimeClassName(EObject element) {
		element.optionalReferenceAdapter?.qualifiedNameForReference ?: element.class.canonicalName
	}

	public static def getJavaClassName(MetaclassReference metaclassReference) {
		metaclassReference.metaclass.javaClassName;
	}

	public static def VitruvDomainProvider<?> getProviderForDomain(VitruvDomain domain) {
		return if (VitruvDomainProviderRegistry.hasDomainProvider(domain.name)) {
			VitruvDomainProviderRegistry.getDomainProvider(domain.name);
		} else {
			null;
		}
	}

	public static def VitruvDomain getDomainForReference(DomainReference domainReference) {
		return getDomainProviderForReference(domainReference).domain;
	}

	public static def VitruvDomainProvider<?> getDomainProviderForReference(DomainReference domainReference) {
		val referencedDomainProvider = if (VitruvDomainProviderRegistry.hasDomainProvider(domainReference.domain)) {
			VitruvDomainProviderRegistry.getDomainProvider(domainReference.domain)
		}
		if (referencedDomainProvider === null) {
			throw new IllegalStateException("Given domain reference references no existing domain: " + domainReference.domain);
		}
		return referencedDomainProvider;
	}

	static def Pair<VitruvDomain, VitruvDomain> getSourceTargetPair(ReactionsSegment reactionsSegment) {
		val sourceDomain = reactionsSegment.fromDomain.domainForReference;
		val targetDomain = reactionsSegment.toDomain.domainForReference;
		if (sourceDomain !== null && targetDomain !== null) {
			return new Pair<VitruvDomain, VitruvDomain>(sourceDomain, targetDomain);
		} else {
			return null;
		}
	}

	def static ReactionsFile getReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		checkArgument(firstContentElement instanceof ReactionsFile,
			"The given resource %s was expected to contain a ReactionsFile element! (was %s)", resource,
			firstContentElement?.class?.simpleName);

		return firstContentElement as ReactionsFile;
	}
	
	def static getOptionalReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		if (firstContentElement instanceof ReactionsFile) {
			firstContentElement
		} else {
			null
		}
	}
	
	def static containsReactionsFile(Resource resource) {
		resource.optionalReactionsFile !== null
	}
	
	def static isComplete(ReactionsSegment reactionsSegment) {
		if (reactionsSegment === null) return false;
		if (reactionsSegment.name === null) return false;
		if (reactionsSegment.fromDomain === null) return false;
		if (reactionsSegment.toDomain === null) return false;
		return true;
	}
	
	def static isComplete(Reaction reaction) {
		if (reaction === null) return false;
		if (reaction.name === null) return false;
		if (reaction.trigger === null) return false;
		if (reaction.callRoutine === null) return false;
		return true;
	}
	
	def static isComplete(Routine routine) {
		if (routine === null) return false;
		if (routine.name === null) return false;
		if (routine.action === null) return false;
		if (routine.input === null) return false;
		if (routine.input.javaInputElements.findFirst[it.name === null || it.type === null || it.type.qualifiedName === null] !== null) {
			return false;
		}
		if (routine.input.modelInputElements.findFirst[it.name === null || it.metaclass === null || it.metaclass.javaClassName === null] !== null) {
			return false;
		}
		return true;
	}
	
	// note: this triggers a resolve of cross-references
	def static isComplete(RoutineOverrideImportPath routineOverrideImportPath) {
		if (routineOverrideImportPath === null) return false;
		if (routineOverrideImportPath.fullPath.findFirst[it.reactionsSegment === null || it.reactionsSegment.name === null] !== null) {
			return false;
		} 
		return true;
	}
}
