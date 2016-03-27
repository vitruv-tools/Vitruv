package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseExecutor
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.*;

class ExecutorClassGenerator extends ClassGenerator {
	private final ResponsesSegment responsesSegment;
	
	new(ResponsesSegment responsesSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.responsesSegment = responsesSegment;
	}
	
	override generateClass() {
		val executorNameGenerator = responsesSegment.executorClassNameGenerator;
		responsesSegment.toClass(executorNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractResponseExecutor);
			members += toConstructor() [
				val userInteractingParameter = generateUserInteractingParameter;
				parameters += userInteractingParameter;
				body = '''super(«userInteractingParameter.name»);'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«FOR response : responsesSegment.responses»
						«val responseNameGenerator = response.responseClassNameGenerator»
						this.addResponse(«responseNameGenerator.qualifiedName».getTrigger(), new «responseNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}