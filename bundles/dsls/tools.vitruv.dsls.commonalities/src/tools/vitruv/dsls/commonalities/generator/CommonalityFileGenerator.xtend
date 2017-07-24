package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import tools.vitruv.dsls.commonalities.language.CommonalityFile;

abstract package class CommonalityFileGenerator  {

	protected CommonalityFile commonalityFile
	protected IFileSystemAccess2 fsa
	protected IGeneratorContext context
	
	def abstract void generate()

	def CommonalityFileGenerator forCommonalityFile(CommonalityFile commonalityFile) {
		this.commonalityFile = commonalityFile
		this
	}
	
	def CommonalityFileGenerator withFileSystemAccess(IFileSystemAccess2 fsa) {
		this.fsa = fsa
		this
	}
	
	def CommonalityFileGenerator withContext(IGeneratorContext context) {
		this.context = context
		this
	}

}
