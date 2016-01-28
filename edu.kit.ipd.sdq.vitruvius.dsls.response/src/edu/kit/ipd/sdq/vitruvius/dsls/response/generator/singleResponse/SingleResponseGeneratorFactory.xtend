package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ConcreteModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange

public class SingleResponseGeneratorFactory {
	public static val INSTANCE = new SingleResponseGeneratorFactory();
	
	private new() {}
	
	public def ISingleResponseGenerator createGenerator(Response response) {
		if (response.trigger instanceof ConcreteModelElementChange) {
			return new SingleConcreteModelElementChangeResponseGenerator(response);
		} else if (response.trigger instanceof ArbitraryModelElementChange) {
			return new SingleArbitraryModelElementChangeResponseGenerator(response);
		} else {
			return new SingleEmptyResponseGenerator();
		}
	}
}