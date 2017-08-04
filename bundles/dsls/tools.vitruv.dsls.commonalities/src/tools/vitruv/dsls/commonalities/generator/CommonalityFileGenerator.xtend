package tools.vitruv.dsls.commonalities.generator;

import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import tools.vitruv.dsls.commonalities.language.CommonalityFile;
import tools.vitruv.dsls.commonalities.language.elements.VitruvDomainAdapter
import tools.vitruv.framework.domains.VitruvDomain

abstract package class CommonalityFileGenerator {

	protected CommonalityFile commonalityFile
	protected IFileSystemAccess2 fsa
	protected extension IGeneratorContext context
	
	protected extension CommonalitiesLanguageGenerationContext generationContext

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
	
	def CommonalityFileGenerator withGenerationContext(CommonalitiesLanguageGenerationContext generationContext) {
		this.generationContext = generationContext
		this
	}

	def protected getCommonality() {
		return commonalityFile.commonality
	}
	
	def protected dispatch getVitruvDomain(CommonalityFile commonalityFile) {
		// TODO
		null as VitruvDomain
	}
	
	def protected dispatch getVitruvDomain(VitruvDomainAdapter adapter) {
		adapter.wrapped
	}
}
