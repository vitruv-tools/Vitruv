package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.Repository

import static org.junit.Assert.assertEquals

import static extension edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class EJBPackageMappingTest extends EJBJaMoPP2PCMTransformationTest {
	
	@Test
	def testCreatePackage(){
		//test
		super.addRepoContractsAndDatatypesPackage()
				
		//check: main package needs to correspond to a repository
		val correspondingRepo = this.correspondenceModel.getCorrespondingEObjectsByType(this.mainPackage, Repository).claimOne
		assertEquals("Corresponding Repository has not the same name as the main package", correspondingRepo.entityName, this.mainPackage.name)
	}
	
}