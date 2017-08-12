package tools.vitruv.dsls.commonalities.generator

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.commonalities.language.CommonalityFile

import static com.google.common.base.Preconditions.*
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.Collections
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.generator.IFileSystemAccess2
import static extension tools.vitruv.dsls.commonalities.generator.GeneratorConstants.*
import tools.vitruv.dsls.commonalities.language.ConceptDeclaration

class CommonalitiesLanguageGenerationContext {
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var CommonalityFile commonalityFile
	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var IFileSystemAccess2 fsa
	var Map<CommonalityFile, EClass> intermediateModelClassCache = new HashMap
	var Map<String, EPackage> intermediateModelPackageCache = new HashMap

	@Accessors(PACKAGE_SETTER, PACKAGE_GETTER)
	var Iterable<String> generatedConcepts = Collections.emptyList

	def package getGeneratedIntermediateModelClass(CommonalityFile commonalityFile) {
		intermediateModelClassCache.computeIfAbsent(commonalityFile, [
			commonalityFile.concept.generatedIntermediateModelPackage.EClassifiers
				.findFirst [name == commonalityFile.intermediateModelClassName] as EClass
			])
	}

	def package getChangeClass(CommonalityFile commonalityFile) {
		commonalityFile.generatedIntermediateModelClass
	}
	
	def package getGeneratedIntermediateModelPackage(ConceptDeclaration concept) {
		concept.name.generatedIntermediateModelPackage
	}
	
	def package getCommonality() {
		commonalityFile.commonality
	}

	def package getGeneratedIntermediateModelPackage(String conceptName) {
		intermediateModelPackageCache.computeIfAbsent(conceptName, [
			resourceSet.getResource(getIntermediateModelOutputUri(conceptName), false).contents.head as EPackage
		])
	}

	def private ResourceSet getResourceSet() {
		commonalityFile.eResource.resourceSet
	}

	def package reportGeneratedConcept(String conceptName, EPackage conceptModel) {
		intermediateModelPackageCache.put(conceptName, conceptModel)
	}

	def package getConceptDomain(CommonalityFile commonalityFile) {
		val conceptName = commonalityFile.concept.name
		val ePackage = conceptName.generatedIntermediateModelPackage
		checkState(ePackage !== null, '''No ePackage was registered for the concept “«conceptName»”!''')
		new ConceptDomain(conceptName, ePackage)
	}
	
	def package getIntermediateModelOutputUri(String conceptName) {
		fsa.getURI(conceptName + MODEL_OUTPUT_FILE_EXTENSION)
	}
}
