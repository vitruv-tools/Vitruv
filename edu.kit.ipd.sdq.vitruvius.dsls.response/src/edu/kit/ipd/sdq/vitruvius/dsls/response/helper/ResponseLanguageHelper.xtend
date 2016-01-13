package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import org.eclipse.xtext.xbase.XBlockExpression
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effects

final class ResponseLanguageHelper {
	private new() {}
	
	public static def Response getContainingResponse(XBlockExpression codeBlock) {
		val effects = codeBlock.eContainer();
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