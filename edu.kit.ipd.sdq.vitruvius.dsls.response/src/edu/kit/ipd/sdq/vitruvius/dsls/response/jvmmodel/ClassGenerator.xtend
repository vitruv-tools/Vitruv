package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel

import org.eclipse.xtext.common.types.JvmGenericType
import java.util.Map
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmVisibility
import org.apache.log4j.Logger
import org.eclipse.xtext.xbase.jvmmodel.JvmTypeReferenceBuilder
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import java.util.HashMap

abstract class ClassGenerator implements IJvmOperationRegistry {
	protected extension JvmTypesBuilderWithoutAssociations _typesBuilder;
	protected extension JvmTypeReferenceBuilder _typeReferenceBuilder;
	
	private Map<String, JvmOperation> methodMap;
	
	public new(JvmTypesBuilderWithoutAssociations typesBuilder, JvmTypeReferenceBuilder typeReferenceBuilder) {
		this._typesBuilder = typesBuilder;
		this._typeReferenceBuilder = typeReferenceBuilder;
		this.methodMap = new HashMap<String, JvmOperation>();
	}
	
	protected def void addLoggerMethods(JvmGenericType clazz) {
		clazz.members += generateLoggerInitialization(clazz);
		clazz.members += generateMethodGetLogger();
	}
	
	private def JvmField generateLoggerInitialization(JvmGenericType clazz) {
		generateUnassociatedField("LOGGER", typeRef(Logger)) [
			visibility = JvmVisibility.PUBLIC;
			initializer = '''«Logger».getLogger(«clazz».class)'''
		]
	}
	
	private def JvmOperation generateMethodGetLogger() {
		generateUnassociatedMethod("getLogger", typeRef(Logger)) [
			visibility = JvmVisibility.PROTECTED;
			body = '''return LOGGER;'''
		]
	}
			
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