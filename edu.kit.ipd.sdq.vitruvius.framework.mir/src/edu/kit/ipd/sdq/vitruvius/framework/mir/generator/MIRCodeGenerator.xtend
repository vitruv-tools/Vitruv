package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaPredicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ReverseFeaturesCorrespondWithEClassifiers
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaInitializer
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.CreateCorresponding
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping
import java.util.ArrayList

class MIRCodeGenerator implements IGenerator {
	private static final String CONTEXT_NAME = "context";
	private static final String RESULT_NAME = "result";
	
	@Inject IGeneratorStatus generatorStatus;
	
	var Map<ClassifierMapping, String> predicateCheckMethodNames
	var Map<ClassifierMapping, String> createMethodNames
	
	@Override
	public override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI.trimSegments(1)
		
 		input.contents.filter(typeof(MIRFile)).forEach [
 			resetState
			val il = generatorStatus.getIntermediateForMIR(it)
			transform(il, resourcePath, fsa)
		]
	}
	
	private def resetState() {
		predicateCheckMethodNames = new HashMap<ClassifierMapping, String>();
		createMethodNames = new HashMap<ClassifierMapping, String>();
	}
	
	private def transform(MIR file, URI resourcePath, IFileSystemAccess fsa) {
		println(file.configuration.package)
		println(resourcePath)
		
		var predicateChecks = file.classMappings.map [ generatePredicateCheckMethod ].join
		var checkMappingMethods = file.classMappings.map [ generateCreateMappingMethod ].join
		val featureChangeMethods = file.featureMappings.map [ generateFeatureChangeMethods ].join
		val mappingIDMethod = generateGetMappingIDMethod
		val callCreateMethod = generateCallCreateMethod
		val checkElementMethod = generateCheckElementMethod
		
		fsa.generateFile(file.configuration.type + ".java", '''
			/*package «file.configuration.package»;*/
			
			import org.eclipse.emf.ecore.EObject;
			import java.util.Map;
			import java.util.HashMap;
			
			class «file.configuration.type» {
				/*
				 * Generated:
				 *     * Methods to check if mapping with index i holds (predicateCheck_i)
				 *     * check mapping methods for every class mapping (checkMapping*_i)
				 *     * Feature add and delete methods for every feature mapping
				 *
				 * Generic delete method:
				 *     1) Delete subtree recursively (starting with the leafs)
				 *     2) Delete corresponding (also starting with leafs)
				 *     3) Delete element intself
				 *     4) Trigger "mapping update checks" for every possibly
				 *        affected model element
				 */
				
				private Map<EObject, Integer> currentMappingID;
				
				public «file.configuration.type»() {
					currentMappingID = new HashMap<EObject, Integer>();
				}
				
				«checkElementMethod»
				
				«mappingIDMethod»
				
				«callCreateMethod»
								
				// predicate checks
				«predicateChecks»
				
				// object creation
				«checkMappingMethods»
				
				// feature change methods
				«featureChangeMethods»
			}
		''')
	}
	
	def generateCallCreateMethod() {
		var i = 0;
		'''
		public void callCreateMethod(int mappingID, EObject «CONTEXT_NAME») {
			switch (mappingID) {
				«FOR createMethod : createMethodNames.values»
				case «i++»:
					«createMethod»(«CONTEXT_NAME»);
				    break;
				«ENDFOR»
				default:
					throw new IllegalArgumentException("Unknown mapping id: " + mappingID);
			}
		}'''
		
		
	}
	
	private def generateCheckElementMethod() {
		'''
		public void checkElement(EObject «CONTEXT_NAME») {
			Integer lastMappingID = currentMappingID.get(«CONTEXT_NAME»);
			Integer newMappingID = getMappingID(«CONTEXT_NAME»);
			
			// only perform an action, if the state has changed
			if (lastMappingID != newMappingID) {
				if (lastMappingID == null) {
					// case 1: new model element
					
					callCreateMethod(newMappingID, «CONTEXT_NAME»);
				} else if (newMappingID == null) {
					// case 2: deleted model element
					
					// delete corresponding ...
				} else {
					// case 3: different mapping
					
					// delete corresponding ...
					callCreateMethod(newMappingID, «CONTEXT_NAME»);
				}
			}
		}
		'''
	}
	
	private def generateGetMappingIDMethod() {
		var i = 0;
		'''
		public Integer getMappingID(EObject «CONTEXT_NAME») {
			«FOR predicateCheck : predicateCheckMethodNames.values»
			if («predicateCheck»(«CONTEXT_NAME»))
				return «i++»;
			«ENDFOR»
			return null;
		}
		'''
	}
	
	private def generatePredicateCheckMethod(ClassifierMapping mapping) {
		val methodName = "predicateCheck_" + predicateCheckMethodNames.size
		val leftType = mapping.left.instanceTypeName
		
		predicateCheckMethodNames.put(mapping, methodName)
		
		'''
		/**
		 * @generated
		 */
		public boolean «methodName»(EObject eObject_«CONTEXT_NAME») {
				if (!(eObject_«CONTEXT_NAME» instanceof «leftType»))
					return false;
					
				// type cast from EObject
				«leftType» «CONTEXT_NAME» = («leftType») eObject_«CONTEXT_NAME»;
			
				«FOR predicate : mapping.predicates SEPARATOR "\n\n"»
					if (!(«getPredicateString(predicate)»)) return false;
				«ENDFOR»
				
				// all predicates hold
				return true;
		}
		'''
		
	}
	
	private def getCreateStatement(EClassifier eClassifier) {
		return "/* create " + eClassifier.name + " */ (null)"
	}
	
	private def generateCreateMappingMethod(ClassifierMapping mapping) {
		val methodName = "create" + mapping.left.name + "_" + createMethodNames.size 
		val leftType = mapping.left.instanceTypeName
		val rightType = mapping.right.instanceTypeName
		
		createMethodNames.put(mapping, methodName)
		
		'''
		/**
		 * @generated
		 */
		public void «methodName»(EObject «CONTEXT_NAME») {
			«rightType» «RESULT_NAME» =
				«getCreateStatement(mapping.right)»;
				
			«getCreateCorrespondenceStatement(CONTEXT_NAME, RESULT_NAME)»;
				
			/* call initializers (where-declarations) */
			«FOR initializer : mapping.initializer»
			«getInitializerStatement(initializer)»;
			«ENDFOR»
		}
		'''
	}
	
	private def dispatch getPredicateString(JavaPredicate predicate) {
		'''/* Java predicate */ «predicate.checkStatement»'''
	}
	
	private def dispatch getPredicateString(ReverseFeaturesCorrespondWithEClassifiers predicate) {
		var leftPath = ""
		var result = "/* RFCWECls */ ("
		
		var predicateParts = new ArrayList<String>()
		
		for (correspondence : predicate.correspondences) {
			leftPath += ".(reverse "+correspondence.feature.name+")"
			predicateParts += '''
				(/* check if «CONTEXT_NAME»«leftPath» corresponds
						with a «correspondence.EClassifier.name» */ false)
				'''
		}
		
		result += predicateParts.join("\n &&")
		result += ")"
		
		return result
	}
	
	private def dispatch getPredicateString(Predicate predicate) {
		'''/* Unknown predicate type */ false'''
	}
	
	private def dispatch getInitializerStatement(CreateCorresponding initializer) {
		'''/* Create corresponding */'''
	}
	
	private def dispatch getInitializerStatement(JavaInitializer initializer) {
		'''/* Java initializer */ «initializer.callStatement»'''
	}
	
	private def dispatch getInitializerStatement(Initializer initializer) {
		'''/* Unknown initializer */'''
	}
	
	private def getCreateCorrespondenceStatement(String varName1, String varName2) {
		return '''/* create correspondence betweeen «varName1» and «varName2» */'''
	}
	
	
	private def String generateFeatureChangeMethods(FeatureMapping mapping) {
		val leftClassifier = mapping.left.last.EClassifier
		val leftFeature = mapping.left.last.feature
		
		val leftType = leftClassifier.instanceTypeName
		val leftElementType = leftFeature.EType.instanceTypeName
		
		val rightFeature = mapping.right.last.feature
		
		val methodNameSuffix = '''_«leftClassifier.name»_«leftFeature.name»'''
		
		'''
		public void add«methodNameSuffix»(«leftType» «CONTEXT_NAME»,
			«leftElementType» elementToAdd) {
			
			// correspondence for elementToAdd should already exist
			// get correspondence elementToAdd_correspondence
			
			// get correspondence «CONTEXT_NAME»_correspondence
			// «CONTEXT_NAME»_correspondence.«rightFeature.name» += elementToAdd_correspondence
		}
		
		public void remove«methodNameSuffix»(«leftType» «CONTEXT_NAME»,
			«leftElementType» deletedElement) {

			// correspondence for deletedElement should already exist
			// get correspondence deletedElement_correspondence
			
			// get correspondence «CONTEXT_NAME»_correspondence
			// «CONTEXT_NAME»_correspondence.«rightFeature.name» -= deletedElement_correspondence
		}
		'''
	}
	
}