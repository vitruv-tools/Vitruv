package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.generator.IGeneratorStatus
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EMFHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.ClosureProvider
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmUnknownTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.util.Tuples
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*

class MIRJvmModelInferrer extends AbstractModelInferrer {
	@Inject extension JvmTypesBuilder
	@Inject IGeneratorStatus generatorStatus
	@Inject ClosureProvider closureProvider
	
	// The dispatch modifier must not be removed or the method will not override / be called
   	def dispatch void infer(MIRFile mirFile, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		println("Starting MIRJvmModelInferrer (" + isPreIndexingPhase + ")")
   		if (!isPreIndexingPhase) {
   			// the "compilation state" is reset at this stage
   			generatorStatus.reset
   			closureProvider.reset
   			
   			val pkgName = mirFile.generatedPackage
			EcoreUtil2.resolveAll(mirFile);
			
			mirFile.mappings.forEach [
				inferMapping(it as ClassMapping, pkgName, acceptor)
			]
   		}
   	}
   	
   	def void inferMapping(Mapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
   		println("Inferring for mapping: " + mapping + ". " + mapping.constraints?.whenwhere)
   		
		if (mapping.constraints != null) {
	   		mapping.constraints.whenwhere?.createMappingWhensWheres(mapping, pkgName, acceptor)
	   		
			val firstPackage = mapping.mappedElements.get(0).typeRecursive.EPackage
			val secondPackage = mapping.mappedElements.get(1).typeRecursive.EPackage
		
			mapping.constraints.withBlocks.forEach [ withBlock |
				inferWithBlock(withBlock, firstPackage, secondPackage, pkgName, acceptor)
			]
			
			mapping.constraints.withs.forEach [ with | 
				inferMapping(with, pkgName, acceptor)
			]
		}	
	}
   	
   	def void inferWithBlock(XExpression withBlock, EPackage firstPackage, EPackage secondPackage, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
   		val parameters = createParameterList(withBlock).map [ toParameter(withBlock, it) ]
   		
   		acceptor.accept(withBlock.toClass(pkgName + withBlock.classPrefix + "WithBlock" + withBlock.hashCode)) [
   			members += withBlock.toMethod("apply", typeRef(Void.TYPE)) [ applyMethod |
   				applyMethod.parameters += parameters
   				applyMethod.body = withBlock
   			]
   			
   			it.makeSingleton(withBlock)
   		]
   		
   		acceptor.accept(withBlock.toClass(pkgName + withBlock.classPrefix + "WithBlockInferred" + withBlock.hashCode)) [
   			members += #[
   				withBlock.createAssignmentClosureMethod("assignmentsFirst", firstPackage, parameters, ""),
   				withBlock.createAssignmentClosureMethod("assignmentsSecond", secondPackage, parameters, "")
   			]
   			
   			it.makeSingleton(withBlock)
   		]
   		
   		generatorStatus.addWithBlockToInfer(withBlock)
   	}
   	
	// TODO: Pull up name from NamedEClass and FeatureCall, refactor
	def List<EObject> createParameterList(EObject context) {
		var containerHierarchy = EMFHelper.getContainerHierarchy(context, true)
		
		val result = new ArrayList<EObject>()
		
		for (EObject o : containerHierarchy) {
			if (o instanceof ClassMapping) {
				result += o.mappedElements
				           .filter(NamedEClass)
				           .filterNull
				           .filter[it.name != null]
			} else if (o instanceof FeatureMapping) {
				result += o.mappedElements
				           .map[MIRHelper.collectFeatureCalls(it)]
				           .flatten
				           .filter[it.name != null]
			}
		}
		
		result.reverse
	}
	
	def toParameter(EObject context, EObject object) {
		val typeName = (MIRHelper.getTypeRecursive(object)?.instanceTypeName) ?: "java.lang.Object"
		//val typeName = "java.lang.Object"
		val returnType = typeRef(typeName)
		
		context.toParameter(MIRHelper.tryGetName(object), returnType)
	}
   	
   	/**
   	 * Creates four methods per when-where Block (= per class mapping, since there can only be one).
   	 * <p>
   	 * The closures for the content of those methods can later be found via
   	 * <ul>
   	 *   <li>{@link XBlockInferenceHelper#getEqualityClosure(XExpression, EPackage)} and</li>
   	 *   <li>{@link XBlockInferenceHelper#getAssignmentClosure(XExpression, EPackage)}</li>
   	 * </ul>
   	 */
   	def dispatch createMappingWhensWheres(XExpression whenWhere, ClassMapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
   		throw new IllegalStateException("All when-wheres should be XBlockExpressions")
   	}
   	
   	/**
   	 * Creates four methods per when-where Block (= per class mapping, since there can only be one).
   	 * <p>
   	 * The closures for the content of those methods can later be found via
   	 * <ul>
   	 *   <li>{@link XBlockInferenceHelper#getEqualityClosure(XExpression, EPackage)} and</li>
   	 *   <li>{@link XBlockInferenceHelper#getAssignmentClosure(XExpression, EPackage)}</li>
   	 * </ul>
   	 */
	def dispatch createMappingWhensWheres(XBlockExpression whenWhere, Mapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val fstElement = mapping.mappedElements.get(0)
		val sndElement = mapping.mappedElements.get(1)
		
		val firstPackage = fstElement.typeRecursive.EPackage
		val secondPackage = sndElement.typeRecursive.EPackage
		
		// we don't want the user to be able to reference the second element
		// in the first when-where block and vice versa
		val allParameters =
			createParameterList(whenWhere)
				.map[ Tuples.create(it,
					  whenWhere.toParameter(it.tryGetName, typeRef(it.typeRecursive.instanceTypeName))) ]
		
		
		val firstParameters =
			allParameters.filter[it.first != sndElement].map[second]	
		val secondParameters =
			allParameters.filter[it.first != fstElement].map[second]
					
		// TODO: find out how to omit the actual creation of those classes.
		// they are currently only used so the XBlockExpressions are linked correctly
		acceptor.accept(whenWhere.toClass(pkgName + whenWhere.classPrefix  + "NonInferred" + whenWhere.hashCode)) [
			nonInferredClass |
			nonInferredClass.members += whenWhere.toMethod("nonInferred", typeRef(Void.TYPE)) [
				parameters += allParameters.map[second]
				body = whenWhere
			]
		]
		
		acceptor.accept(whenWhere.toClass(pkgName + whenWhere.classPrefix + "Inferred" + whenWhere.hashCode)) [
			inferredClass |
			inferredClass.members += #[
				whenWhere.createClosureMethod("equalitiesFirst",
					firstParameters, firstPackage, true,
					"equalitiesFirst"),
				whenWhere.createClosureMethod("assignmentsFirst",
					firstParameters, firstPackage, false,
					"assignmentsFirst"),
				whenWhere.createClosureMethod("equalitiesSecond",
					secondParameters, secondPackage, true,
					"equalitiesSecond"),
				whenWhere.createClosureMethod("assignmentsSecond",
					secondParameters, secondPackage, false,
					"assignmentsSecond")
			]
		]
		
		generatorStatus.addWhenWhereToInfer(whenWhere)
	}
	
	def throwExceptionIfUnknown(JvmTypeReference reference) {
		if (reference instanceof JvmUnknownTypeReference)
			throw new IllegalStateException("Could not resolve type " + reference.qualifiedName + " - check dependencies.")
	}
	
   	private def createClosureMethod(XExpression block, String name,
   		Iterable<JvmFormalParameter> parameters,
   		EPackage ePackage,
   		boolean equalities,
   		String comment) {
   			
		val returnType = 
			if (equalities)
				Boolean.TYPE
			else
				Void.TYPE
				
		val bodyClosure = 
			if (equalities)
				closureProvider.getEqualityClosure(block, ePackage)
			else
				closureProvider.getAssignmentClosure(block, ePackage)
				
		block.toMethod(name, typeRef(returnType)) [
			it.parameters += parameters
			body = bodyClosure
			documentation = comment
		]
	}
	
	private def createAssignmentClosureMethod(XExpression block, String name,
		EPackage ePackage, List<JvmFormalParameter> parameters, String comment) {
	
		val bodyClosure = closureProvider.getAssignmentClosure(block, ePackage)
		
		block.toMethod(name, typeRef(Void.TYPE)) [
			it.parameters += parameters
			body = bodyClosure
			documentation = comment
		]		
	}
	
	/** Provides a string that can be prefixed to class names corresponding to the given item */
	private def dispatch String getClassPrefix(Object o) {
		""
	}
	
	private def dispatch String getClassPrefix(ClassMapping mapping) {
		"CMapping"
		+ mapping.mappedElements
			.map [ it.typeRecursive.name.toFirstUpper ]
			.join
	}
	
	private def dispatch String getClassPrefix(FeatureMapping mapping) {
		"FMapping"
		+ mapping.mappedElements
			.map [ it.typeRecursive.name.toFirstUpper ]
			.join
	}
	
	private def dispatch String getClassPrefix(XBlockExpression block) {
		"Block"
		+ block.eContainer.classPrefix
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
}


