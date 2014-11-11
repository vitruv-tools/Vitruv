package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import org.eclipse.xtext.generator.IGenerator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.Collections
import org.eclipse.emf.common.util.URI
import com.google.inject.Inject
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
		
		var mirResourceSet = new ResourceSetImpl()
		var outResource = mirResourceSet.createResource(resourcePath.trimFileExtension.appendFileExtension("il"));
		outResource.contents.add(mirfile)
		
		outResource.save(Collections.EMPTY_MAP)
	}
	
}