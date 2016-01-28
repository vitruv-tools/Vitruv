package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondenceSourceDeterminationBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.TargetChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.PreconditionBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteTargetModelRootUpdate

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
		if (modelRootUpdate instanceof ConcreteTargetModelRootUpdate) {
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
}