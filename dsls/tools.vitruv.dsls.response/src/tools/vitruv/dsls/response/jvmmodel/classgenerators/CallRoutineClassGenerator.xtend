package tools.vitruv.dsls.response.jvmmodel.classgenerators

import tools.vitruv.dsls.response.jvmmodel.classgenerators.ClassGenerator
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmVisibility
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruv.dsls.response.helper.ResponseClassNamesGenerator.ClassNameGenerator
import static tools.vitruv.dsls.response.helper.ResponseLanguageConstants.*;
import org.eclipse.xtext.common.types.JvmMember
import java.util.List

class CallRoutineClassGenerator extends ClassGenerator {
	private val EObject objectMappedToClass;
	private val String qualifiedClassName;
	private val ClassNameGenerator routineFacadeClassNameGenerator;
	private val List<JvmMember> methods;
	
	new(TypesBuilderExtensionProvider typesBuilderExtensionProvider, EObject objectMappedToClass, String qualifiedClassName, ClassNameGenerator routineFacadeClassNameGenerator) {
		super(typesBuilderExtensionProvider)
		this.objectMappedToClass = objectMappedToClass;
		this.qualifiedClassName = qualifiedClassName;
		this.routineFacadeClassNameGenerator = routineFacadeClassNameGenerator;
		this.methods = newArrayList;
	}
	
	override generateClass() {
		return objectMappedToClass.toClass(qualifiedClassName) [
			visibility = JvmVisibility.PRIVATE;
			static = true;
			superTypes += typeRef(AbstractEffectRealization.UserExecution);
			val routinesFacadeField = toField(EFFECT_FACADE_FIELD_NAME,  typeRef(routineFacadeClassNameGenerator.qualifiedName)) [
				annotations += annotationRef(Extension);
			]
			members += #[routinesFacadeField];
			members += toConstructor() [
				val responseExecutionStateParameter = generateResponseExecutionStateParameter();
				val calledByParameter = generateParameter("calledBy", typeRef(CallHierarchyHaving));
				parameters += responseExecutionStateParameter;
				parameters += calledByParameter;
				body = '''
					super(«parameters.get(0).name»);
					this.«routinesFacadeField.simpleName» = new «routineFacadeClassNameGenerator.qualifiedName»(«parameters.get(0).name», «parameters.get(1).name»);
				'''
			]
			members += methods;
		]
	}
	
	public def void addMethod(JvmMember method) {
		this.methods += method;
	}
	
	public def String getQualifiedClassName() {
		return qualifiedClassName;
	}
	
}