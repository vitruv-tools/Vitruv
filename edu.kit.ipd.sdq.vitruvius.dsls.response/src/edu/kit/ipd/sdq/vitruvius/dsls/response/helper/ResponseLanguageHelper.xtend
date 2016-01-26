package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CodeBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CompareBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.TargetModel
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.CorrespondenceSourceDeterminationBlock
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.UpdatedModel

final class ResponseLanguageHelper {
	private new() {}
	
	public static def Response getContainingResponse(CodeBlock codeBlock) {
		val effects = codeBlock.eContainer();
		if (effects instanceof Effects) {
			return getContainingResponse(effects);
		}
		return null;
	}
	
	public static def Response getContainingResponse(CompareBlock compareBlock) {
		val effects = compareBlock.eContainer();
		if (effects instanceof Effects) {
			return getContainingResponse(effects);
		}
		return null;
	}
	
	public static def Response getContainingResponse(CorrespondenceSourceDeterminationBlock correspondenceSourceBlock) {
		val updatedModel = correspondenceSourceBlock.eContainer();
		if (updatedModel instanceof UpdatedModel) {
			return getContainingResponse(updatedModel);
		}
		return null;
	}
	
	public static def Response getContainingResponse(TargetModel targetModel) {
		val effects = targetModel.eContainer();
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
}