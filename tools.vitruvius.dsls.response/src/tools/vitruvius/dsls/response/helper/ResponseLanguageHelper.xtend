package tools.vitruvius.dsls.response.helper

import tools.vitruvius.dsls.response.responseLanguage.Response
import tools.vitruvius.dsls.response.responseLanguage.CodeBlock
import tools.vitruvius.dsls.response.responseLanguage.Trigger
import org.eclipse.emf.ecore.EClass
import tools.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import tools.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import tools.vitruvius.dsls.response.environment.SimpleTextXBlockExpression
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.emf.ecore.EObject
import tools.vitruvius.dsls.response.responseLanguage.PreconditionCodeBlock
import tools.vitruvius.dsls.response.responseLanguage.CorrespondingObjectCodeBlock
import tools.vitruvius.dsls.mirbase.mirBase.ModelElement
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.util.datatypes.Pair;
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import tools.vitruvius.dsls.response.responseLanguage.Effect
import tools.vitruvius.dsls.response.responseLanguage.ImplicitRoutine
import tools.vitruvius.dsls.response.responseLanguage.ExplicitRoutine
import tools.vitruvius.dsls.response.responseLanguage.RetrieveModelElement

final class ResponseLanguageHelper {
	private new() {}
	
	public static def Response getContainingResponse(CodeBlock codeBlock) {
		val effect = codeBlock.eContainer();
		if (effect instanceof Effect) {
			return getContainingResponse(effect);
		}
		return null;
	}
	
	public static def Response getContainingResponse(PreconditionCodeBlock preconditionBlock) {
		val trigger = preconditionBlock.eContainer();
		if (trigger instanceof Trigger) {
			return getContainingResponse(trigger);
		}
		return null;
	}
	
	public static def Response getContainingResponse(CorrespondingObjectCodeBlock correspondenceSourceBlock) {
		val correspondingModelElementSpecification = correspondenceSourceBlock.eContainer();
		if (correspondingModelElementSpecification instanceof RetrieveModelElement) {
			return getContainingResponse(correspondingModelElementSpecification);
		}
		return null;
	}
	
	public static def Response getContainingResponse(RetrieveModelElement correspondingModelElementSpecification) {
		var EObject currentObject = correspondingModelElementSpecification;
		while (!(currentObject instanceof Response) && currentObject != null) {
			currentObject = currentObject.eContainer();
		}
		if (currentObject != null) {
			currentObject as Response;
		} else {
			return null;
		}
	}
	
	public static def Response getContainingResponse(Effect effect) {
		val routine = effect.eContainer();
		if (routine instanceof ImplicitRoutine) {
			return getContainingResponse(routine);
		}
		return null;
	}
	
	public static def Response getContainingResponse(ImplicitRoutine routine) {
		val response = routine.eContainer();
		if (response instanceof Response) {
			return response;
		}
		return null;
	}
	
	public static def Response getContainingResponse(Trigger tigger) {
		val response = tigger.eContainer();
		if (response instanceof Response) {
			return response;
		}
		return null;
	}
	
	public static def dispatch ResponsesSegment getResponsesSegment(ExplicitRoutine routine) {
		return routine.responsesSegment
	}
	
	public static def dispatch ResponsesSegment getResponsesSegment(ImplicitRoutine routine) {
		return routine.containingResponse.responsesSegment;
	}
	
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
