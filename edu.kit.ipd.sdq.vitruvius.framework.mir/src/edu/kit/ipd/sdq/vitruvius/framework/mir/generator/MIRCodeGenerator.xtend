package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Predicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaPredicate;
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Initializer
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.JavaInitializer
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.EChangeListener
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change

/**
 * @author Dominik Werle
 */
class MIRCodeGenerator implements IGenerator {
	private static final String CONTEXT_NAME = "context";
	private static final String RESULT_NAME = "result";
	private static final String SRC_GEN_FOLDER = "src-gen/";
	
	private static final String EMFMODELTRANSFORMATIONEXECUTING_FQN = EMFModelTransformationExecuting.name
	
	@Inject IGeneratorStatus generatorStatus;
	
	var Map<ClassifierMapping, String> predicateCheckMethodNames
	var Map<ClassifierMapping, String> createMethodNames
	var List<ClassifierMapping> classifierMappings
	
	@Override
	public override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI.trimSegments(1)
		val mirFile = MIRHelper.getMIR(input)
		
		resetState
		val il = generatorStatus.getIntermediateForMIR(mirFile)
		
		val fqn = il.configuration.package + "." + il.configuration.type
		val projectName = MIRHelper.getProjectName(MIRHelper.getMIR(input));
		
		MIRPluginProjectCreator.createPluginXML(fsa, fqn);
		MIRPluginProjectCreator.createManifest(fsa, projectName)
		
		generateTransformationExecuting(il, resourcePath, fsa)
	}
	
	private def resetState() {
		predicateCheckMethodNames = new HashMap<ClassifierMapping, String>();
		createMethodNames = new HashMap<ClassifierMapping, String>();
	}

	private static final char PATH_SEPERATOR = '/';
	
	private def packageNameToPath(String pkgName) {
		pkgName.replace('.', PATH_SEPERATOR)
	}
	
	/**
	 * The classes that are imported <strong>inside the generated class</strong>.
	 */
	private static final List<? extends Class<?>> IMPORTED_CLASSES = #[
		EObject, Map, HashMap, List, ArrayList,
		IllegalArgumentException,
		Pair, EMFModelTransformationExecuting, EMFChangeResult, VURI,
		CorrespondenceInstance, EMFModelChange, Change,
		EChange, EcoreHelper
	]
	
	/**
	 * Creates the import statements for {@link #IMPORTED_CLASSES}.
	 */
	private static def String getImportStatements() {
		'''
		«FOR i : IMPORTED_CLASSES»
		import «i.name»;
		«ENDFOR»
		'''
	}

	private def generateTransformationExecuting(MIR file, URI resourcePath, IFileSystemAccess fsa) {
		println(file.configuration.package)
		println(resourcePath)
		
		classifierMappings = file.classMappings
		
		var predicateChecks = classifierMappings.map [ generatePredicateCheckMethod ].join
		var checkMappingMethods = classifierMappings.map [ generateCreateMappingMethod ].join
		val featureChangeMethods = file.featureMappings.map [ generateFeatureChangeMethods ].join
		val mappingIDMethod = generateGetMappingIDMethod
		val callCreateMethod = generateCallCreateMethod
		val checkElementMethod = generateCheckElementMethod
		
		fsa.generateFile(SRC_GEN_FOLDER + file.configuration.package.packageNameToPath + '/' + file.configuration.type + ".java", '''
			package «file.configuration.package»;
			
			«importStatements»
			
			/**
			 * {@link EMFModelTransformationExecuting} for keeping the following meta models consistent:
			 * <ol>
			 *   <li>«file.packages.get(0).nsURI»</li>
			 *   <li>«file.packages.get(1).nsURI»</li>
			 * </ol>.
			 */
			class «file.configuration.type» implements «EMFModelTransformationExecuting.simpleName» {
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
				
				/** The first mapped metamodel. **/
				public final String MM_ONE = "«file.packages.get(0).nsURI»";
				/** The second mapped metamodel. **/
				public final String MM_TWO = "«file.packages.get(1).nsURI»";
				
				private final VURI VURI_ONE = VURI.getInstance(MM_ONE);
				private final VURI VURI_TWO = VURI.getInstance(MM_TWO);
				
				/* Transformable metamodels. */
				private final List<Pair<VURI, VURI>> transformableMetamodels;
				
				public «file.configuration.type»() {
					currentMappingID = new HashMap<EObject, Integer>();
					
					transformableMetamodels = new ArrayList<Pair<VURI, VURI>>();
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_ONE, VURI_TWO));
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_TWO, VURI_ONE));
				}
				
				@Override
				public List<Pair<VURI, VURI>> getTransformableMetamodels() {
					return transformableMetamodels;
				}

				/**
				 * For debug purposes only. Prints out {@code s} and returns {@code null}.
				 */
				private static <T> T outAndNull(String s) {
					System.out.println(s);
					return null;
				}
				
				private void deleteCorrespondenceRecursive(EObject object) {
					System.out.println("Deleting correspondence of " + object.toString());
				}
				
				@Override
				public EMFChangeResult executeTransformation(EMFModelChange change, CorrespondenceInstance correspondenceInstance) {
					return handleEChange(change.getEChange(), correspondenceInstance);
				}
				
				@Override
				public EMFChangeResult executeTransformation(CompositeChange compositeChange, CorrespondenceInstance correspondenceInstance) {
					final EMFChangeResult result = new EMFChangeResult();
					for (Change c : compositeChange.getChanges()) {
						if (c instanceof CompositeChange) {
							result.addChangeResult(this.executeTransformation((CompositeChange) c, correspondenceInstance));
						} else if (c instanceof EMFModelChange) {
							result.addChangeResult(this.executeTransformation((EMFModelChange) c, correspondenceInstance));
						} else {
							throw new IllegalArgumentException("Change subtype " + c.class.getName() + " not handled");
						}
					}
					
					return result;
				}
				
				public EMFChangeResult handleEChange(EChange eChange, CorrespondenceInstance correspondenceInstance) {
					return null;
				}
			}
		''')
	}
	
	def generateCallCreateMethod() {
		var result =
			'''
			public void callCreateMethod(int mappingID, EObject «CONTEXT_NAME») {
				switch (mappingID) {
			'''
			
		var i = 0;
		for (classifierMapping : classifierMappings) {
			var createMethod = createMethodNames.get(classifierMapping)
			result +=
				'''
			
				case «i»:
					«createMethod»(«CONTEXT_NAME»);
					break;
				'''
			i++
		}
		
		result +=
			'''
			
				default:
					throw new IllegalArgumentException("Unknown mapping id: " + mappingID);
				}
				
				currentMappingID.put(«CONTEXT_NAME», mappingID);
			}
			'''
			
		return result
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
					
					deleteCorrespondenceRecursive(«CONTEXT_NAME»);
				} else {
					// case 3: different mapping
					
					deleteCorrespondenceRecursive(«CONTEXT_NAME»);
					callCreateMethod(newMappingID, «CONTEXT_NAME»);
				}
			}
		}
		'''
	}
	
	private def generateGetMappingIDMethod() {
		var i = 0;
		var result = '''
		public Integer getMappingID(EObject «CONTEXT_NAME») {
		'''
		
		for (classifierMapping : classifierMappings) {
			var predicateCheck = predicateCheckMethodNames.get(classifierMapping)
			result +=
				'''
				
					if («predicateCheck»(«CONTEXT_NAME»))
						return «i»;
				'''
			i++
		}
		
		result += '''
			return null;
		}'''
		
		return result
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
		return '''outAndNull("create «eClassifier.name»")'''
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
	
	private def dispatch getPredicateString(Predicate predicate) {
		'''/* Unknown predicate type */ false'''
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