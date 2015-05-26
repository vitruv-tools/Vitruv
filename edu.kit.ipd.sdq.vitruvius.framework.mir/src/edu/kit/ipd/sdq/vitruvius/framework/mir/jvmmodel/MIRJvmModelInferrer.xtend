package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.generator.IGeneratorStatus
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.XBlockInferenceHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*
import org.eclipse.xtext.common.types.JvmUnknownTypeReference

class MIRJvmModelInferrer extends AbstractModelInferrer {
	@Inject extension JvmTypesBuilder
	@Inject IGeneratorStatus generatorStatus;
	@Inject extension XBlockInferenceHelper xBlockInferenceHelper
	
   	def dispatch void infer(MIRFile mirFile, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		println("Starting MIRJvmModelInferrer (" + isPreIndexingPhase + ")")
   		if (!isPreIndexingPhase) {
   			generatorStatus.reset
   			xBlockInferenceHelper.reset
   			
   			val pkgName = mirFile.generatedPackage
   			
			EcoreUtil2.resolveAll(mirFile);
			
			mirFile.mappings.forEach [
//				println("Creating classes for Mapping " + it.hashCode)
				it.whenwhere.createMappingWhensWheres(it, pkgName, acceptor)
			]
   		}
   	}
   	
   	def dispatch createMappingWhensWheres(XExpression whenWhere, ClassMapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
   		throw new IllegalStateException("All when-wheres should be XBlockExpressions")
   	}
   	
	def dispatch createMappingWhensWheres(XBlockExpression whenWhere, ClassMapping mapping, String pkgName, IJvmDeclaredTypeAcceptor acceptor) {
		val fstElement = mapping.mappedElements.get(0) as NamedEClass
		val sndElement = mapping.mappedElements.get(1) as NamedEClass
		
		val firstPackage = fstElement.representedEClass.EPackage
		println("first package: " + firstPackage)
		val secondPackage = sndElement.representedEClass.EPackage
		println("second package: " + secondPackage)
		
		val firstName = fstElement.name
		val secondName = sndElement.name
		
		val firstTypeRef = typeRef(fstElement.typeRecursive.instanceTypeName)
		val secondTypeRef = typeRef(sndElement.typeRecursive.instanceTypeName)
		
		throwExceptionIfUnknown(firstTypeRef)
		throwExceptionIfUnknown(secondTypeRef)
		
		// TODO: find out how to omit the actual creation of those classes.
		// they are currently only used so the XBlockExpressions are linked correctly
		acceptor.accept(whenWhere.toClass(pkgName + ".NonInferred" + whenWhere.hashCode)) [
			nonInferredClass |
			nonInferredClass.members += whenWhere.toMethod("nonInferred", typeRef(Void.TYPE)) [
				parameters += whenWhere.toParameter(firstName, firstTypeRef)
				parameters += whenWhere.toParameter(secondName, secondTypeRef)
				
				body = whenWhere
			]
		]
		
		acceptor.accept(whenWhere.toClass(pkgName + ".Inferred" + whenWhere.hashCode)) [
			inferredClass |
			inferredClass.members += #[
				whenWhere.createClosureMethod("equalitiesFirst",
					firstName, firstTypeRef, firstPackage, true,
					"equalitiesFirst"),
				whenWhere.createClosureMethod("assignmentsFirst",
					firstName, firstTypeRef, firstPackage, false,
					"assignmentsFirst"),
				whenWhere.createClosureMethod("equalitiesSecond",
					secondName, secondTypeRef, secondPackage, true,
					"equalitiesSecond"),
				whenWhere.createClosureMethod("assignmentsSecond",
					secondName, secondTypeRef, secondPackage, false,
					"assignmentsSecond")
			]
		]
	}
	
	def throwExceptionIfUnknown(JvmTypeReference reference) {
		if (reference instanceof JvmUnknownTypeReference)
			throw new IllegalStateException("Could not resolve type " + reference.qualifiedName + " - check dependencies.")
	}
	
   	private def createClosureMethod(XExpression block, String name,
   		String parameterName,
   		JvmTypeReference parameterTypeReference,
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
				getEqualityClosure(block, ePackage)
			else
				getAssignmentClosure(block, ePackage)
				
//		println("createClosureMethod: " + name + " => " + block.hashCode + " => " + bodyClosure.hashCode)
		
		block.toMethod(name, typeRef(returnType)) [
			parameters += block.toParameter(parameterName, parameterTypeReference)
			body = bodyClosure
			documentation = comment
		]
	}
}


