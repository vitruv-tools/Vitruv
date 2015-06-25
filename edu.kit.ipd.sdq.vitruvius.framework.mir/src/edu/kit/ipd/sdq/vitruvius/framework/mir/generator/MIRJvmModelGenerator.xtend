package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.IWithBlockInferrer
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.InvariantInferrerProviding
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.WhenWhereInferrer
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator

class MIRJvmModelGenerator extends JvmModelGenerator {
	@Inject private WhenWhereInferrer whenWhereInferrer
	@Inject IGeneratorStatus generatorStatus
	
	@Inject private IWithBlockInferrer withBlockInferrer
	@Inject private InvariantInferrerProviding invariantInferrer

	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val model = input.contents.filter(MIRFile).head
		
		generatorStatus.whenWheresToInfer.forEach [ whenWhereInferrer.inferBlock(it) ]
		generatorStatus.withBlocksToInfer.forEach [ withBlockInferrer.infer(it) ]
		generatorStatus.invariantsToInfer.forEach [ invariantInferrer.infer(it) ]
		
		super.doGenerate(input, fsa)
	}
}