package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import tools.vitruvius.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruvius.dsls.response.responseLanguage.ResponsesSegment
import static extension tools.vitruvius.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseExecutor

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
						this.addResponse(«responseNameGenerator.qualifiedName».getExpectedChangeType(), new «responseNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}