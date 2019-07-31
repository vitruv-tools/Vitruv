package tools.vitruv.dsls.mappings.tests.pcmuml

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import static tools.vitruv.dsls.mappings.tests.pcmuml.MappingConstants.*
import org.eclipse.uml2.uml.Package
import static org.junit.Assert.assertTrue


class RepositoryTest extends PcmUmlClassTest{
	
	@Test
	def void testCreateRepository(){
		var pcmRepository = RepositoryFactory.eINSTANCE.createRepository()
		val name = 'model/Repository.repository'
		createAndSynchronizeModel(name, pcmRepository)
		pcmRepository = reloadResourceAndReturnRoot(pcmRepository) as Repository
		assertModelExists(name)
		helper.printCorrModel
		val repositoryPkg = helper.getModifiableCorr(pcmRepository, Package, tagRepositoryXrepositoryPkg)
		assertTrue(repositoryPkg !==null)

	}
	
	
}