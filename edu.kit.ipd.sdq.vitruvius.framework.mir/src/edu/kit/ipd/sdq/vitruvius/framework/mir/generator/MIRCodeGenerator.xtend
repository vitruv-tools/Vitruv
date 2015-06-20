package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFChangeResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.EMFModelTransformationExecuting
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMIRTransformationExecuting
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
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.MIRMappingHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappedCorrespondenceInstance
import org.apache.log4j.Logger

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


	var List<ClassMapping> ClassMappings
	
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
		
		generateMappedCorrespondenceInstance(il, resourcePath, fsa)
		generateTransformationExecuting(il, resourcePath, fsa)
	}
	
	private def getFqn(MIR mir) {
		mir.configuration.package + "." + mir.configuration.type
	}
	
	private def getSimpleName(MIR mir) {
		mir.configuration.type
	}
	
	private def getMappedCorrespondenceName(MIR mir) {
		mir.simpleName + "Correspondence"
	}
	
	private def getMappedCorrespondenceFQN(MIR mir) {
		mir.configuration.package + "." + mir.mappedCorrespondenceName
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
		Pair, EMFModelTransformationExecuting, EMFChangeResult, VURI,
		AbstractMIRTransformationExecuting,
		CorrespondenceInstance, EMFModelChange, Change,
		EChange, EcoreHelper,
		Logger
	]
	
	private static final List<? extends Class<?>> IMPORTED_CLASSES_MAPPING = #[
		MIRMappingRealization, AbstractMIRMappingRealization, EMFChangeResult,
		EChange, CorrespondenceInstance, MIRModelInformationProvider, EStructuralFeature,
		EClass, AbstractMIRTransformationExecuting, EObject, List, ArrayList,
		Set, HashSet, EPackage, Pair, MIRMappingHelper, MappedCorrespondenceInstance,
		Logger
	]
	
	private static final List<? extends Class<?>> IMPORTED_CLASSES_CORRESPONDENCE_INSTANCE = #[
		CorrespondenceInstance, AbstractMappedCorrespondenceInstance, EObject,
		Logger
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

	def generateMappedCorrespondenceInstance(MIR mir, URI resourcePath, IFileSystemAccess fsa) {
		fsa.generateFile(SRC_GEN_FOLDER + mir.mappedCorrespondenceFQN.classNameToJavaPath, '''
			package «mir.configuration.package»;
			
			«getImportStatements(IMPORTED_CLASSES_CORRESPONDENCE_INSTANCE)»
			
			public class «mir.mappedCorrespondenceName» extends AbstractMappedCorrespondenceInstance {
				«createLoggerField(mir.mappedCorrespondenceName)»
				
				private CorrespondenceInstance correspondenceInstance;
				
				@Override
				public CorrespondenceInstance getCorrespondenceInstance() {
					return correspondenceInstance;
				}
				
				public void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
					this.correspondenceInstance = correspondenceInstance;
				}
				
				public «mir.mappedCorrespondenceName»() {
				}
				
				public «mir.mappedCorrespondenceName»(CorrespondenceInstance correspondenceInstance) {
					this.correspondenceInstance = correspondenceInstance;
				}
				
				«FOR mapping : mappingClassNames.keySet»
				«mappingCorrespondenceMethods(mapping)»
				«ENDFOR»
			}
			
		''')
	}
	
	def createLoggerField(String className) {
		'''private static final Logger «LOGGER_NAME» = Logger.getLogger(«className».class);'''
	}
	
	def mappingCorrespondenceMethods(Mapping mapping) {
		if (!mappingClassNames.containsKey(mapping)) {
			throw new IllegalStateException("Mapping class for mapping " + mapping.toString + " not created yet")
		}
		
		val mappingName = mappingClassNames.get(mapping)
		
		if (mapping instanceof ClassMapping) {
			'''
				public boolean isMappedBy«mappingName»(«mapping.left.type.instanceTypeName» «mapping.left.name») {
					«LOGGER_NAME».trace("isMappedBy«mappingName»(" + «mapping.left.name».toString() +")");
					return false;
				}
				
				public «mapping.right.type.instanceTypeName» getMappingTargetFor«mappingName»(«mapping.left.type.instanceTypeName» «mapping.left.name») {
					«LOGGER_NAME».trace("getMappingTargetFor«mappingName»(" + «mapping.left.name».toString() +")");
					return null;
				}
			'''
		}
	}

	private def generateTransformationExecuting(MIR file, URI resourcePath, IFileSystemAccess fsa) {
		ClassMappings = file.classMappings
		
		fsa.generateFile(SRC_GEN_FOLDER + file.fqn.classNameToJavaPath, '''
			package «file.configuration.package»;
			
			«getImportStatements(IMPORTED_CLASSES_TRANSFORMATION_EXECUTING)»
			
			/**
			 * {@link EMFModelTransformationExecuting} for keeping the following meta models consistent:
			 * <ol>
			 *   <li>«file.packages.get(0).nsURI»</li>
			 *   <li>«file.packages.get(1).nsURI»</li>
			 * </ol>.
			 */
			public class «file.configuration.type» extends «AbstractMIRTransformationExecuting.simpleName» {
				«createLoggerField(file.configuration.type)»
				
				/** The first mapped metamodel. **/
				public final String MM_ONE = "«file.packages.get(0).nsURI»";
				/** The second mapped metamodel. **/
				public final String MM_TWO = "«file.packages.get(1).nsURI»";
				
				/** The correspondence instance for this TransformationExecuting. */
				private final «file.mappedCorrespondenceName» mappedCorrespondenceInstance;
				
				private final VURI VURI_ONE = VURI.getInstance(MM_ONE);
				private final VURI VURI_TWO = VURI.getInstance(MM_TWO);
				
				/* Transformable metamodels. */
				private final List<Pair<VURI, VURI>> transformableMetamodels;
				
				public «file.configuration.type»() {
					transformableMetamodels = new ArrayList<Pair<VURI, VURI>>();
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_ONE, VURI_TWO));
					transformableMetamodels.add(new Pair<VURI, VURI>(VURI_TWO, VURI_ONE));
					
					this.mappedCorrespondenceInstance = new «file.mappedCorrespondenceName»();
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
				
				@Override
				protected «file.mappedCorrespondenceName» getMappedCorrespondenceInstance() {
					return this.mappedCorrespondenceInstance;
				}
			
				@Override
				protected void setCorrespondenceInstance(CorrespondenceInstance correspondenceInstance) {
					this.mappedCorrespondenceInstance.setCorrespondenceInstance(correspondenceInstance);
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
					MappedCorrespondenceInstance correspondenceInstance) {

					if (!(context instanceof «mapping.left.type.instanceTypeName»)) {
						return false;
					}
					
					«mapping.left.type.instanceTypeName» «mapping.left.name» =
						(«mapping.left.type.instanceTypeName») context;

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
				protected EClass getMappedEClass() { return null; }
				
				@Override
				protected void restorePostConditions(EChange eChange,
						MappedCorrespondenceInstance correspondenceInstance) {
					«LOGGER_NAME».trace("restorePostConditions(" + eChange.toString() + ", " + correspondenceInstance.toString() + ")");
				}
				
				@Override
				protected void createCorresponding(EObject eObject,
						MappedCorrespondenceInstance correspondenceInstance) {
					«LOGGER_NAME».trace("createCorresponding(" + eObject.toString() + ", " + correspondenceInstance.toString() + ")");
				}
				
				@Override
				protected void deleteCorresponding(EObject eObject,
						MappedCorrespondenceInstance correspondenceInstance) {
					«LOGGER_NAME».trace("deleteCorresponding(" + eObject.toString() + ", " + correspondenceInstance.toString() + ")");
				}
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