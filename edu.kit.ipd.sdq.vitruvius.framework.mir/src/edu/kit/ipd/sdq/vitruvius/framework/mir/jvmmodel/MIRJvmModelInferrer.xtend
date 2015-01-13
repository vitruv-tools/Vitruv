package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaInvariantInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaResponseInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.OCLPredicateEvaluator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicInvariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicResponse
import edu.kit.ipd.sdq.vitruvius.framework.mir.generator.IGeneratorStatus
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import java.util.List
import org.eclipse.xtext.util.Tuples
import org.eclipse.emf.ecore.EClassifier
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EcoreHelper
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper

class MIRJvmModelInferrer extends AbstractModelInferrer {
	@Inject extension JvmTypesBuilder
	@Inject IGeneratorStatus generatorStatus;
	
	int responseCounter
	int invariantCounter
	int whenCounter
	int whereCounter
	
	private static val CONTEXT_VAR_NAME = "context"

   	def dispatch void infer(MIRFile element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		if (!isPreIndexingPhase) {
   			generatorStatus.reset
   			
   			val pkgName = element.generatedPackage
   			
			EcoreUtil2.resolveAll(element);
			
			whenCounter = 0
			whereCounter = 0

			// at this point, only the Java classes for XBlockExpressions
			// (When, Where, Invariant, Response) are created
			element.mappings.forEach [
				createMappingWhensWheres(pkgName, acceptor)
			]
			
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
   	
	def void createBlock(EObject predicate, List<EObject> parameterList, String pkgName,
		IJvmDeclaredTypeAcceptor acceptor,
		String className,
		Class<?> type
	) {
		if (predicate instanceof XBlockExpression) {
			acceptor.accept(predicate.toClass(className) [ whenClass |
				whenClass.members += predicate.toMethod("apply", typeRef(type)) [ checkMethod |
					checkMethod.parameters += parameterList.map [
						toParameter(predicate, it)
					]
					checkMethod.body = predicate
				]
				
				whenClass.makeSingleton(predicate)
			])
			
			generatorStatus.putJvmName(predicate, className)
		} else if (predicate instanceof OCLBlock) {
			acceptor.accept(predicate.toClass(className)) [ whenClass |
				whenClass.members += predicate.toMethod("apply", typeRef(type)) [ checkMethod |
					checkMethod.parameters += parameterList.map [
						toParameter(predicate, it, "context")
					]
					checkMethod.body = '''return this.predicateEvaluator.check(context);'''
				]
				whenClass.members += predicate.toField("predicateEvaluator", typeRef(OCLPredicateEvaluator)) [
					visibility = JvmVisibility::PRIVATE
				]
				whenClass.makeSingleton(predicate,
					'''
					this.predicateEvaluator = new OCLPredicateEvaluator(
						"«predicate.oclString»",
						«parameterList.map['"' + tryGetName + '"'].join(", ")»);
					'''
				)
			]
		}
	}
	
	def void makeSingleton(JvmGenericType type, EObject context, StringConcatenationClient constructorBody) {
		type.members += context.toConstructor [
			visibility = JvmVisibility::PRIVATE
			body = constructorBody
		]
		type.members += context.toField("INSTANCE", typeRef(type)) [
			visibility = JvmVisibility::PUBLIC
			static = true
			initializer = '''new «type.simpleName»()'''
		]
	}
	
	def void makeSingleton(JvmGenericType type, EObject context) {
		makeSingleton(type, context, '''''');
	}

	// TODO: Pull up name from NamedEClass and FeatureCall, refactor
	def List<EObject> createParameterList(EObject context, int index) {
		var containerHierarchy = EcoreHelper.getContainerHierarchy(context, true)
		
		val result = new ArrayList<EObject>()
		
		for (EObject o : containerHierarchy) {
			if (o instanceof ClassMapping) {
				if (o.mappedElements.size > index) {
					val namedEClass = o.mappedElements.get(index) as NamedEClass
					if (namedEClass?.name != null)
						result += namedEClass
				}
					
			} else if (o instanceof FeatureMapping) {
				if (o.mappedElements.size > index) {
					val featureCall = o.mappedElements.get(index)
					val callList = MIRHelper.collectFeatureCalls(featureCall)
					result += callList.filter[name != null]
				}
			}
		}
		
		result.reverse
	}
	
	// TODO: refactor whens/withs into super class
	def void createMappingWhensWheres(EObject mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val whens = switch (mapping) {
			ClassMapping: mapping.whens
			FeatureMapping: mapping.whens
			default: null
		}
		
		val withs = switch (mapping) {
			ClassMapping: mapping.withs
			FeatureMapping: mapping.withs
			default: null
		}
		
		val wheres = switch (mapping) {
			ClassMapping: mapping.wheres
			FeatureMapping: mapping.wheres
			default: null
		}
		
		val parameterListWhen = createParameterList(mapping, 0)
		val parameterListWhere = createParameterList(mapping, 1)
		
		whens.forEach[it.createWhenPredicateClass(
			parameterListWhen,
			pkgName,
			acceptor
		)]
		
		wheres.forEach[it.createWherePredicateClass(
			parameterListWhere,
			pkgName,
			acceptor
		)]
		
		withs.forEach[it.createMappingWhensWheres(pkgName, acceptor)]
	}
	
	def void createWhenPredicateClass(EObject predicate, List<EObject> parameterList, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val whenClassName = pkgName + ".whens.When" + whenCounter
		
		createBlock(predicate, parameterList, pkgName, acceptor, whenClassName, Boolean)
		whenCounter++
	}
	
	def void createWherePredicateClass(EObject predicate, List<EObject> parameterList, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val whereClassName = pkgName + ".wheres.Where" + whereCounter
		
		createBlock(predicate, parameterList, pkgName, acceptor,
				whereClassName, Void.TYPE)
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
		
		generatorStatus.putJvmName(xbaseBlock, invariantClassName)
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
   		
   		generatorStatus.putJvmName(resp, responseClassName)
   	}
   	
   	def dispatch toParameterType(FeatureCall object) {
   		var typeName = object.typeRecursive?.instanceTypeName
   		if (typeName == null)
   			typeRef(Object)
   		else
   			typeRef(typeName)
   	}
   	
   	def dispatch toParameterType(NamedEClass object) {
   		typeRef(object.representedEClass.instanceTypeName)
   	}
   	
   	def dispatch toParameterType(EObject object) { return null }
   	
   	def toParameter(EObject context, EObject object) {
   		context.toParameter(object.tryGetName, object.toParameterType)
	}
	
   	def toParameter(EObject context, EObject object, String name) {
   		context.toParameter(name, object.toParameterType)
	}
	
	def toTypeIdentifier(NamedEClass object) {
		object.representedEClass.name.orIfNull("unnamed")
	}
	
	def dispatch toIdentifier(FeatureCall object) {
		(object.getStructuralFeature.name.orIfNull("unnamed") + "_" + object.name.orIfNull("unnamed"))
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
	
	
	def dispatch String tryGetName(NamedEClass object) { return object.name }
	def dispatch String tryGetName(FeatureCall object) { return object.name }
	def dispatch String tryGetName(EObject object) { return null }
}


