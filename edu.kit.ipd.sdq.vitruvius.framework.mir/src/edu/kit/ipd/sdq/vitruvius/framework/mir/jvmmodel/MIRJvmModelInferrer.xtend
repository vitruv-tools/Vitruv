package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaInvariantInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaPredicateEvaluator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaResponseInvoker
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.OCLInvariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.OCLPredicateEvaluator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.WhenEvaluator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicInvariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicResponse
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.MappingOperation
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where
import java.util.ArrayList
import java.util.Collection
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class MIRJvmModelInferrer extends AbstractModelInferrer {

	@Inject extension JvmTypesBuilder
	
	int responseCounter
	int invariantCounter
	int mappingCounter
	int whenCounter
	
	private static val INVARIANT_TYPE = edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant.name
	private static val RESPONSE_INVOKER_TYPE = JavaResponseInvoker.name
	private static val INVARIANT_INVOKER_TYPE = JavaInvariantInvoker.name
	private static val EOBJECT_TYPE = EObject.name
	
	private static val CONTEXT_VAR_NAME = "context"
	private static val WHEN_PREFIX = "WHEN_"
	private static val WHERE_PREFIX = "WHERE_"
	
	private List<String> whenCreateStatements
	private List<String> whereCreateStatements

   	def dispatch void infer(MIRFile element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		// TODO: refactor god method
   		if (!isPreIndexingPhase) {
   			val pkgName = element.generatedPackage
   			val changeListenerName = element.generatedClass ?: "MIRChangeListener"
   			val changeListenerNameFQN = pkgName + "." + changeListenerName
   			val mappingsClassNameFQN = pkgName + "." + changeListenerName + "Mappings"
   			
			EcoreUtil2.resolveAll(element);
			
			// MAPPINGS
			mappingCounter = 0
			whenCounter = 0
			whenCreateStatements = newArrayList()
			whereCreateStatements = newArrayList()
			val Map<Mapping, List<String>> mappingToWhenEvaluation = newHashMap()
			val Map<Mapping, List<String>> mappingToWhereEvaluation = newHashMap()

			createWhenEvaluationStatements(element.mappings, pkgName, acceptor, mappingToWhenEvaluation)
			createWhereEvaluationStatements(element.mappings, pkgName, acceptor, mappingToWhereEvaluation)
			
			acceptor.accept(element.toClass(mappingsClassNameFQN)) [ mappingClass |
				element.mappings.forEach [ mapping |
					createMappingMethods(mapping, acceptor, mappingClass, mappingToWhenEvaluation, null)
				]
			]
			
			// INVARIANTS
			invariantCounter = 0
			val createInvariantStatements = new ArrayList<String>();
			element.invariants.forEach [ invariant |
				if (invariant.predicate != null) {
					val stmnt = createInvariant(invariant.predicate, pkgName, invariant, acceptor)
					createInvariantStatements += stmnt
				}
			]

			// RESPONSES
			responseCounter = 0			
			val createResponseStatements = new ArrayList<String>();
			element.responses.forEach [ response |
				val stmnt = createResponse(response, pkgName, acceptor)
				createResponseStatements += stmnt
			]
			
			acceptor.accept(element.toClass(changeListenerNameFQN)) [
				changeListener |
				
				changeListener.members += element.toMethod("setup", typeRef(Void.TYPE)) [
					body = '''
						// create and link when statements
						«whenCreateStatements.join("\n")»
					
						// mappings
						// setMappingsClass(«mappingsClassNameFQN».class);

						// create invariants
						«createInvariantStatements.map["addInvariant(" + it + ");"].join("\n")»
						
						// create responses
						«createResponseStatements.map["addResponse(" + it + ");"].join("\n")»
					'''
				]
				
				for (var i = 0; i < whenCounter; i++)
					changeListener.members +=
						element.toField(WHEN_PREFIX + i, typeRef(WhenEvaluator))
			]
   		}
   	}
	
	def void createWhenEvaluationStatements(EList<Mapping> mappingList, String pkgName, IJvmDeclaredTypeAcceptor acceptor, Map<Mapping, List<String>> whenMap) {
		mappingList.forEach [ mapping |
			val whenEvaluationStatements = newArrayList 
			for (When when : mapping.whens) {
				whenEvaluationStatements += when.createWhenClass(
					mapping.mappedElements.get(0),
					pkgName,
					acceptor
				)
			}
			whenMap.put(mapping, whenEvaluationStatements)
			
			createWhenEvaluationStatements(mapping.withs, pkgName, acceptor, whenMap)
		]
	}
	
	def void createWhereEvaluationStatements(EList<Mapping> mappingList, String pkgName, IJvmDeclaredTypeAcceptor acceptor, Map<Mapping, List<String>> whereMap) {
		// TODO: implement
	}
	
	def String createWhereClass(Where where, MappableElement element, String string, IJvmDeclaredTypeAcceptor acceptor) {
		// TODO: implement
	}
	
	/**
	 * Generic method for when and where is createPredicate.
	 */
	def String createWhenClass__(When when, MappableElement context, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		// createPredicate(when, context, pkgName, acceptor)
	}
	
	def String createWhenClass(When when, MappableElement context, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val currentNameVarName = '''this.«WHEN_PREFIX»«whenCounter»''';
		
		if (when.predicate instanceof XBlockExpression) {
			val predicate = (when.predicate as XBlockExpression)
			val whenClassName = pkgName + ".whens.When" + whenCounter
			
			acceptor.accept(when.toClass(whenClassName) [ whenClass |
				whenClass.members += when.toMethod("check", typeRef(Boolean.TYPE)) [ checkMethod |
					checkMethod.parameters += toParameter(when, context)
					checkMethod.body = predicate
				]
			])
			
			whenCreateStatements +=
				'''«currentNameVarName» = new «JavaPredicateEvaluator.name»(«whenClassName».class);'''
			
			whenCounter++
		} else if (when.predicate instanceof OCLBlock) {
			val predicate = (when.predicate as OCLBlock)
			whenCreateStatements += '''
				«currentNameVarName» = new «OCLPredicateEvaluator.name»(
					"«predicate.oclString»",
					null, null
				);'''
						
			whenCounter++
		}
		
		return '''«currentNameVarName».check(source, target)'''
	}
	
	def dispatch void createMappingMethods(BaseMapping mapping, IJvmDeclaredTypeAcceptor acceptor, JvmGenericType mappingClass,
		Map<Mapping, List<String>> mappingToWhenEvaluation, Mapping parentMapping
	) {
		val whenEvaluationStatements = mappingToWhenEvaluation.get(mapping)
		
		val leftClass = (mapping.mappedElements.get(0) as NamedEClass)
		val rightClass = (mapping.mappedElements.get(1) as NamedEClass)
			
		createCreateMethod(mappingClass, whenEvaluationStatements, mapping, leftClass,
			rightClass, acceptor, null)
		createCreateMethod(mappingClass, whenEvaluationStatements, mapping, rightClass,
			leftClass, acceptor, null)
		
		mappingCounter++

		mapping.withs.forEach[ subMapping |
			createMappingMethods(subMapping, acceptor, mappingClass, mappingToWhenEvaluation, mapping)
		]
		
	}
	
	def dispatch void createMappingMethods(SubMapping mapping, IJvmDeclaredTypeAcceptor acceptor, JvmGenericType mappingClass,
		Map<Mapping, List<String>> mappingToWhenEvaluation, Mapping parentMapping
	) {
		val whenEvaluationStatements = mappingToWhenEvaluation.get(mapping)
		
		val leftFeature = (mapping.mappedElements.get(0) as NamedFeature)
		val rightFeature = (mapping.mappedElements.get(1) as NamedFeature)
		
		createUpdateMethod(mappingClass, whenEvaluationStatements, mapping, leftFeature,
			rightFeature, acceptor, parentMapping)
		createUpdateMethod(mappingClass, whenEvaluationStatements, mapping, rightFeature,
			leftFeature, acceptor, parentMapping)
			
		mappingCounter++

		mapping.withs.forEach[ subMapping |
			createMappingMethods(subMapping, acceptor, mappingClass, mappingToWhenEvaluation, mapping)
		]
		
	}
	
	def createCreateMethod(JvmGenericType type, Collection<String> whenEvaluationStatements,
		BaseMapping mapping, NamedEClass left, NamedEClass right, IJvmDeclaredTypeAcceptor acceptor,
		Mapping parentMapping
	) {
		val leftTypeName = left.representedEClass.instanceTypeName
		val rightTypeName = right.representedEClass.instanceTypeName
		
		type.members += mapping.toMethod(
			"create_" + left.toTypeIdentifier + "_" + mappingCounter,
			typeRef(rightTypeName)
		) [
			// TODO: xtext does not support non-default values
			annotations += annotationRef(MappingOperation,
				'''CREATE;«left.representedEClass.instanceTypeName»;«right.representedEClass.instanceTypeName»'''
			)
			parameters += left.toParameter("source", typeRef(leftTypeName))
			body = '''
				// check when predicate: 
				// if !((«whenEvaluationStatements.map["(" + it + ")"].join("\n\t// && ")»))
				//   return null; // not the right mapping
				//
				// «EOBJECT_TYPE» target = newInstance(«rightTypeName»);
				// createCorrespondence(source, target);
				// return correspondence
				
				return null;
			'''
		]
		
		mappingCounter++
	}
	
	def createUpdateMethod(JvmGenericType type, List<String> whenEvaluationStatements,
		SubMapping mapping, NamedFeature leftNamedFeature, NamedFeature rightNamedFeature,
		IJvmDeclaredTypeAcceptor acceptor, Mapping parentMapping
	) {
		val leftFeature = leftNamedFeature.representedFeature
		val rightFeature = rightNamedFeature.representedFeature
		
		val leftFeatureTypeName = leftFeature.getEType.instanceTypeName
		val rightFeatureTypeName = rightFeature.getEType.instanceTypeName
		
		
		val leftClass = leftNamedFeature.containingNamedEClass.representedEClass
		val rightClass = rightNamedFeature.containingNamedEClass.representedEClass
		
		val leftTypeName =
			leftClass.instanceTypeName
		val rightTypeName =
			rightClass.instanceTypeName

		type.members += mapping.toMethod(
			"update_" + leftClass.name + "_" + leftFeature.name + "_" + mappingCounter,
			typeRef(Void.TYPE)
		) [
			// TODO: xtext does not support non-default values
//			annotations += annotationRef(MappingOperation,
//				'''CREATE;«left.representedEClass.instanceTypeName»;«right.representedEClass.instanceTypeName»'''
//			)
			parameters += leftNamedFeature.toParameter("context", typeRef(leftTypeName))
			parameters += leftNamedFeature.toParameter("newValue", typeRef(leftFeatureTypeName))
			body = '''
				// check correspondence:
				// rightContext = forceGetCorrespondence(context)
				// if !(rightContext instanceof «rightTypeName»)
				//   return; // not the wanted corresponding type
				//
				// check when predicate: 
				// if !(«whenEvaluationStatements.map["(" + it + ")"].join("\n\t// && ")»)
				//   return null; // not the right correspondence
				//
				// right.«rightFeature.name» = («rightFeatureTypeName») context.«leftFeature.name»
			'''
		]
		
		mappingCounter++
	}
	
   	
   	def dispatch createInvariant(OCLBlock oclBlock, String pkgName, Invariant inv, IJvmDeclaredTypeAcceptor acceptor) {
   		return '''
   			new «OCLInvariant.name»(
   				«inv.context.instanceTypeName.typeNameToEClassStatement»,
   				"«inv.name»",
   				"«oclBlock.oclString»")'''
   	}
   	
   	def dispatch createInvariant(XBlockExpression xbaseBlock, String pkgName, Invariant inv, IJvmDeclaredTypeAcceptor acceptor) {
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
		
		val signatureCreationJava = '''java.util.Arrays.asList(new «INVARIANT_TYPE».Parameter[] {'''
			+ "\n" + inv.params
				.map['''	new «INVARIANT_TYPE».Parameter(
					"«name»",
					«type.instanceTypeName.typeNameToEClassStatement»)''']
				.join("\n")
				
			+ "\n})"
		
		return '''
			new «INVARIANT_INVOKER_TYPE»(
				«invariantClassName».class,
				"«inv.name»",
				«inv.context.instanceTypeName.typeNameToEClassStatement»,
				«signatureCreationJava»
			)''';
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
   	
   	def createResponse(Response resp, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
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
   		return '''new «RESPONSE_INVOKER_TYPE»(
   			«responseClassName».class,
   			«resp.action.translateResponseAction»,
   			«(resp.context as NamedEClass).representedEClass.instanceTypeName.typeNameToEClassStatement»,
			"«resp.inv.name»"
		)'''
   	}
   	
   	def dispatch toParameterType(NamedFeature object) {
   		var fref = object.representedFeature;
   		var typeName = fref.getEType.instanceTypeName
   		typeRef(typeName)
   	}
   	
   	def dispatch toParameterType(NamedEClass object) {
   		typeRef(object.representedEClass.instanceTypeName)
   	}
   	
   	def dispatch toParameter(When w, MappableElement object) {
   		w.toParameter(object.name, object.toParameterType)
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


