package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CodeBlock
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
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl.SimpleTextXBlockExpression
import org.eclipse.xtext.xbase.XBlockExpression
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingModelElementSpecification
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondingObjectCodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.ModelElement
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.NamedJavaElement
import org.eclipse.xtext.common.types.JvmTypeReference
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ImplicitEffect
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

final class ResponseLanguageHelper {
	private new() {}
	
	public static def Response getContainingResponse(CodeBlock codeBlock) {
		val effect = codeBlock.eContainer();
		if (effect instanceof ImplicitEffect) {
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
		if (correspondingModelElementSpecification instanceof CorrespondingModelElementSpecification) {
			return getContainingResponse(correspondingModelElementSpecification);
		}
		return null;
	}
	
	public static def Response getContainingResponse(CorrespondingModelElementSpecification correspondingModelElementSpecification) {
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
	
	public static def Response getContainingResponse(ImplicitEffect effect) {
		val response = effect.eContainer();
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
	
	public static def Class<?> getJavaClass(EClass element) {
		return element.instanceClass;
	}
	
	public static def Class<?> getJavaClass(ModelElement element) {
		return element.element.javaClass;
	}
	
	static def Pair<VURI, VURI> getSourceTargetPair(Response response) {
		val sourceVURI = response.sourceVURI;
		val targetVURI = response.targetVURI;
		if (sourceVURI != null && targetVURI != null) {
			return new Pair<VURI, VURI>(sourceVURI, targetVURI);
		} else {
			return null;
		}		
	}
	
	private static def VURI getSourceVURI(Response response) {
		val sourceURI = response?.trigger?.sourceMetamodel;
		return sourceURI.VURI;
	}
	
	private static def VURI getTargetVURI(Response response) {
		if (!(response.eContainer instanceof MetamodelPairResponses)) {
			throw new IllegalStateException();
		}
		val metamodels = (response.eContainer as MetamodelPairResponses).affectedMetamodels.map[model.package];
		val sourceMetamodel = response?.trigger?.sourceMetamodel;
		var potentialModels = metamodels.filter[!it.VURI.equals(sourceMetamodel.VURI)];
		if (potentialModels.size > 1) {
			throw new IllegalStateException();
		} else if (potentialModels.size == 0) {
			potentialModels = #[sourceMetamodel];
		}
		
		val targetPackage = potentialModels.get(0);
		return targetPackage.VURI;
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
