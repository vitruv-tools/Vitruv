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
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Mapping

/**
 * @author Dominik Werle
 */
class MIRCodeGenerator implements IGenerator {
	private static final String CONTEXT_NAME = "context";
	private static final String RESULT_NAME = "result";
	private static final String SRC_GEN_FOLDER = "src-gen/";
	
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
		
		for (mapping : (il.classMappings + il.featureMappings)) {
			generateMappingClass(mapping, fsa)
		}
		
		generateTransformationExecuting(il, resourcePath, fsa)
	}
	
	def generateMappingClass(Mapping mapping, IFileSystemAccess access) {
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
				
				public EMFChangeResult handleEChange(EChange eChange, CorrespondenceInstance correspondenceInstance) {
					return null;
				}
			}
		''')
	}
}