package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.MIRHelper
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIR
import edu.kit.ipd.sdq.vitruvius.framework.mir.intermediate.MIRintermediate.MIRintermediateFactory
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

/**
 * Generates the intermediate language form of the model
 * @author Dominik Werle
 */
class MIRIntermediateLanguageGenerator implements IGenerator {
	private static final Logger logger = Logger.getLogger(MIRIntermediateLanguageGenerator)
	private static final String DEFAULT_CLASS_NAME = "ChangeSynchronizer"
	
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
				DEFAULT_CLASS_NAME
				
		if (mirfile.imports.size != 2) {
			logger.error("MIR currently only supports exactly two metamodels")
			throw new IllegalStateException("MIR currently only supports exactly two metamodels");
		}
		
		mir.packages.clear
		mir.packages += mirfile.imports.map[it.package]
				
//		mirfile.mappings
//	    	   .forEach [ it.mapClassifierMapping(mir) ]
	}
}