package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.commonalities.language.CommonalityFile

import static com.google.common.base.Preconditions.*
import java.util.function.Function
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Collections

@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
class CommonalitiesLanguageGenerationContext {
	var Function<CommonalityFile, EClass> intermediateModelClassFunction
	var Function<String, EPackage> intermediateModelPackageFunction
	var Iterable<String> generatedConcepts = Collections.emptyList 
	
	def protected generatedIntermediateModelClass(CommonalityFile commonalityFile) {
		intermediateModelClassFunction.apply(commonalityFile)
	}

	def protected getChangeClass(CommonalityFile commonalityFile) {
		commonalityFile.generatedIntermediateModelClass
	}
	
	def protected generatedIntermediateModelPackage(String conceptName) {
		intermediateModelPackageFunction.apply(conceptName)
	}

	def protected getConceptDomain(CommonalityFile commonalityFile) {
		val conceptName = commonalityFile.concept.name
		val ePackage = generatedIntermediateModelPackage(conceptName)
		checkState(ePackage !== null, '''No ePackage was registered for the concept “«conceptName»”!''')
		new ConceptDomain(conceptName, ePackage)
	}
}
