package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjavaejb

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.assertEquals

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class EJBPackageMappingTest extends EJBJaMoPP2PCMTransformationTest {
	
	@Test
	def testCreatePackage(){
		//test
		super.addRepoContractsAndDatatypesPackage()
				
		//check: main package needs to correspond to a repository
		val correspondingRepo = this.correspondenceInstance.getCorrespondingEObjectsByType(this.mainPackage, Repository).claimOne
		assertEquals("Corresponding Repository has not the same name as the main package", correspondingRepo.entityName, this.mainPackage.name)
	}
	
}