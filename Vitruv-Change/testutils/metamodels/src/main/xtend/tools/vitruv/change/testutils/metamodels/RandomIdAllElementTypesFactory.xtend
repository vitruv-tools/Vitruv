package tools.vitruv.change.testutils.metamodels

import allElementTypes.Identified
import allElementTypes.impl.AllElementTypesFactoryImpl
import tools.vitruv.change.utils.activeannotations.WithGeneratedRandomIds

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdAllElementTypesFactory extends AllElementTypesFactoryImpl {
}
