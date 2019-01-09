package tools.vitruv.testutils.metamodels

import tools.vitruv.testutils.activeannotations.WithGeneratedRandomIds
import uml_mockup.Identified
import uml_mockup.impl.Uml_mockupFactoryImpl

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdUmlMockupFactory extends Uml_mockupFactoryImpl {
}
