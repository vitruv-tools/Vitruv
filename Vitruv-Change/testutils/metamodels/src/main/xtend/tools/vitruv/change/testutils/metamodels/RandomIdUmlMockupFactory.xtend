package tools.vitruv.change.testutils.metamodels

import tools.vitruv.change.utils.activeannotations.WithGeneratedRandomIds
import uml_mockup.Identified
import uml_mockup.impl.Uml_mockupFactoryImpl

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdUmlMockupFactory extends Uml_mockupFactoryImpl {
}
