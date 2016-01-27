package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange

public class SingleResponseGeneratorFactory {
	public static val INSTANCE = new SingleResponseGeneratorFactory();
	
	private new() {}
	
	public def ISingleResponseGenerator createGenerator(Response response) {
		if (response.trigger instanceof ConcreteModelElementChange) {
			return new SingleChangeResponseGenerator(response);
		} else {
			return new SingleEmptyResponseGenerator();
		}
	}
}