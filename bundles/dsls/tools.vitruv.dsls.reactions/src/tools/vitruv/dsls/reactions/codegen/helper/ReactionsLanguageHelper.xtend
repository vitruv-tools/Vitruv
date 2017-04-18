package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
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
//		val creatorMethod = Platform.getExtensionRegistry().
//	        	getConfigurationElementsFor(VitruvDomain.EXTENSION_POINT_ID).
//	        	map[Class.forName(getAttribute("domainClass")).getMethod("getInstance")].filterNull.findFirst[];
//	    val referencedDomain = creatorMethod.invoke(null) as VitruvDomain;

//	        	eExecutableExtension("domainClass")].filter(VitruvDomain);
//	    val referencedDomain = domains.findFirst[it.name.equals(domainReference.domain)];

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
	
//	static def Pair<EPackage, EPackage> getSourceTargetPair(Reaction reaction) {
//		return reaction.reactionsSegment.sourceTargetPair;
//	}
//	
//	private static def EPackage getTopPackage(EPackage pckg) {
//		return if (pckg?.nsURI != null) {
//			var topPckg = pckg;
//			while (topPckg.ESuperPackage != null) {
//				topPckg = pckg.ESuperPackage;
//			}
//			return topPckg;
//		} else {
//			null;
//		}
//	}
	
}
