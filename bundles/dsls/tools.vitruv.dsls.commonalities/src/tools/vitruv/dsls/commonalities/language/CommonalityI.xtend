package tools.vitruv.dsls.commonalities.language

import org.eclipse.emf.common.util.DelegatingEList.UnmodifiableEList
import tools.vitruv.dsls.commonalities.language.elements.Classifier
import tools.vitruv.dsls.commonalities.language.elements.MostSpecificType
import tools.vitruv.dsls.commonalities.language.impl.CommonalityImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityI extends CommonalityImpl {
	
	override basicGetPackageLikeContainer() {
		domain
	}
	
	override getAllMembers() {
		new UnmodifiableEList(attributes)
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
	
	override toString() {
		'''«packageLikeContainer»:«name»'''
	}
}