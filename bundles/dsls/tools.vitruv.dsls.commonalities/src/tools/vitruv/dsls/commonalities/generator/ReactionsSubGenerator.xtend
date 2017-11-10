package tools.vitruv.dsls.commonalities.generator

package abstract class ReactionsSubGenerator<ThisType extends ReactionsSubGenerator<ThisType>> {
	protected extension ReactionsGenerationContext reactionsGenerationContext
	protected extension GenerationContext generationContext
	
	def package ThisType withGenerationContext(ReactionsGenerationContext context) {
		this.reactionsGenerationContext = context
		this.generationContext = reactionsGenerationContext.generationContext
		this as ThisType
	}
}