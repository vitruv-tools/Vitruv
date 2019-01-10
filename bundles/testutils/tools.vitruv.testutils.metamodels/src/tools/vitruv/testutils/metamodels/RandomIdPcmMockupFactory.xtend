package tools.vitruv.testutils.metamodels

import pcm_mockup.Identified
import pcm_mockup.impl.Pcm_mockupFactoryImpl
import tools.vitruv.testutils.activeannotations.WithGeneratedRandomIds

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdPcmMockupFactory extends Pcm_mockupFactoryImpl {
}
