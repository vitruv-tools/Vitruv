package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondenceSourceDeterminationBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.TargetChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import org.eclipse.emf.ecore.EClass
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicConcreteModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import org.eclipse.emf.ecore.EPackage
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.InvariantViolationEvent
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import org.eclipse.xtext.xbase.XBlockExpression

final class ResponseLanguageHelper {
	private new() {}
	
	public static def Response getContainingResponse(CodeBlock codeBlock) {
		val effects = codeBlock.eContainer();
		if (effects instanceof Effects) {
			return getContainingResponse(effects);
		}
		return null;
	}
	
	public static def Response getContainingResponse(PreconditionBlock preconditionBlock) {
		val trigger = preconditionBlock.eContainer();
		if (trigger instanceof Trigger) {
			return getContainingResponse(trigger);
		}
		return null;
	}
	
	public static def Response getContainingResponse(CorrespondenceSourceDeterminationBlock correspondenceSourceBlock) {
		val modelRootUpdate = correspondenceSourceBlock.eContainer();
		if (modelRootUpdate instanceof ConcreteTargetModelRootChange) {
			return getContainingResponse(modelRootUpdate);
		}
		return null;
	}
	
	public static def Response getContainingResponse(TargetChange targetChange) {
		val effects = targetChange.eContainer();
		if (effects instanceof Effects) {
			return getContainingResponse(effects);
		}
		return null;
	}
	
	public static def Response getContainingResponse(Effects effects) {
		val response = effects.eContainer();
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
	
	public static def dispatch EClass getChangedModelElementClass(Trigger change) {
		throw new UnsupportedOperationException();
	}
	
	public static def dispatch EClass getChangedModelElementClass(AtomicRootObjectChange change) {
		return change?.changedElement?.element;
	}
	
	public static def dispatch EClass getChangedModelElementClass(AtomicFeatureChange change) {
		change?.changedFeature?.element;
	}
	
	public static def dispatch EPackage getSourceMetamodel(AtomicConcreteModelElementChange change) {
		return change.changedModelElementClass?.EPackage;
	}
	
	public static def dispatch EPackage getSourceMetamodel(ArbitraryModelElementChange change) {
		change?.changedModel?.model.package;
	}

	public static def dispatch EPackage getSourceMetamodel(InvariantViolationEvent change) {
		throw new UnsupportedOperationException();
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
}
