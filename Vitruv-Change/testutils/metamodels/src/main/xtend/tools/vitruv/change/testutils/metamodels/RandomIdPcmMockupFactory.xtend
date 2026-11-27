package tools.vitruv.change.testutils.metamodels

import pcm_mockup.Identified
import pcm_mockup.impl.Pcm_mockupFactoryImpl
import tools.vitruv.change.utils.activeannotations.WithGeneratedRandomIds

@WithGeneratedRandomIds(identifierMetaclass=Identified)
class RandomIdPcmMockupFactory extends Pcm_mockupFactoryImpl {
}
