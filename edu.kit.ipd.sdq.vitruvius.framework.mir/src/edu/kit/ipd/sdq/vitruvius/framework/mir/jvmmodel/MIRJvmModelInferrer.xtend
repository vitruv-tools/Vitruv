package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.generator.IGeneratorStatus
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.ClosureProvider
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmOperation
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
import org.apache.log4j.Logger

class MIRJvmModelInferrer extends AbstractModelInferrer {
	private static final Logger LOGGER = Logger.getLogger(MIRJvmModelInferrer)
	
	@Inject extension JvmTypesBuilder
	@Inject IGeneratorStatus generatorStatus
	@Inject ClosureProvider closureProvider
	@Inject MIRInvariantJvmModelInferrer invariantJvmModelInferrer

	def static dispatch String getHexHash(Object o) {
		return Integer.toHexString(o.hashCode)
	}

	// The dispatch modifier must not be removed or the method will not override / be called
	def dispatch void infer(MIRFile mirFile, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
		if (!isPreIndexingPhase) {
			val pkgName = mirFile.generatedPackage
			EcoreUtil2.resolveAll(mirFile);
			
			mirFile.invariants.forEach [
				invariantJvmModelInferrer.infer(it, acceptor, pkgName, _typeReferenceBuilder)
			]
			
			mirFile.mappings.filter[isValid].forEach[inferMapping(it as ClassMapping, pkgName, acceptor)]
		}
	}

	def void inferMapping(Mapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		// LOGGER.trace("Inferring for mapping: " + mapping + ". " + mapping.constraints?.whenwhere)

		if (mapping.isValid && (mapping.constraints != null)) {
			mapping.constraints.whenwhere?.createMappingWhensWheres(mapping, pkgName, acceptor)

			val firstPackage = mapping.mappedElements.get(0).typeRecursive.EPackage
			val secondPackage = mapping.mappedElements.get(1).typeRecursive.EPackage

			mapping.constraints.withBlocks.forEach[withBlock|
				inferWithBlock(withBlock, firstPackage, secondPackage, pkgName, acceptor)]

			mapping.constraints.withs.forEach[with|inferMapping(with, pkgName, acceptor)]
		}
	}

	def void inferWithBlock(XExpression withBlock, EPackage firstPackage, EPackage secondPackage, String pkgName,
		IJvmDeclaredTypeAcceptor acceptor) {
		val parameters = createParameterList(withBlock).map[toParameter(withBlock, it)]

		acceptor.accept(withBlock.toClass(pkgName + "." + withBlock.classPrefix + "With" + withBlock.hexHash)) [
			members += withBlock.toMethod("apply", typeRef(Void.TYPE))[applyMethod|
				applyMethod.parameters += parameters
				applyMethod.body = withBlock]
			it.makeStaticClass(withBlock)
		]

		val withBlockName = pkgName + "." + withBlock.classPrefix + "WithInferred" + withBlock.hexHash
		acceptor.accept(withBlock.toClass(withBlockName)) [
			members += #[
				withBlock.createAssignmentClosureMethod(firstPackage.assignmentMethodName, firstPackage, parameters,
					"To be called when " + firstPackage.name + " has been changed"),
				withBlock.createAssignmentClosureMethod(secondPackage.assignmentMethodName, secondPackage, parameters,
					"To be called when " + secondPackage.name + " has been changed") ]
			it.makeStaticClass(withBlock)
		]

		generatorStatus.addWithBlockToInfer(withBlock)
		generatorStatus.putJvmName(withBlock, withBlockName)
		
	}

	private def void makeStaticClass(JvmGenericType type, EObject context) {
		type.members += context.toConstructor [
			visibility = JvmVisibility::PRIVATE
			body = ''''''
			documentation = '''Private constructor since this class is static'''
		]

		type.members.filter(JvmOperation).forEach [
			it.static = true
		]
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
	def dispatch createMappingWhensWheres(XExpression whenWhere, ClassMapping mapping, String pkgName,
		IJvmDeclaredTypeAcceptor acceptor) {
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
	def dispatch createMappingWhensWheres(XBlockExpression whenWhere, Mapping mapping, String pkgName,
		IJvmDeclaredTypeAcceptor acceptor) {
		val fstElement = mapping.mappedElements.get(0)
		val sndElement = mapping.mappedElements.get(1)

		val firstPackage = fstElement.typeRecursive.EPackage
		val secondPackage = sndElement.typeRecursive.EPackage

		// we don't want the user to be able to reference the second element
		// in the first when-where block and vice versa
		val allParameters = createParameterList(whenWhere).map[
			Tuples.create(it, whenWhere.toParameter(it.tryGetName, typeRef(it.typeRecursive.instanceTypeName)))]

		val firstParameters = allParameters.filter[it.first != sndElement].map[second]
		val secondParameters = allParameters.filter[it.first != fstElement].map[second]

		// TODO: find out how to omit the actual creation of those classes.
		// they are currently only used so the XBlockExpressions are linked correctly
		acceptor.accept(whenWhere.toClass(pkgName + "." + whenWhere.classPrefix + "WhenWhere" + whenWhere.hexHash)) [ nonInferredClass |
			nonInferredClass.members += whenWhere.toMethod("nonInferred", typeRef(Void.TYPE)) [
				parameters += allParameters.map[second]
				body = whenWhere
			]
			nonInferredClass.makeStaticClass(whenWhere)
		]

		val whenWhereName = pkgName + "." + whenWhere.classPrefix + "WhenWhereInferred" + whenWhere.hexHash
		acceptor.accept(
			whenWhere.toClass(whenWhereName)) [ inferredClass |
			inferredClass.members += #[
				whenWhere.createClosureMethod(firstPackage.equalitiesMethodName, firstParameters, firstPackage, true,
					"To be called when " + firstPackage.name + " has been changed"),
				whenWhere.createClosureMethod(firstPackage.assignmentMethodName, firstParameters, firstPackage, false,
					"To be called when " + firstPackage.name + " has been changed"),
				whenWhere.createClosureMethod(secondPackage.equalitiesMethodName, secondParameters, secondPackage, true,
					"To be called when " + secondPackage.name + " has been changed"),
				whenWhere.createClosureMethod(secondPackage.assignmentMethodName, secondParameters, secondPackage, false,
					"To be called when " + secondPackage.name + " has been changed")
			]
			inferredClass.makeStaticClass(whenWhere)
		]

		generatorStatus.addWhenWhereToInfer(whenWhere)
		generatorStatus.putJvmName(whenWhere, whenWhereName)
	}
	
	public static def String getAssignmentMethodName(EPackage pkg) {
		'''assignments_«pkg.name»'''		
	}
	
	public static def String getEqualitiesMethodName(EPackage pkg) {
		'''equalities_«pkg.name»'''		
	}
	
	def throwExceptionIfUnknown(JvmTypeReference reference) {
		if (reference instanceof JvmUnknownTypeReference)
			throw new IllegalStateException(
				"Could not resolve type " + reference.qualifiedName + " - check dependencies.")
	}

	private def createClosureMethod(XExpression block, String name, Iterable<JvmFormalParameter> parameters,
		EPackage ePackage, boolean equalities, String comment) {

		val returnType = if (equalities)
				Boolean.TYPE
			else
				Void.TYPE

		val bodyClosure = if (equalities)
				closureProvider.getEqualityClosure(block, ePackage)
			else
				closureProvider.getAssignmentClosure(block, ePackage)

		block.toMethod(name, typeRef(returnType)) [
			it.parameters += parameters
			body = bodyClosure
			documentation = comment
		]
	}

	private def createAssignmentClosureMethod(XExpression block, String name, EPackage ePackage,
		List<JvmFormalParameter> parameters, String comment) {

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
		"CMapping" + mapping.mappedElements.map[it.typeRecursive.name.toFirstUpper].join
	}

	private def dispatch String getClassPrefix(FeatureMapping mapping) {
		"FMapping" + mapping.mappedElements.map[it.typeRecursive.name.toFirstUpper].join
	}

	private def dispatch String getClassPrefix(XBlockExpression block) {
		"Block" + block.eContainer.classPrefix
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
