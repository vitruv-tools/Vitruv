package tools.vitruv.testutils.metamodels

import uml_mockup.impl.Uml_mockupFactoryImpl
import tools.vitruv.testutils.activeannotations.WithGeneratedRandomIds
import uml_mockup.Identified
import uml_mockup.Uml_mockupPackage

@WithGeneratedRandomIds(identifierMetaclass=Identified, identifierFeature=Uml_mockupPackage.IDENTIFIED__ID)
class RandomIdUmlMockupFactory extends Uml_mockupFactoryImpl {
}
