package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaInvariantInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaResponseInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicInvariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicResponse
import edu.kit.ipd.sdq.vitruvius.framework.mir.generator.IGeneratorStatus
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

class MIRJvmModelInferrer extends AbstractModelInferrer {
	@Inject extension JvmTypesBuilder
	@Inject IGeneratorStatus generatorStatus;
	
	int responseCounter
	int invariantCounter
	int whenCounter
	int whereCounter
	
	private static val CONTEXT_VAR_NAME = "context"
	private static val WHEN_PREFIX = "WHEN_"
	private static val WHERE_PREFIX = "WHERE_"
	
   	def dispatch void infer(MIRFile element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		if (!isPreIndexingPhase) {
   			generatorStatus.reset
   			
   			val pkgName = element.generatedPackage
   			
			EcoreUtil2.resolveAll(element);
			
			whenCounter = 0
			whereCounter = 0

			// at this point, only the Java classes for XBlockExpressions
			// (When, Where, Invariant, Response) are created
			createWhenClasses(element.mappings, pkgName, acceptor)
			createWhereClasses(element.mappings, pkgName, acceptor)
			
			// INVARIANTS
			invariantCounter = 0
			element.invariants.forEach [
				if ((it.predicate != null) && (it.predicate instanceof XBlockExpression)) {
					createInvariant(it.predicate as XBlockExpression, pkgName, it, acceptor)
				}
			]

			// RESPONSES
			responseCounter = 0			
			element.responses.forEach [ createResponse(it, pkgName, acceptor) ]
   		}
   	}
   	
	def void createBlock(EObject predicate, List<MappableElement> context, String pkgName,
		IJvmDeclaredTypeAcceptor acceptor,
		String currentNameVarName, String className,
		Class<?> type
	) {
		if (predicate instanceof XBlockExpression) {
			println("new Class: " + className)
			acceptor.accept(predicate.toClass(className) [ whenClass |
				whenClass.members += predicate.toMethod("check", typeRef(type)) [ checkMethod |
					checkMethod.parameters += context.map [ toParameter(predicate, it) ]
					checkMethod.body = predicate
				]
			])
			
			generatorStatus.PutJvmName(predicate, className)
		}
	}
	
	def void createWhenClasses(EList<Mapping> mappingList, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		mappingList.forEach [ mapping |
			mapping.whens.forEach[it.createWhenPredicateClass(
					mapping.mappedElements.get(0),
					pkgName,
					acceptor
				)]
			
			createWhenClasses(mapping.withs, pkgName, acceptor)
		]
	}
	
	def void createWhereClasses(EList<Mapping> mappingList, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		mappingList.forEach [ mapping |
			mapping.wheres.forEach[it.createWherePredicateClass(
					mapping.mappedElements.get(1),
					pkgName,
					acceptor
				)]
			
			createWhereClasses(mapping.withs, pkgName, acceptor)
		]
	}
	
	def void createWhenPredicateClass(EObject predicate, MappableElement context, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val currentNameVarName = '''this.«WHEN_PREFIX»«whenCounter»''';
		val whenClassName = pkgName + ".whens.When" + whenCounter
		
		createBlock(predicate, #[context], pkgName, acceptor,
				currentNameVarName, whenClassName, Boolean.TYPE)
		whenCounter++
	}
	
	def void createWherePredicateClass(EObject predicate, MappableElement context, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val currentNameVarName = '''this.«WHERE_PREFIX»«whereCounter»''';
		val whereClassName = pkgName + ".wheres.Where" + whereCounter
		
		createBlock(predicate, #[context], pkgName, acceptor,
				currentNameVarName, whereClassName, Void.TYPE)
		whereCounter++
	}
	
   	def void createInvariant(XBlockExpression xbaseBlock, String pkgName, Invariant inv, IJvmDeclaredTypeAcceptor acceptor) {
   		val invariantPkgName = pkgName + "." + "invariants"
   		val invariantClassName = invariantPkgName + ".Invariant_" + invariantCounter + "_" + inv.name.toFirstUpper
		
		acceptor.accept(inv.toClass(invariantClassName)) [ invariantClass |
			invariantClass.annotations += annotationRef(DynamicInvariant)
			
			// TODO: inject methods to manipulate interface
			invariantClass.members += inv.toMethod(JavaInvariantInvoker.CHECK_METHOD_NAME, typeRef(Boolean.TYPE)) [
				parameters += inv.context.toParameter(CONTEXT_VAR_NAME, typeRef(inv.context.instanceTypeName))
				body = xbaseBlock
			]
		]
			
		invariantCounter++
		
		generatorStatus.PutJvmName(xbaseBlock, invariantClassName)
   	}
   	
   	def typeNameToEClassStatement(String typeName) {
   		// TODO is this the right way to do this?
		// de.uka.ipd.sdq.pcm.repository.BasicComponent
   		// --> de.uka.ipd.sdq.pcm.repository.RepositoryPackage.eINSTANCE.getBasicComponent();

   		val splittedName = typeName.split("\\.")
   		val tail = splittedName.get(splittedName.size - 1)
   		val pkgName = splittedName.get(splittedName.size - 2)
   		
   		splittedName.subList(0, splittedName.size - 1).join(".") + "." + pkgName.toFirstUpper + "Package.eINSTANCE.get" + tail + "()"
   	}
   	
   	def translateResponseAction(edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction ra) {
   		ResponseAction.name + "." + ra.getName()
   	}
   	
   	def void createResponse(Response resp, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
   		val responsePkgName = pkgName + "." + "responses"
   		val responseClassName = responsePkgName + "." + "Response_" + responseCounter
   		
   		acceptor.accept(resp.toClass(responseClassName)) [ responseClass |
   			println("new Class: " + responseClassName)
			responseClass.annotations += annotationRef(DynamicResponse)
   			
   			responseClass.members += resp.toMethod(JavaResponseInvoker.DISPATCH_METHOD_NAME, typeRef(Void.TYPE)) [
   				if (resp.context != null) {
   					parameters +=
   						resp.context.toParameter(
   							CONTEXT_VAR_NAME,
   							typeRef((resp.context as NamedEClass).representedEClass.instanceTypeName)
   						)
   				}
   				
   				if (resp.inv != null)
   					parameters += resp.inv.params.map [
   						toParameter(name, typeRef(it.type.instanceTypeName))
   					]
   					
   				if (resp.restoreAction != null)
   					body = resp.restoreAction
   			]
   		]
   		
   		responseCounter++
   		
   		generatorStatus.PutJvmName(resp, responseClassName)
   	}
   	
   	def dispatch toParameterType(NamedFeature object) {
   		var fref = object.representedFeature;
   		var typeName = fref.getEType.instanceTypeName
   		typeRef(typeName)
   	}
   	
   	def dispatch toParameterType(NamedEClass object) {
   		typeRef(object.representedEClass.instanceTypeName)
   	}
   	
   	def toParameter(EObject obj, MappableElement object) {
   		obj.toParameter(object.name, object.toParameterType)
	}
	
	def toTypeIdentifier(NamedEClass object) {
		object.representedEClass.name.orIfNull("unnamed")
	}
	
	def dispatch toIdentifier(NamedFeature object) {
		(object.representedFeature.name.orIfNull("unnamed") + "_" + object.name.orIfNull("unnamed"))
	}
	
	def dispatch toIdentifier(NamedEClass object) {
		(object.eClass.name.orIfNull("unnamed")) + "_" + (object.name.orIfNull("unnamed"))
	}
	
	def dispatch toIdentifier(EClass eClass) {
		(eClass.getEPackage.name + eClass.name).replaceAll("\\.", "_")
	}
	
	def <T>orIfNull(T a, T b) {
		return if (a == null) b else a;
	}
	
}


