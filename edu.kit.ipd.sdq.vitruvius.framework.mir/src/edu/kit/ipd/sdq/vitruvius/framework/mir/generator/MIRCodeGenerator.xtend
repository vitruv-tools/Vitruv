package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRModelInformationProvider
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EMFHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.WhenWhereJavaClass
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import java.util.Optional
import java.util.Collection
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EclipseHelper
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult

/**
 * @author Dominik Werle
 * TODO: refactor god class
 */
class MIRCodeGenerator implements IGenerator {
	private static final String SRC_GEN_FOLDER = "src-gen/";
	private static final String MAPPING_PKG_NAME = "mappings";
	private static final String LOGGER_NAME = "LOGGER";
	
	@Inject IGeneratorStatus generatorStatus;
	
	var Map<ClassMapping, String> predicateCheckMethodNames
	var Map<ClassMapping, String> createMethodNames

	var Map<Mapping, String> mappingClassNames;


	var List<ClassMapping> classMappings
	
	@Override
	public override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI.trimSegments(1)
		val mirFile = MIRHelper.getMIR(input)
		
		resetState
		val il = generatorStatus.getIntermediateForMIR(mirFile)
		
		val fqn = il.configuration.package + "." + il.configuration.type
		val projectName = MIRHelper.getProjectName(MIRHelper.getMIR(input));
		
		MIRPluginProjectCreator.createPluginXML(fsa, fqn);
		MIRPluginProjectCreator.createManifest(fsa, projectName,
			mirFile.bundles.map[bundleFQN],
			#[il.configuration.package,
			  il.configuration.package.mappingPackageName]
		)
		
		for (mapping : il.classMappings) {
			generateMappingClass(mapping, il.configuration.package, fsa)
		}
		
		generateTransformationExecuting(il, resourcePath, fsa)
	}
	
	private def getFqn(MIR mir) {
		mir.configuration.package + "." + mir.configuration.type
	}
	
	private def getSimpleName(MIR mir) {
		mir.configuration.type
	}
	
	private def classNameToJavaPath(String name) {
		name.replace('.', PATH_SEPERATOR) + ".java"
	}
	
	private def resetState() {
		predicateCheckMethodNames = new HashMap<ClassMapping, String>()
		createMethodNames = new HashMap<ClassMapping, String>()
		mappingClassNames = new HashMap<Mapping, String>()
	}
	
	new() {
		resetState
	}

	private static final char PATH_SEPERATOR = '/';
	
	private def packageNameToPath(String pkgName) {
		pkgName.replace('.', PATH_SEPERATOR) + PATH_SEPERATOR
	}
	
	/**
	 * The classes that are imported <strong>inside the generated class</strong>.
	 */
	private static final List<? extends Class<?>> IMPORTED_CLASSES_TRANSFORMATION_EXECUTING = #[
		EObject, Map, HashMap, List, ArrayList,
		IllegalArgumentException,
		Pair, VURI,
		AbstractMIRChange2CommandTransforming,
		CorrespondenceInstance, EMFModelChange, Change,
		EChange, EcoreHelper,
		Logger
	]
	
	private static final List<? extends Class<?>> IMPORTED_CLASSES_MAPPING = #[
		MIRMappingRealization, AbstractMIRMappingRealization,
		EChange, CorrespondenceInstance, MIRModelInformationProvider, EStructuralFeature,
		EClass, AbstractMIRChange2CommandTransforming, EObject, List, ArrayList,
		Set, HashSet, EPackage, Pair, MIRMappingHelper, MappedCorrespondenceInstance,
		Logger, JavaHelper, Collections, Blackboard, Optional, EcoreHelper,
		Collection, VURI, EclipseHelper, TransformationResult
	]
	
	/**
	 * Creates the import statements for {@link #IMPORTED_CLASSES}.
	 */
	private static def String getImportStatements(List<? extends Class<?>> classes) {
		'''
		«FOR i : classes»
		import «i.name»;
		«ENDFOR»
		'''
	}

	def createLoggerField(String className) {
		'''private static final Logger «LOGGER_NAME» = Logger.getLogger(«className».class);'''
	}

	private def generateTransformationExecuting(MIR file, URI resourcePath, IFileSystemAccess fsa) {
		classMappings = file.classMappings
		
		fsa.generateFile(SRC_GEN_FOLDER + file.fqn.classNameToJavaPath, '''
			package «file.configuration.package»;
			
			«getImportStatements(IMPORTED_CLASSES_TRANSFORMATION_EXECUTING)»
			
			/**
			 * {@link AbstractMIRChange2CommandTransforming} for keeping the following meta models consistent:
			 * <ol>
			 *   <li>«file.packages.get(0).nsURI»</li>
			 *   <li>«file.packages.get(1).nsURI»</li>
			 * </ol>.
			 */
			public class «file.configuration.type» extends «AbstractMIRChange2CommandTransforming.simpleName» {
				«createLoggerField(file.configuration.type)»
				
				/** The first mapped metamodel. **/
				public final String MM_ONE = "«file.packages.get(0).nsURI»";
				/** The second mapped metamodel. **/
				public final String MM_TWO = "«file.packages.get(1).nsURI»";
				
				/** The correspondence instance for this TransformationExecuting. */
				
				private final VURI VURI_ONE = VURI.getInstance(MM_ONE);
				private final VURI VURI_TWO = VURI.getInstance(MM_TWO);
				
				/* Transformable metamodels. */
				private final List<Pair<VURI, VURI>> transformableMetamodels;
				
				public «file.configuration.type»() {
					transformableMetamodels = new ArrayList<Pair<VURI, VURI>>();
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_ONE, VURI_TWO));
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_TWO, VURI_ONE));
				}
				
				@Override
				protected void setup() {
					«FOR name : mappingClassNames.values»
					addMIRMapping(«file.configuration.package».mappings.«name».INSTANCE);
					«ENDFOR»
				}
				
				@Override
				public List<Pair<VURI, VURI>> getTransformableMetamodels() {
					return transformableMetamodels;
				}
			}
		''')
	}
	
	def String nextMappingClassName() {
		'''Mapping''' + mappingClassNames.size
	}
	
	def String mappingPackageName(String rootPkgName) {
		rootPkgName + "." + MAPPING_PKG_NAME
	}
	
	def String checkWhenWhereJava(WhenWhereJavaClass predicate) {
		'''«predicate.methodFQN»(«predicate.parameterNames.join(", ")»)'''
	}
	
	def dispatch generateMappingClass(ClassMapping mapping, String pkgName, IFileSystemAccess fsa) {
		val className = nextMappingClassName
		mappingClassNames.put(mapping, className)
		
		val leftFQN = mapping.left.type.instanceTypeName
		val leftType = mapping.left.type
		val leftName = mapping.left.name
		
		val rightFQN = mapping.right.type.instanceTypeName
		val rightType = mapping.right.type
		val rightName = mapping.right.name
		
		val hasParent = (mapping.featureMapping != null)
		
		val parentClassMapping = mapping.featureMapping?.parent
		val parentClassMappingClass = mappingClassNames.get(parentClassMapping)
		
		// TODO: DW - Direction important here?
		val parent = mapping.featureMapping?.parent?.left
		val parentFQN = parent?.type?.instanceTypeName
		val parentType = parent?.type
		val parentName = parent?.name
		
		// TODO: DW - multiple hops. non-list...
		val parentReference = mapping.featureMapping?.left?.get(0)?.feature
		
		val parentRight = mapping.featureMapping?.parent?.right
		val parentRightFQN = parentRight?.type?.instanceTypeName
		val parentRightType = parentRight?.type
		val parentRightName = parentRight?.name
		
		fsa.generateFile(SRC_GEN_FOLDER + (pkgName.mappingPackageName + "." + className).classNameToJavaPath,
		'''
			package «pkgName.mappingPackageName»;
			
			«getImportStatements(IMPORTED_CLASSES_MAPPING)»
			
			/**
			 * Class Mapping
			 */
			public class «className» extends «AbstractMIRMappingRealization.simpleName» {
				«createLoggerField(className)»
				
				// Singleton
				public final static «className» INSTANCE = new «className»();
				
				private «className»() {}
				
				protected boolean checkConditions(EObject context,
					Blackboard blackboard) {

					if (!(context instanceof «leftFQN»)) {
						return false;
					}
					
					«leftFQN» «leftName» =
						(«leftFQN») context;

					«featureMappingCheckAndBindingJava(mapping, 0)»
					
					«FOR predicate : mapping.predicates.filter(WhenWhereJavaClass)»
					if (!(«predicate.checkWhenWhereJava»)) {
						return false;
					}
					«ENDFOR»
					
					«FOR predicate : mapping.predicates.filter[!(it instanceof WhenWhereJavaClass)]»
						// unsupported predicate: «predicate»
					«ENDFOR»
					
					return true;
				}
			
				@Override
				protected EClass getMappedEClass() {
					return «EMFHelper.getJavaExpressionThatReturns(leftType, true)»;
				}
				
				@Override
				public String getMappingID() {
					return «className».class.getName();
				}
			
				@Override
				protected void restorePostConditions(EObject eObject, EObject target, EChange change,
						Blackboard blackboard) {
					LOGGER.trace("restorePostConditions(" + eObject.toString() + ", " + target.toString() + ", " + change.toString() + ")");
					
					final «leftFQN» «leftName» = «JavaHelper.simpleName».requireType(eObject, «leftFQN».class);
					final «rightFQN» «rightName» = «JavaHelper.simpleName».requireType(target, «rightFQN».class);
					
					// TODO: restore post conditions
				}
			
				@Override
				protected Collection<Pair<EObject, VURI>> createCorresponding(EObject eObject,
						Blackboard blackboard) {
					LOGGER.trace("createCorresponding(" + eObject.toString() + ", " + blackboard.toString() + ")");
					final «MappedCorrespondenceInstance.name» ci = getMappedCorrespondenceInstanceFromBlackboard(blackboard);
					
					final Collection<Pair<EObject, VURI>> result = new HashSet<Pair<EObject, VURI>>();
					
					«leftFQN» «leftName» = «JavaHelper.simpleName».requireType(eObject, «leftFQN».class);
					«rightFQN» «rightName» = «EMFHelper.getJavaExpressionThatCreates(rightType)»;
					
					«IF hasParent»
					final «parentRightFQN» «parentRightName» = claimParentCorresponding(«leftName», blackboard);
					«parentRightName».get«parentReference.name.toFirstUpper»().add(«rightName»);
					«ENDIF»
					
					if («rightName».eContainer() == null) {
						VURI resourceVURI = VURI.getInstance(EclipseHelper.askForNewResource(«rightName»));
						
						result.add(new Pair<EObject, VURI>((EObject) «rightName», resourceVURI));
					}
					
					// create here, since containment is decided here
					ci.createMappedCorrespondence(«leftName», «rightName», this);
					
					return result;
				}
				
				@Override
				protected void deleteCorresponding(EObject eObject, EObject target, Blackboard blackboard, TransformationResult transformationResult) {
					LOGGER.trace("deleteCorresponding(" + eObject.toString()
						+ ", " + target.toString()
						+ ", " + blackboard.toString() + ")");
					
					super.deleteCorresponding(eObject, target, blackboard, transformationResult);
				}
				
				public static Optional<«rightFQN»> getCorresponding(«leftFQN» «leftName», «Blackboard.simpleName» blackboard) {
					final «MappedCorrespondenceInstance.simpleName» ci =
						getMappedCorrespondenceInstanceFromBlackboard(blackboard);
						
					final Optional<«rightFQN»> «rightName» = JavaHelper.tryCast(
						ci.getMappingTarget(«leftName», «className».INSTANCE), «rightFQN».class);
						
					return «rightName»;
				}
				
				public static «rightFQN» claimCorresponding(«leftFQN» «leftName», «Blackboard.simpleName» blackboard) {
					final «rightFQN» «rightName» = getCorresponding(«leftName», blackboard)
						.orElseThrow(() ->
							new IllegalStateException("Could not find mapped «rightFQN» for «leftFQN» " + «leftName».toString()));
					
					return «rightName»;
				}
				
				«IF hasParent»
				private Optional<«parentFQN»> getParent(«leftFQN» «leftName») {
					final Optional<«parentFQN»> «parentName» = EcoreHelper.findOneReferencee(«leftName»,
						«EMFHelper.getJavaExpressionThatReturns(parentReference, true)», «parentFQN».class);
						
					return «parentName»;
				}
				
				private «parentFQN» claimParent(«leftFQN» «leftName») {
					final Optional<«parentFQN»> «parentName»_opt = getParent(«leftName»);
					final «parentName» = «parentName»_opc.orElseThrow(() -> new IllegalStateException(
						"Could not find a referencing «parentFQN» for «leftFQN» " + «leftName».toString()));
						
					return «parentName»;
				}
				
				private Optional<«parentRightFQN»> getParentCorresponding(«leftFQN» «leftName», Blackboard blackboard) {
					return getParent(«leftName»).map(it ->
						«parentClassMappingClass».getCorresponding(it, blackboard).orElse(null));
				}
				
				private «parentRightFQN» claimParentCorresponding(«leftFQN» «leftName», Blackboard blackboard) {
					final «parentFQN» «parentName» = claimParent(«leftName»);
					final «parentRightFQN» «parentRightName» =
						«parentClassMappingClass».claimCorresponding(«parentName», blackboard);
						
					return «parentRightName»;
				}
				«ENDIF»
			}
		'''
		)
	}
	
	
	def String featureMappingCheckAndBindingJava(ClassMapping mapping, int index) {
		if (mapping.featureMapping != null) {
			val classMappingLeftName = mapping.left.name
			
			val leftType = mapping.featureMapping.parent.left.type
			val leftName = mapping.featureMapping.parent.left.name
			
			val rightType = mapping.featureMapping.parent.right.type
			val rightName = mapping.featureMapping.parent.right.name
			
			val pairName = '''parentAndMappedEObject_«index»'''
			val featureName = '''feature_«index»'''
			val mappingName = '''mapping_«index»'''
			
			
			'''
			// feature «mapping.featureMapping.left.get(0).feature.name»
			EStructuralFeature «featureName» =
			  «EMFHelper.getJavaExpressionThatReturns(mapping.featureMapping.left.get(0).feature, true)»;
			
			«MIRMappingRealization.simpleName» «mappingName» =
			  «mappingClassNames.get(mapping.featureMapping.parent)».INSTANCE;
			
			Pair<EObject, EObject> «pairName» =
				MIRMappingHelper.getReverseFeatureMappedBy(«classMappingLeftName»,
					«featureName», correspondenceInstance, «mappingName»);
					
			«leftType.instanceTypeName» «leftName» =
				(«leftType.instanceTypeName») «pairName».getFirst();
				
			if ((«pairName».getSecond() == null)
			   || !(«pairName».getSecond() instanceof
			          «mapping.featureMapping.left.get(0).EClass.instanceTypeName»))
				{ return false; }
			
			// else: type of mapped element is correct
			«rightType.instanceTypeName» «rightName» =
				(«rightType.instanceTypeName») «pairName».getSecond();
			
			
			«featureMappingCheckAndBindingJava(mapping.featureMapping.parent, index + 1)»
			'''
		} else {
			""
		}
	}
	
	def dispatch generateMappingClass(FeatureMapping mapping, String pkgName, IFileSystemAccess fsa) {
	}
}