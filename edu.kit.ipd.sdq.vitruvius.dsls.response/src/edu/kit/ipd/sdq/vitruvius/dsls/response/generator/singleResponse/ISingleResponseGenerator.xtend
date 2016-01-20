package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.singleResponse

interface ISingleResponseGenerator {
	def CharSequence generateResponseClass(String packageName, String className);
}