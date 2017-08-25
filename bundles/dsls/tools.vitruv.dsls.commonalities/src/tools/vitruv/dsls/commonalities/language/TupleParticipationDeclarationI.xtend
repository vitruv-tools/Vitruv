package tools.vitruv.dsls.commonalities.language

import tools.vitruv.dsls.commonalities.language.impl.TupleParticipationDeclarationImpl

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class TupleParticipationDeclarationI extends TupleParticipationDeclarationImpl implements ParticipationNameDerived {
	
	override getName() {
		ParticipationNameDerived.super.getName()
	}
	
	override getClasses() {
		if (classes === null) {
			val classes = super.getClasses()
			classes += getParts.flatMap [participationClasses]
		}
		classes
	}
	
	override eIsSet(int eFeatureId) {
		if  (eFeatureId === LanguagePackage.PARTICIPATION_DECLARATION__CLASSES) {
			getClasses()
		}
		super.eIsSet(eFeatureId)
	}
}