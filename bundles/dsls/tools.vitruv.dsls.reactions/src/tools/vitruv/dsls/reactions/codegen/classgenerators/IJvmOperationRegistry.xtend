package tools.vitruv.dsls.reactions.codegen.classgenerators

import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.emf.ecore.EObject

interface IJvmOperationRegistry {
	def JvmOperation getOrGenerateMethod(EObject contextObject, String methodName, JvmTypeReference returnType, Procedure1<? super JvmOperation> initializer);
	def JvmOperation getOrGenerateMethod(String methodName, JvmTypeReference returnType, Procedure1<? super JvmOperation> initializer);
}