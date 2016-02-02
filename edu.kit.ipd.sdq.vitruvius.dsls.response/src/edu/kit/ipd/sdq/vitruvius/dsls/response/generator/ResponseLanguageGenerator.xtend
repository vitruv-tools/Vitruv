package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseEnvironmentGeneratorFactory

class ResponseLanguageGenerator implements IGenerator {
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		val environmentGenerator = new ResponseEnvironmentGeneratorFactory().createResponseEnvironmentGenerator();
		environmentGenerator.addResponses((resource.contents.get(0) as ResponseFile).responses);
		environmentGenerator.generateEnvironment(fsa);
	}
	
		
}