package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import edu.kit.ipd.sdq.commons.util.java.Pair
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.dsls.reactions.generator.SimpleTextXBlockExpression
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.*

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

	public static def dispatch String getXBlockExpressionText(SimpleTextXBlockExpression blockExpression) {
		blockExpression.text.toString;
	}

	public static def Class<?> getJavaClass(EClass element) {
		return element.instanceClass;
	}

	public static def Class<?> getJavaClass(MetaclassReference metaclassReference) {
		return metaclassReference.metaclass.javaClass;
	}

	public static def VitruvDomainProvider<?> getProviderForDomain(VitruvDomain domain) {
		return VitruvDomainProvider.getDomainProviderFromExtensionPoint(domain.name);
	}

	public static def VitruvDomain getDomainForReference(DomainReference domainReference) {
		return getDomainProviderForReference(domainReference).domain;
	}

	public static def VitruvDomainProvider<?> getDomainProviderForReference(DomainReference domainReference) {
		val referencedDomainProvider = VitruvDomainProvider.getDomainProviderFromExtensionPoint(domainReference.domain)
		if (referencedDomainProvider === null) {
			throw new IllegalStateException("Given domain reference references no existing domain");
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
		val firstContentElement = resource?.contents?.get(0)
		checkArgument(firstContentElement instanceof ReactionsFile,
			"The given resource %s was expected to contain a ReactionsFile element (was %s).", resource,
			firstContentElement?.class?.simpleName);

		return firstContentElement as ReactionsFile;
	}
	
	def static getOptionalReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.get(0)
		if (firstContentElement instanceof ReactionsFile) {
			firstContentElement
		} else {
			null
		}
	}
	
	def static containsReactionsFile(Resource resource) {
		resource.optionalReactionsFile !== null
	}
}
