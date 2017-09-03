package tools.vitruv.dsls.commonalities.language

import java.util.Collections
import org.eclipse.emf.common.util.DelegatingEList.UnmodifiableEList
import tools.vitruv.dsls.commonalities.language.impl.ConceptDeclarationImpl

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ConceptDeclarationI extends ConceptDeclarationImpl {
	
	override getMetaclasses() {
		new UnmodifiableEList(Collections.singletonList(containingCommonalityFile.commonality))
	}
	
	override toString() {
		'''«name»'''
	}
	
}