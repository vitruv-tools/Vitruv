package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureCall
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.TypedElement
import java.util.Collection
import java.util.Collections
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.EMFHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel.MIRJvmModelInferrer.*

/**
 * Generates the intermediate language form of the model
 * @author Dominik Werle
 */
class MIRIntermediateLanguageGenerator implements IGenerator {
	private static final Logger logger = Logger.getLogger(MIRIntermediateLanguageGenerator)
	public static final String DEFAULT_CLASS_NAME = "Concrete" + Change2CommandTransforming.simpleName

	@Inject IGeneratorStatus generatorStatus;

	static extension MIRintermediateFactory mirILfactory = MIRintermediateFactory.eINSTANCE

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

		// convention: the mappings inside are all ordered, so
		// the first mapped element is part of the first package, and so on.
		// it is currently unclear what happens, if there are more than
		// two metamodels.
		mir.packages.clear
		mir.packages += mirfile.imports.map[it.package]

		mirfile.mappings.forEach[it.mapMapping(mir)]
	}

	def ClassMapping mapMapping(Mapping mapping, MIR mir) {
		val fstMapping = mapMappingAndReverse(mapping, mir)
		
		val source = mir.packages.get(0)
		val target = mir.packages.get(1)
		
		val withs = mapping.constraints?.withs
		if (withs != null) {
			val correctTypes = withs.checkCorrectTypes
			if (!correctTypes) {
				throw new RuntimeException("Erroneous types for features.")
			}
			
			withs
				.featureMappingCandidates
				.forEach [
					val classMappingFromFeature = mapMapping(it, mir)
					classMappingFromFeature
						.addFeatureConditions(it, fstMapping, mir, source, target)
					classMappingFromFeature.opposite
						.addFeatureConditions(it, fstMapping.opposite, mir, target, source)
				]
				
			withs
				.postConditionCandidates
				.forEach [
					fstMapping
						.addFeaturePostCondition(it, source, target)
					fstMapping.opposite
						.addFeaturePostCondition(it, target, source)
				]
		}
		
		return fstMapping
	}
	
	def void addFeaturePostCondition(ClassMapping parent, FeatureMapping featureMapping, EPackage source, EPackage target) {
		val pc = createWithAttributePostCondition
		
		val leftFeature = featureMapping
						.getMappedElementInPackage(source)
						.collectFeatureCalls
						.claimExactlyOne

		pc.leftVariableName =
			leftFeature
				.ref
				.tryGetName
				
		pc.left =
			leftFeature
				.structuralFeature
				.requireType(EAttribute)
				
		val rightFeature = featureMapping
						.getMappedElementInPackage(target)
						.collectFeatureCalls
						.claimExactlyOne
		pc.rightVariableName =
			rightFeature
				.ref
				.tryGetName
		
		pc.right =
			rightFeature
				.structuralFeature
				.requireType(EAttribute)
				
		parent.postconditions += pc
	}
	
	/**
	 * @see MIRIntermediateLanguageGenerator#checkCorrectTypes
	 */
	def private static getFeatureMappingCandidates(Collection<Mapping> mappings) {
		mappings
			.filter(FeatureMapping)
			.filter[
				(it.mappedElements.size == 2) &&
				((it.mappedElements.forall[ mE |
					mE.collectFeatureCalls.forall[ fc |
						(fc.typeRecursive instanceof EClass)
						&& (fc.structuralFeature instanceof EReference)
					]
				]))
			]
	}
	
	/**
	 * @see MIRIntermediateLanguageGenerator#checkCorrectTypes
	 */
	def private static getPostConditionCandidates(Collection<Mapping> mappings) {
		mappings
			.filter(FeatureMapping)
			.filter[
				it.mappedElements.forall[ mE |
					val featureCalls = mE.collectFeatureCalls
					
					return ((featureCalls.size == 1)
						&& (featureCalls.forall[structuralFeature instanceof EAttribute]))
				]
			]
	}
	
	/**
	 * Checks that either
	 * <ul>
	 * 	<li>both sides of the mapping are {@link EAttribute EAttributes}
	 * 		and only one feature call, or that</li>
	 * 	<li>both sides only contain {@link EReference EReferences}
	 * 	to {@link EClass EClasses}.</li>
	 * </ul> 
	 */
	def private static checkCorrectTypes(Collection<Mapping> mappings) {
		(mappings.forall[it instanceof FeatureMapping])
			&&
		(mappings.filter(FeatureMapping).forall[
			(it.mappedElements.size == 2) &&
			((it.mappedElements.forall[ mE |
				mE.collectFeatureCalls.forall[ fc |
					(fc.typeRecursive instanceof EClass)
					&& (fc.structuralFeature instanceof EReference)
				]
			])
				||
			(
				it.mappedElements.forall[ mE |
					val featureCalls = mE.collectFeatureCalls
					
					return ((featureCalls.size == 1)
						&& (featureCalls.forall[structuralFeature instanceof EAttribute]))
				]
			))
		])
	}
	
	def ClassMapping mapMappingAndReverse(Mapping mapping, MIR mir) {
		val source = mir.packages.get(0)
		val target = mir.packages.get(1)
		
		val fstMapping = mapping.mapToClassMapping(mir, source, target)
		val sndMapping = mapping.mapToClassMapping(mir, target, source)

		fstMapping.opposite = sndMapping
		sndMapping.opposite = fstMapping
		
		fstMapping
	}
	
	def ClassMapping mapToClassMapping(
		Mapping mapping, MIR mir, EPackage source, EPackage target) {
		
		val newMapping = createClassMapping
		newMapping.left  = mapping.getMappedElementInPackage(source).createNamedEClass
		newMapping.right = mapping.getMappedElementInPackage(target).createNamedEClass

		mir.classMappings += newMapping

		if (mapping.constraints != null) {
			if (mapping.constraints.whenwhere != null) {
				val newPredicate = createWhenWhereJavaPredicate(mapping, target)
				newMapping.predicates += newPredicate
				mir.predicates += newPredicate
			}
			
			newMapping.postconditions +=
				mapping.constraints.withBlocks.map [
					createWithBlockPostCondition(source)
				]
		}

		return newMapping
	}
	
	def private createWithBlockPostCondition(EObject context, EPackage source) {
		if (generatorStatus.getJvmName(context) == null) {
			logger.debug("No JVM class name found for " + context.toString)
		}
		
		val newPostcondition = createWithBlockPostCondition
		newPostcondition.assignmentExpression = createStaticMethodCall(
			generatorStatus.getJvmName(context)
				+ "." + source.assignmentMethodName,
			context.createParameterList
			  .map[tryGetName])
			  
		newPostcondition
	}
	
	def private createWhenWhereJavaPredicate(Mapping mapping, EPackage target) {
		val newPredicate = createWhenWhereJavaPredicate
		newPredicate.assignmentExpression = createStaticMethodCall(
			generatorStatus.getJvmName(mapping.constraints.whenwhere)
				+ "." + target.assignmentMethodName,
			mapping.constraints.whenwhere
				.createParameterList
				// filter out the element to map with, since it can't be
				// checked in the predicate
				.filter[it != mapping.getMappedElementInPackage(target)]
				.map[tryGetName]
				.toList
		)
		
		newPredicate.checkExpression = createStaticMethodCall(
			generatorStatus.getJvmName(mapping.constraints.whenwhere)
				+ "." + target.equalitiesMethodName,
			mapping.constraints.whenwhere
				.createParameterList
				// filter out the element to map with, since it can't be
				// checked in the predicate
				.filter[it != mapping.getMappedElementInPackage(target)]
				.map[tryGetName]
				.toList					
		)
				
		newPredicate
	}
	
	def private static createStaticMethodCall(String methodFQN, List<String> parameterNames) {
		val smc = createStaticMethodCall
		smc.methodFQN = methodFQN
		smc.parameterNames.addAll(parameterNames)
		smc
	}
	
	def addFeatureConditions(
		ClassMapping classMapping,
		FeatureMapping featureMapping, ClassMapping parent,
		MIR mir, EPackage source, EPackage target) {
			
		val newMapping = createReferenceMapping
		mir.featureMappings += newMapping
		
		newMapping.left += featureMapping.getMappedElementInPackage(source)
			                             .createEClassFeatures
			                             
		newMapping.right += featureMapping.getMappedElementInPackage(target)
			                              .createEClassFeatures
			                              
		newMapping.parent = parent
		
		// also automatically sets opposite classMapping.featureMapping
		newMapping.classMapping = classMapping
	}
	
	private def createEClassFeatures(TypedElement typedElement) {
		typedElement
			.collectFeatureCalls
			.map [
				// the type can be required, because it is checked before, same for EClass
				val namedFeatureCall = createNamedEReference
				namedFeatureCall.EClass = it.ref.typeRecursive.ensureEClass
				namedFeatureCall.EReference = requireType(it.structuralFeature, EReference)
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
	
	def TypedElement getMappedElementInPackage(Mapping mapping, EPackage pkg) {
//		mapping.mappedElements.forEach[
//			logger.debug("Mapped element: " + it.tryGetName + " (" + it.typeRecursive.getContainerHierarchy(true).map[it.toString].join(", ") + ")")
//		]
		
		val mappedElementsInPkg = 
			if (mapping instanceof edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping)
				(mapping.mappedElements.requireCollectionType(NamedEClass).filter[
					type.hasContainer(pkg)
				])
			else if (mapping instanceof FeatureMapping)
				(mapping.mappedElements.requireCollectionType(FeatureCall).filter[
					tail.EContainingClass.hasContainer(pkg)
				])
		
		if (mappedElementsInPkg.size != 1) {
			throw new RuntimeException("Mapping " + mapping + " has " + mappedElementsInPkg.size + " mapped elements in package " + pkg.name)
		}
		
		return mappedElementsInPkg.head
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
}