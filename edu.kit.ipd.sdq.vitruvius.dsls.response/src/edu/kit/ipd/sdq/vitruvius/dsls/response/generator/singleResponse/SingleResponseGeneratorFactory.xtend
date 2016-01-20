package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ChangeEvent

public class SingleResponseGeneratorFactory {
	public static val INSTANCE = new SingleResponseGeneratorFactory();
	
	private new() {}
	
	public def ISingleResponseGenerator createGenerator(Response response) {
		if (response.trigger instanceof ModelChangeEvent) {
			return new SingleOldChangeResponseGenerator(response)
		} else if (response.trigger instanceof ChangeEvent) {
			return new SingleChangeResponseGenerator(response);
		} else {
			return new SingleEmptyResponseGenerator();
		}
	}
}