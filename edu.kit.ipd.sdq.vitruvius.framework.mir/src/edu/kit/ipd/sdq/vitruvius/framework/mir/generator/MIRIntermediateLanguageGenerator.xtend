package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*

/**
 * Generates the intermediate language form of the model
 * @author Dominik Werle
 */
class MIRIntermediateLanguageGenerator implements IGenerator {
	private static final Logger logger = Logger.getLogger(MIRIntermediateLanguageGenerator)
	public static final String DEFAULT_CLASS_NAME = "ChangeSynchronizer"

	@Inject IGeneratorStatus generatorStatus;

	extension MIRintermediateFactory mirILfactory = MIRintermediateFactory.eINSTANCE

	/**
	 * Entry point of the generator, dispatches to transform
	 * for each MIRFile in the passed resource
	 */
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI
		val mirFile = MIRHelper.getMIR(input)

		transform(mirFile, resourcePath)
	}

	/**
	 * Transformation entry point, sets up the resulting file  and the
	 * transformation context (factories, etc.) and
	 * passes it to the actual mappings. 
	 */
	def transform(MIRFile mirfile, URI resourcePath) {
		val reg = Resource.Factory.Registry.INSTANCE
		val m = reg.extensionToFactoryMap
		val xmifactory = new XMIResourceFactoryImpl

		// dump parsed mir file into xmi for debugging purposes
		m.put("mirxmi", xmifactory)
		val mirXMIResourceSet = new ResourceSetImpl
		val mirXMIResource = mirXMIResourceSet.createResource(
			resourcePath.trimFileExtension.appendFileExtension("mirxmi"));
		mirXMIResource.contents.add(EcoreUtil.copy(mirfile))
		mirXMIResource.save(Collections.EMPTY_MAP)

		m.put("il", xmifactory)
		val mirResourceSet = new ResourceSetImpl
		val outResource = mirResourceSet.createResource(resourcePath.trimFileExtension.appendFileExtension("il"));

		val mir = createMIR

		mirfile.mapMIRFileToMIR(mir)

		outResource.contents.add(mir)
		outResource.save(Collections.EMPTY_MAP)

		generatorStatus.put(mirfile, mir)
	}

	/**
	 * The actual transformation of a MIRFile to a MIR Intermediate Language file
	 */
	def void mapMIRFileToMIR(MIRFile mirfile, MIR mir) {
		mir.configuration = createConfiguration
		mir.configuration.package = mirfile.generatedPackage
		mir.configuration.type = if (mirfile.generatedClass != null)
			mirfile.generatedClass
		else
			DEFAULT_CLASS_NAME

		if (mirfile.imports.size != 2) {
			logger.error("MIR currently only supports exactly two metamodels")
			throw new IllegalStateException("MIR currently only supports exactly two metamodels");
		}

		mir.packages.clear
		mir.packages += mirfile.imports.map[it.package]

		mirfile.mappings.forEach[it.mapMapping(mir)]
	}

	def void mapMapping(Mapping mapping, MIR mir) {
		val fstMapping = mapping.mapToClassMapping(mir, false)
		val sndMapping = mapping.mapToClassMapping(mir, true)

		fstMapping.opposite = sndMapping
		sndMapping.opposite = fstMapping
	}

	def ClassMapping mapToClassMapping(
		Mapping mapping, MIR mir, boolean reverse) {
		
		val newMapping = createClassMapping
		newMapping.left = mapping.getLeft(reverse).createNamedEClass
		newMapping.right = mapping.getRight(reverse).createNamedEClass

		mir.classMappings += newMapping

		if (mapping.constraints != null) {
			if (mapping.constraints.whenwhere != null) {
				newMapping.predicates += {
					val newPredicate = createWhenWhereJavaClass
					mir.predicates += newPredicate
					newPredicate.methodFQN =
						generatorStatus.getJvmName(mapping.constraints.whenwhere)
							+ "." + equalitiesMethodName(reverse)
					newPredicate.parameterNames +=
						mapping.constraints.whenwhere
							.createParameterList
							// filter out the element to map with, since it can't be
							// checked in the predicate
							.filter[it != mapping.getRight(reverse)]
							.map[tryGetName]
							
					newPredicate
				}
			}
			
			newMapping.postconditions +=
				mapping.constraints.withBlocks.map [
					val newPostcondition = createWithBlockPostCondition
					newPostcondition.classFQN =
						generatorStatus.getJvmName(it)
							+ "." + equalitiesMethodName(reverse)
					newPostcondition
				]
				
			mapping.constraints.withs
					// for typing
				.filter(FeatureMapping)
					// ensures, that the mappings are between EClasses 
				.filter[mappedElements.forall [ mE | mE.typeRecursive instanceof EClass ] ]
				.forEach [
					mapToClassMapping(mir, reverse)
						.addFeatureConditions(it, newMapping, mir, reverse) ]
		}

		return newMapping
	}
	
	def assignmentMethodName(boolean reverse) {
		return if (!reverse) "assignmentsSecond" else "assignmentsFirst"
	}
	
	def equalitiesMethodName(boolean reverse) {
		return if (!reverse) "equalitiesSecond" else "equalitiesFirst"
	}

	def addFeatureConditions(
		ClassMapping classMapping,
		FeatureMapping featureMapping, ClassMapping parent,
		MIR mir, boolean reverse) {
			
		val newMapping = createFeatureMapping
		mir.featureMappings += newMapping
		
		newMapping.left += featureMapping.getLeft(reverse)
			                             .createEClassFeatures
			                             
		newMapping.right += featureMapping.getRight(reverse)
			                              .createEClassFeatures
			                              
		newMapping.parent = parent
		newMapping.classMapping = classMapping
	}
	
	private def createEClassFeatures(TypedElement typedElement) {
		typedElement
			.collectFeatureCalls
			.map [
				val namedFeatureCall = createNamedFeatureCall
				namedFeatureCall.EClass = it.ref.typeRecursive.ensureEClass
				namedFeatureCall.feature = it.structuralFeature
				namedFeatureCall.type = it.typeRecursive.ensureEClass
				namedFeatureCall.name = it.name
				
				namedFeatureCall
			]
	}
	
	/**
	 * TODO: Change model since this is a very ugly workaround which
	 * indicates that soemthing is wrong with the model.
	 */
	private def EClass ensureEClass(EClassifier eClassifier) {
		if (eClassifier instanceof EClass)
			return eClassifier
		else
			throw new IllegalStateException(eClassifier.toString + " should be EClass at this point.")
	}

	def TypedElement getLeft(Mapping mapping, boolean reverse) {
		(mapping.mappedElements.get(if(!reverse) 1 else 0))
	}

	def TypedElement getRight(Mapping mapping, boolean reverse) {
		(mapping.mappedElements.get(if(!reverse) 0 else 1))
	}
	
	def createNamedEClass(TypedElement element) {
		val result = createNamedEClass
		result.name =
			element.tryGetName
		result.type =
			element.typeRecursive
			       .ensureEClass
			       
		return result
	}

	def EClass getLeftEClass(Mapping mapping, boolean reverse) {
		mapping.getLeft(reverse)
		       .typeRecursive
		       .ensureEClass
	}

	def EClass getRightEClass(Mapping mapping, boolean reverse) {
		mapping.getRight(reverse)
		       .typeRecursive
		       .ensureEClass
	}
	
	def String getLeftName(Mapping mapping, boolean reverse) {
		mapping.getLeft(reverse)
		       .tryGetName
	}
	
	def String getRightName(Mapping mapping, boolean reverse) {
		mapping.getRight(reverse)
		       .tryGetName
	}
}
