package tools.vitruv.testutils.metamodels

import allElementTypes2.Identified2
import allElementTypes2.impl.AllElementTypes2FactoryImpl
import tools.vitruv.testutils.activeannotations.WithGeneratedRandomIds

@WithGeneratedRandomIds(identifierMetaclass=Identified2)
class RandomIdAllElementTypes2Factory extends AllElementTypes2FactoryImpl {
	override createRoot2() {
		return super.createRoot2();
	}
}
