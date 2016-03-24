package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators.ClassGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.MetamodelPairResponses
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.ExecutorClassNameGenerator
import org.eclipse.xtext.common.types.JvmVisibility
import edu.kit.ipd.sdq.vitruvius.dsls.response.generator.ResponseClassNamesGenerator.ResponseClassNameGenerator
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.AbstractResponseExecutor

class ExecutorClassGenerator extends ClassGenerator {
	private final MetamodelPairResponses metamodelPairResponses;
	
	new(MetamodelPairResponses metamodelPairResponses, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider)
		this.metamodelPairResponses = metamodelPairResponses;
	}
	
	override generateClass() {
		val executorNameGenerator = new ExecutorClassNameGenerator(metamodelPairResponses);
		metamodelPairResponses.toClass(executorNameGenerator.qualifiedName) [
			superTypes += typeRef(AbstractResponseExecutor);
			members += toConstructor() [
				val userInteractingParameter = generateUserInteractingParameter;
				parameters += userInteractingParameter;
				body = '''super(«userInteractingParameter.name»);'''
			]
			members += toMethod("setup", typeRef(Void.TYPE)) [
				visibility = JvmVisibility.PROTECTED;
				body = '''
					«FOR response : metamodelPairResponses.responses»
						«val responseNameGenerator = new ResponseClassNameGenerator(response)»
						this.addResponse(«responseNameGenerator.qualifiedName».getTrigger(), new «responseNameGenerator.qualifiedName»(userInteracting));
					«ENDFOR»
				'''
			]
		]
	}
	
}