package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent

public class SingleResponseGeneratorFactory {
	public static val INSTANCE = new SingleResponseGeneratorFactory();
	
	private new() {}
	
	public def createGenerator(Response response) {
		if (response.trigger instanceof ModelChangeEvent) {
			return new SingleChangeResponseGenerator(response)
		} else {
			throw new UnsupportedOperationException("This type of response is currently not supported.");
		}
	}
}