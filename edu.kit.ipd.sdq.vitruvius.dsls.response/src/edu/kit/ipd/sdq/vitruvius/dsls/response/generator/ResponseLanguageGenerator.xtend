package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponseFile
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.generator.ResponseEnvironmentGeneratorFactory
import com.google.inject.Inject
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator

class ResponseLanguageGenerator implements IGenerator {
	@Inject
	private JvmModelGenerator jvmModelGenerator;
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		jvmModelGenerator.doGenerate(resource, fsa);
		val environmentGenerator = new ResponseEnvironmentGeneratorFactory().createResponseEnvironmentGenerator();
		environmentGenerator.addResponses((resource.contents.get(0) as ResponseFile).responses);
		environmentGenerator.generateEnvironment(fsa);
	}
	
		
}