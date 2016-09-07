package tools.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import java.util.Map
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import java.util.HashMap

abstract class ClassGenerator extends TypesBuilderExtensionProvider implements IJvmOperationRegistry {
	private Map<String, JvmOperation> methodMap;
	
	public new(TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		typesBuilderExtensionProvider.copyBuildersTo(this);
		this.methodMap = new HashMap<String, JvmOperation>();
	}
	
	public def JvmGenericType generateClass();
	
	override JvmOperation getOrGenerateMethod(EObject contextObject, String methodName, JvmTypeReference returnType, Procedure1<? super JvmOperation> initializer) {
		if (!methodMap.containsKey(methodName)) {
			val operation = contextObject.toMethod(methodName, returnType, initializer);
			methodMap.put(operation.simpleName, operation);
		}
		
		return methodMap.get(methodName);
	}
	
	override JvmOperation getOrGenerateMethod(String methodName, JvmTypeReference returnType, Procedure1<? super JvmOperation> initializer) {
		if (!methodMap.containsKey(methodName)) {
			val operation = generateUnassociatedMethod(methodName, returnType, initializer);
			methodMap.put(operation.simpleName, operation);
		}
		
		return methodMap.get(methodName);
	}
	
	protected def Iterable<JvmOperation> getGeneratedMethods() {
		return methodMap.values;
	}
}