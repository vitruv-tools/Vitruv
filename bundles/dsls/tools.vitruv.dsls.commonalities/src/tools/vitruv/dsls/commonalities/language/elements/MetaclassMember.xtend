package tools.vitruv.dsls.commonalities.language.elements

import org.eclipse.emf.ecore.EClassifier

interface MetaclassMember extends Named {
	def EClassifier getType()
}