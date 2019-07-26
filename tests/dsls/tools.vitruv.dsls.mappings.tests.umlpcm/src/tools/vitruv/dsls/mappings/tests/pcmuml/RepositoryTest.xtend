package tools.vitruv.dsls.mappings.tests.pcmuml

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import static tools.vitruv.dsls.mappings.tests.pcmuml.MappingConstants.*
import static org.junit.Assert.assertTrue

class RepositoryTest extends PcmUmlClassTest{
	
	@Test
	def void testCreateRepository(){
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		
		val repositoryPkg = helper.getModifiableCorr(pcmRepository, Package, tagRepositoryXrepositoryPkg)
		assertTrue(repositoryPkg !==null)

	}
	
	
}