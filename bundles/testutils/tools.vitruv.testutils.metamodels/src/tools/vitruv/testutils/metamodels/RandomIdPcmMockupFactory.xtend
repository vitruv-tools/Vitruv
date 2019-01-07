package tools.vitruv.testutils.metamodels

import pcm_mockup.impl.Pcm_mockupFactoryImpl
import tools.vitruv.testutils.activeannotations.WithGeneratedRandomIds
import pcm_mockup.Identified
import pcm_mockup.Pcm_mockupPackage

@WithGeneratedRandomIds(identifierMetaclass=Identified, identifierFeature=Pcm_mockupPackage.IDENTIFIED__ID)
class RandomIdPcmMockupFactory extends Pcm_mockupFactoryImpl {
}
