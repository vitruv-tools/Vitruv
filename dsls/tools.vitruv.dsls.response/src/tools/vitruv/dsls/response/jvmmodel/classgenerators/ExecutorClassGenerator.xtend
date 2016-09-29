package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.dsls.response.responseLanguage.ResponsesSegment
import static extension tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.userinteraction.UserInteracting

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
				body = '''super(«userInteractingParameter.name», new «MetamodelPair.name»(«
					responsesSegment.fromMetamodel.model.package.class.name».eNS_URI, «
					responsesSegment.toMetamodel.model.package.class.name».eNS_URI));'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«UserInteracting.name» userInteracting = getUserInteracting();
					«FOR response : responsesSegment.responses»
						«val responseNameGenerator = response.responseClassNameGenerator»
						this.addResponse(«responseNameGenerator.qualifiedName».getExpectedChangeType(), new «responseNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}