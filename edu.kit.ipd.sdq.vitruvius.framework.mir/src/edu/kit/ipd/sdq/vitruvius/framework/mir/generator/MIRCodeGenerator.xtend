package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import org.eclipse.xtext.generator.IGenerator
import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping
import java.util.HashMap
import org.eclipse.emf.common.util.EList
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EcorePackage

class MIRCodeGenerator implements IGenerator {
	@Inject IGeneratorStatus generatorStatus;
	
	@Override
	public override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI.trimSegments(1)
		
		input.contents.filter(typeof(MIRFile)).forEach[
			val il = generatorStatus.getIntermediateForMIR(it)
			transform(il, resourcePath, fsa)
		]
	}
	
	def transform(MIR file, URI resourcePath, IFileSystemAccess fsa) {
		println(file.configuration.package)
		println(resourcePath)
		
		val classMappingToMethodName = new HashMap<ClassMapping, String>();
		var mappingMethods = generateMappingMethods(file.classMappings, classMappingToMethodName)

		fsa.generateFile(file.configuration.type + ".java", '''
			package «file.configuration.package»;
			
			import org.eclipse.emf.ecore.EObject;
			
			class «file.configuration.type» /* implements ChangeSynchronizer */ {
				Correspondences correspondences;
				
				public «file.configuration.type»(Correspondences correspondences) {
					this.correspondences = correspondences;
				}
				
				
				// TODO: put in super class?
				public void acceptChange(EChange change) {
					if (change instanceof CreateEObject) {
						createEObject((CreateEObject) change);
					} else if (change instanceof UpdateEAttribute) {
						updateEAttribute((UpdateEAttribute) attribute);
					}
				}
				
				public void createEObject(CreateEObject creation) {
					EObject newObject = creation.getNewValue();
					EObject createdObject = null;
					
					«FOR entry : classMappingToMethodName.entrySet»
					if ((createdObject == null) &&
					    (newObject instanceof «entry.key.left.instanceTypeName»)) {
						createdObject = «entry.value»(newObject);
					}
					«ENDFOR»
					
					
					if (createdObject != null) {
						// "update" all features of newObject 
					}
				}
				
				// object creation
				«mappingMethods»
			}
		''')
	}
	
	def getCreateStatement(EClass eClass) {
		return "/* create " + eClass.name + " */ null"
		/*if (eClass.EPackage.equals(EcorePackage.eINSTANCE))
			return "org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.create" + eClass.name.toFirstUpper + "()"
		else 
			return eClass.EPackage.EFactoryInstance.eClass.EAllSuperTypes.findFirst[isInterface].instanceTypeName +
				".eINSTANCE.create" + eClass.name.toFirstUpper + "()";*/
	}
	
	def generateMappingMethods(List<ClassMapping> list, Map<ClassMapping, String> map) {
		var result = ""; 
		var mappingCounter = 0;
		
		for (mapping : list) {
			val methodName = "map" + mapping.left.name + "To" + mapping.right.name + "_"  + mappingCounter
			val rightType = mapping.right.instanceTypeName
			val leftType = mapping.left.instanceTypeName
			
			result += '''
				/**
				 * @generated
				 */
				public «rightType» «methodName»(«leftType» input) {
					if (!(
					«FOR predicate : mapping.predicates SEPARATOR "&&"»
						(«predicate.checkStatement»)
					«ENDFOR»
					))
						return null;
						
					«rightType» result =
						«getCreateStatement(mapping.right)»;
					
					correspondences.correspondences.add(
						«getCreateCorrespondenceStatement("input", "result")»
					);
						
					return result;
				} 
			'''
			
			map.put(mapping, methodName)
			mappingCounter++
		}
		
		return result
	}
	
	def getCreateCorrespondenceStatement(String varName1, String varName2) {
		return '''/* create correspondence betweeen «varName1» and «varName2» */ null'''
	}
	
}