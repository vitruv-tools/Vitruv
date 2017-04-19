package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.reactions.environment.SimpleTextXBlockExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.mirbase.mirBase.MetaclassReference
import tools.vitruv.dsls.mirbase.mirBase.DomainReference
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsSegment
import tools.vitruv.framework.util.datatypes.Pair
import tools.vitruv.framework.domains.VitruvDomainProvider

final class ReactionsLanguageHelper {
	private new() {}
	
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
	
	public static def VitruvDomainProvider<?> getDomainProviderForReference(DomainReference domainReference) {
		val referencedDomainProvider = VitruvDomainProvider.getDomainProviderFromExtensionPoint(domainReference.domain)
	    if (referencedDomainProvider == null) {
	    	throw new IllegalStateException("Given domain reference references no existing domain");
	    }
	    return referencedDomainProvider;
	}
	
	static def Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>> getSourceTargetPair(ReactionsSegment reactionsSegment) {
		val sourceDomain = reactionsSegment.fromDomain.domainProviderForReference;
		val targetDomain = reactionsSegment.toDomain.domainProviderForReference;
		if (sourceDomain != null && targetDomain != null) {
			return new Pair<VitruvDomainProvider<?>, VitruvDomainProvider<?>>(sourceDomain, targetDomain);
		} else {
			return null;
		}		
	}
	
}
