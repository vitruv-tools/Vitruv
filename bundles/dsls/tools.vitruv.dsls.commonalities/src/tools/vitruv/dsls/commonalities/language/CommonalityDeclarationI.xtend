package tools.vitruv.dsls.commonalities.language

import org.eclipse.emf.common.util.DelegatingEList.UnmodifiableEList
import tools.vitruv.dsls.commonalities.language.elements.Classifier
import tools.vitruv.dsls.commonalities.language.elements.MostSpecificType
import tools.vitruv.dsls.commonalities.language.impl.CommonalityDeclarationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityDeclarationI extends CommonalityDeclarationImpl {
	
	override basicGetPackageLikeContainer() {
		domain
	}
	
	override getAllMembers() {
		new UnmodifiableEList(attributes)
	}
	
	override toString() {
		'''«packageLikeContainer»:«name»'''
	}
	
	def dispatch isSuperTypeOf(Classifier classifier) {
		classifier == this
	}
	
	def dispatch isSuperTypeOf(MostSpecificType mostSpecificType) {
		true
	}
	
	override basicGetDomain() {
		containingCommonalityFile.concept
	}
	
}