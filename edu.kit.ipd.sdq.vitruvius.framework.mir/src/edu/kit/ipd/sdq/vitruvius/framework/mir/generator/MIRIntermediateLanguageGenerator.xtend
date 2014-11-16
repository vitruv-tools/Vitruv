package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.FeatureMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

/**
 * Generates the intermediate language form of the model
 */
class MIRIntermediateLanguageGenerator implements IGenerator {
	
	@Inject IGeneratorStatus generatorStatus;
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		// generate Java class hierarchy for predicates
		
		val resourcePath = input.URI
		input.contents.filter(typeof(MIRFile)).forEach[transform(it, resourcePath)]
	}
	
	def transform(MIRFile mirfile, URI resourcePath) {
		val reg = Resource.Factory.Registry.INSTANCE
		val m = reg.extensionToFactoryMap
		val xmifactory = new XMIResourceFactoryImpl
		m.put("il", xmifactory)
		
		val mirResourceSet = new ResourceSetImpl()
		val outResource = mirResourceSet.createResource(resourcePath.trimFileExtension.appendFileExtension("il"));
		
		val mir = MIRintermediateFactory.eINSTANCE.createMIR

		mirfile.mapMIRFileToMIR(mir)
		
		outResource.contents.add(mir)		
		outResource.save(Collections.EMPTY_MAP)
		
		generatorStatus.put(mirfile, mir)
	}
	
	def void mapMIRFileToMIR(MIRFile mirfile, MIR mir) {
		mir.configuration = MIRintermediateFactory.eINSTANCE.createConfiguration
		mir.configuration.package = mirfile.generatedPackage
		mir.configuration.type =
			if (mirfile.generatedClass != null)
				mirfile.generatedClass
			else
				"ChangeSynchronizer"
		
		mirfile.mappings
	    	   .forEach [ it.mapClassMappingToClassMapping(mir) ]
	}
	
	def void mapClassMappingToClassMapping(ClassMapping mapping, MIR mir) {
		val result = MIRintermediateFactory.eINSTANCE.createClassMapping
		
		val leftElement = mapping.mappedElements.get(0)
		val rightElement = mapping.mappedElements.get(1)
		
		result.left = leftElement.representedEClass
		result.right = rightElement.representedEClass
		
		result.predicates +=
			mapping.whens.map [
				val jvmName = generatorStatus.getJvmName(it)
				
				if (jvmName == null)
					null
				else {
					val predicate = MIRintermediateFactory.eINSTANCE.createPredicate
					predicate.checkStatement = "/* check " + jvmName + " */ false"
					mir.predicates += predicate
					predicate
				}
					
			].filterNull

		result.initializer +=
			mapping.withs.map [
				val jvmName = generatorStatus.getJvmName(it)
				
				if (jvmName == null)
					null
				else {
					val initializer = MIRintermediateFactory.eINSTANCE.createInitializer
					initializer.callStatement = "/* call " + jvmName + " */"
					initializer
				}
			].filterNull
			
		mapping.withs.forEach [
			mapFeatureMappingToFeatureMapping(mir, result)
		]
			
		mir.classMappings += result
	}
	
	def void mapFeatureMappingToFeatureMapping(FeatureMapping mapping, MIR mir,
		edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.ClassMapping parent
	) {
		val result = MIRintermediateFactory.eINSTANCE.createFeatureMapping
		
		val leftElement = mapping.mappedElements.get(0)
		val rightElement = mapping.mappedElements.get(1)
		
		result.left = leftElement.representedFeature
		result.right = rightElement.representedFeature
		
		leftElement.representedFeature.EContainingClass
		
		result.predicates += parent.predicates
		
		
		
		val correspondence = MIRintermediateFactory.eINSTANCE.createPredicate
		correspondence.checkStatement = "/* check correspondence */ false"
		
		mir.predicates += correspondence
		result.predicates += correspondence
		
		mir.featureMappings += result
	}
	
}