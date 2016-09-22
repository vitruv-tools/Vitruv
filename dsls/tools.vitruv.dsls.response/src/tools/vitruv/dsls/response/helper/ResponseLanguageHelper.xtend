package tools.vitruv.dsls.response.helper

import tools.vitruv.dsls.response.responseLanguage.Response
import tools.vitruv.dsls.response.responseLanguage.Trigger
import org.eclipse.emf.ecore.EClass
import tools.vitruv.dsls.response.responseLanguage.AtomicRootObjectChange
import tools.vitruv.dsls.response.responseLanguage.AtomicFeatureChange
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.response.environment.SimpleTextXBlockExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.mirbase.mirBase.ModelElement
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.util.datatypes.Pair;
import tools.vitruv.dsls.response.responseLanguage.ResponsesSegment

final class ResponseLanguageHelper {
	private new() {}
	
	public static def dispatch EClass getChangedModelElementClass(Trigger change) {
		throw new UnsupportedOperationException();
	}
	
	public static def dispatch EClass getChangedModelElementClass(AtomicRootObjectChange change) {
		return change?.changedElement?.element;
	}
	
	public static def dispatch EClass getChangedModelElementClass(AtomicFeatureChange change) {
		change?.changedFeature?.element;
	}
	
//	public static def dispatch EPackage getSourceMetamodel(AtomicConcreteModelElementChange change) {
//		return change.changedModelElementClass?.EPackage;
//	}
//	
//	public static def dispatch EPackage getSourceMetamodel(ArbitraryModelElementChange change) {
//		change?.changedModel?.model.package;
//	}
//
//	public static def dispatch EPackage getSourceMetamodel(InvariantViolationEvent change) {
//		throw new UnsupportedOperationException();
//	}
	
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
	
	public static def Class<?> getJavaClass(ModelElement element) {
		return element.element.javaClass;
	}
	
	static def Pair<VURI, VURI> getSourceTargetPair(ResponsesSegment responsesSegment) {
		val sourceVURI = responsesSegment.fromMetamodel.model.package.VURI;
		val targetVURI = responsesSegment.toMetamodel.model.package.VURI;
		if (sourceVURI != null && targetVURI != null) {
			return new Pair<VURI, VURI>(sourceVURI, targetVURI);
		} else {
			return null;
		}		
	}
	
	static def Pair<VURI, VURI> getSourceTargetPair(Response response) {
		return response.responsesSegment.sourceTargetPair;
	}
	
	private static def VURI getVURI(EPackage pckg) {
		return if (pckg?.nsURI != null) {
			var topPckg = pckg;
			while (topPckg.ESuperPackage != null) {
				topPckg = pckg.ESuperPackage;
			}
			VURI.getInstance(topPckg.nsURI);
		} else {
			null;
		}
	}
	
}
