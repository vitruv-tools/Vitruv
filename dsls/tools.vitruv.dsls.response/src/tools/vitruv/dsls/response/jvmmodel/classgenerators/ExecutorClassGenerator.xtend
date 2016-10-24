package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import static extension tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.*;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseExecutor
import tools.vitruv.framework.util.datatypes.MetamodelPair
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.dsls.response.responseLanguage.ReactionsSegment

class ExecutorClassGenerator extends ClassGenerator {
	private final ReactionsSegment reactionsSegment;
	
	new(ReactionsSegment reactionsSegment, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.reactionsSegment = reactionsSegment;
	}
	
	override generateClass() {
		val executorNameGenerator = reactionsSegment.executorClassNameGenerator;
		reactionsSegment.toClass(executorNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractResponseExecutor);
			members += toConstructor() [
				val userInteractingParameter = generateUserInteractingParameter;
				parameters += userInteractingParameter;
				body = '''super(«userInteractingParameter.name», new «MetamodelPair.name»(«
					reactionsSegment.fromMetamodel.model.package.class.name».eNS_URI, «
					reactionsSegment.toMetamodel.model.package.class.name».eNS_URI));'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«UserInteracting.name» userInteracting = getUserInteracting();
					«FOR reaction : reactionsSegment.reactions»
						«val responseNameGenerator = reaction.reactionClassNameGenerator»
						this.addResponse(«responseNameGenerator.qualifiedName».getExpectedChangeType(), new «responseNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}