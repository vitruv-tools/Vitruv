package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ClassMapping
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator

/**
 * Generates the intermediate language form of the model
 */
class MIRIntermediateLanguageGenerator extends JvmModelGenerator {
	
	@Inject IGeneratorStatus generatorStatus;
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		// generate Java class hierarchy for predicates
		super.doGenerate(input, fsa)
		
		val resourcePath = input.URI
		input.contents.filter(typeof(MIRFile)).forEach[persist(it, resourcePath)]
	}
	
	def persist(MIRFile mirfile, URI resourcePath) {
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
	}
	
	def void mapMIRFileToMIR(MIRFile mirfile, MIR mir) {
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
				val jvmName = generatorStatus.GetJvmName(it)
				
				if (jvmName == null)
					null
				else {
					val predicate = MIRintermediateFactory.eINSTANCE.createPredicate
					predicate.className = jvmName
					mir.predicates += predicate
					
					predicate
				}
					
			].filterNull
			
		mir.classMappings += result
	}
	
}