package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassifierMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ReverseFeaturesCorrespondWithEClassifiers
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.JavaBlock
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall

/**
 * Generates the intermediate language form of the model
 */
class MIRIntermediateLanguageGenerator implements IGenerator {
	
	@Inject IGeneratorStatus generatorStatus;
	
	extension MIRintermediateFactory mirILfactory = MIRintermediateFactory.eINSTANCE
	
	/**
	 * Entry point of the generator, dispatches to transform
	 * for each MIRFile in the passed resource
	 */
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val resourcePath = input.URI
		input.contents.filter(typeof(MIRFile)).forEach[
			transform(it, resourcePath)
		]
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
		m.put("il", xmifactory)
		
		val mirResourceSet = new ResourceSetImpl()
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
		mir.configuration.type =
			if (mirfile.generatedClass != null)
				mirfile.generatedClass
			else
				"ChangeSynchronizer"
		
		mirfile.mappings
	    	   .forEach [ it.mapClassifierMapping(mir) ]
	}
	
	def void mapClassifierMapping(ClassMapping mapping, MIR mir) {
		val result = createClassifierMapping
		
		val leftElement = mapping.mappedElements.get(0)
		val rightElement = mapping.mappedElements.get(1)
		
		result.left = (leftElement as NamedEClass).representedEClass
		result.right = (rightElement as NamedEClass).representedEClass
		
		val mappedPredicates = mapping.whens.map[dispatchCreatePredicate].filterNull.toList
		mir.predicates += mappedPredicates
		result.predicates += mappedPredicates
		result.initializer += mapping.wheres.map[dispatchCreateInitializer].filterNull
		
		mapping.withs.forEach [ it.mapFeatureMapping(mir, result) ]
					
		mir.classMappings += result
	}
	
	def dispatch dispatchCreatePredicate(Object o) { return null; }
	def dispatch dispatchCreatePredicate(JavaBlock javaBlock) {
		val result = createJavaPredicate
		result.checkStatement = "(" + javaBlock.javaString + ")";
		return result
	}
	
	def dispatch dispatchCreateInitializer(Object o) { return null; }
	def dispatch dispatchCreateInitializer(JavaBlock javaBlock) {
		val result = createJavaInitializer
		result.callStatement = "(" + javaBlock.javaString + ")";
		return result
	}
	
	def void mapFeatureMapping(edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping mapping, MIR mir,
		ClassifierMapping parent
	) {
		// create new Feature Mapping
		val featureMapping = createFeatureMapping
		mir.featureMappings += featureMapping
		
		val leftElement = mapping.mappedElements.get(0) as FeatureCall
		val rightElement = mapping.mappedElements.get(1) as FeatureCall
		
		featureMapping.left = createEClassifierFeature(leftElement)
		featureMapping.right = createEClassifierFeature(rightElement)

		// create new Class Mapping for the Feature Mapping
		val classifierMapping = createClassifierMapping
		mir.classMappings += classifierMapping
		
		classifierMapping.left = featureMapping.left.EClassifier
		classifierMapping.right = featureMapping.right.EClassifier

		// create initializers for classifier mapping
		classifierMapping.initializer += mapping.wheres.map[dispatchCreateInitializer].filterNull

		// create new correspondence predicate for the feature mapping
		val correspondencePredicate = createReverseFeaturesCorrespondWithEClassifiers
		val correspondence = createFeatureEClassifierCorrespondence
		correspondence.feature = leftElement.getStructuralFeature
		correspondence.EClassifier = getRightSideType(parent)
		correspondencePredicate.correspondences += correspondence
		
		// get correspondences from parent. there should only be one
		correspondencePredicate.correspondences += EcoreUtil.copyAll(getMappingCorrespondences(parent))

		mir.predicates += correspondencePredicate
		classifierMapping.predicates += correspondencePredicate
		
		// map predicates for both class and feature mapping
		val mappedPredicates = mapping.whens.map [ it.dispatchCreatePredicate ].filterNull.toList
		mir.predicates += mappedPredicates
		featureMapping.predicates += mappedPredicates
		classifierMapping.predicates += mappedPredicates
		
		// call recursively for each child mapping
		mapping.withs.forEach [
			mapFeatureMapping(it, mir, classifierMapping)
		]
	}
	
	/**
	 * Returns the correspondences of the mapping if it has any,
	 * or an empty list if not.
	 */
	def getMappingCorrespondences(Mapping mapping) {
		mapping
			.predicates
			.filter(ReverseFeaturesCorrespondWithEClassifiers)
			.head
			?.correspondences
		?:
			#[]
	}
	
	def createEClassifierFeature(FeatureCall feature) {
		val result = createEClassifierFeature
		result.feature = feature.getStructuralFeature
		result.EClassifier = feature.getTypeRecursive
		
		return result
	}
	
	/**
	 * Returns the type of the right side of the {@link Mapping} 
	 */
	def dispatch getRightSideType(FeatureMapping mapping) {
		return mapping.right.EClassifier
	}
	def dispatch getRightSideType(ClassifierMapping mapping) {
		return mapping.right
	}
	
	def dispatch getLeftSideType(FeatureMapping mapping) {
		return mapping.left.EClassifier
	}
	def dispatch getLeftSideType(ClassifierMapping mapping) {
		return mapping.left
	}
	
}