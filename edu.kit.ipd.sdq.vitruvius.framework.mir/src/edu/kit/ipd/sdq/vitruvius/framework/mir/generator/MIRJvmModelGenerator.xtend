package edu.kit.ipd.sdq.vitruvius.framework.mir.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.xbase.XbaseFactory
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.IWithBlockInferrer
import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.WhenWhereInferrer

class MIRJvmModelGenerator extends JvmModelGenerator {
	@Inject private WhenWhereInferrer whenWhereInferrer
	@Inject IGeneratorStatus generatorStatus
	
	@Inject private IWithBlockInferrer withBlockInferrer

	override doGenerate(Resource input, IFileSystemAccess fsa) {
		val model = input.contents.filter(MIRFile).head
		
		generatorStatus.whenWheresToInfer.forEach [ whenWhereInferrer.inferBlock(it) ]
		generatorStatus.withBlocksToInfer.forEach [ withBlockInferrer.infer(it) ]
		
		super.doGenerate(input, fsa)
	}
}