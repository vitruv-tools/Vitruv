package edu.kit.ipd.sdq.vitruvius.tests.components

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.api.MappedCorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRMappingRealization
import edu.kit.ipd.sdq.vitruvius.framework.vsum.VSUMImpl
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Test
import pcm_mockup.Interface
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import uml_mockup.UPackage

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*

class CorrespondenceTest extends VSUMTest {
	static final String interfaceCRefName = "interfaces"
	static final Logger LOGGER = Logger.getLogger(CorrespondenceTest.getSimpleName())

	@Test def void testAllInCommand() {
		val VSUMImpl vsum = testMetaRepositoryAndVSUMCreation()
		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand([testAll(vsum) return null], vsum)
	}

	def private void testAll(VSUMImpl vsum) {
		var Repository repo = testLoadObject(vsum, getPCMInstanceUri(), Repository)
		var UPackage pkg = testLoadObject(vsum, getUMLInstanceURI(), UPackage)
		var InternalCorrespondenceInstance correspondenceInstance = testCorrespondenceInstanceCreation(vsum)
		assertFalse(correspondenceInstance.hasCorrespondences())
		var Correspondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceInstance)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, correspondenceInstance, repo2pkg)
		var Interface repoInterface = testHasCorrespondences(repo, pkg, correspondenceInstance)
		testSimpleRemove(pkg, correspondenceInstance, repo2pkg, repoInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testRecursiveRemove(repo, pkg, correspondenceInstance, repo2pkg) // now the correspondence instance should be empty
		// recreate the same correspondence as before
		repo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceInstance) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testCreateRepo2PkgCorrespondenceAndUpdateTUID(repo, pkg, correspondenceInstance, repo2pkg)
		correspondenceInstance.removeCorrespondencesOfEObjectAndChildrenOnBothSides(pkg) // now the correspondence instance should be empty
		testCorrespondencePersistence(vsum, repo, pkg, correspondenceInstance)
	}

	@Test def void testCorrespondenceUpdate() {
		val VSUMImpl vsum = testMetaRepositoryAndVSUMCreation()
		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand([ // create vsum and Repo and UPackage
			var Repository repo = testLoadObject(vsum, getPCMInstanceUri(), Repository)
			var UPackage pkg = testLoadObject(vsum, getUMLInstanceURI(), UPackage)
			// create correspondence
			var InternalCorrespondenceInstance correspondenceInstance = testCorrespondenceInstanceCreation(vsum)
			correspondenceInstance.createAndAddCorrespondence(repo, pkg)
			LOGGER.
				trace('''Before we remove the pkg from the resource it has the tuid '«»«correspondenceInstance.calculateTUIDFromEObject(pkg)»'.''')
			removePkgFromFileAndUpdateCorrespondence(pkg, correspondenceInstance)
			LOGGER.
				trace('''After removing the pkg from the resource it has the tuid '«»«correspondenceInstance.calculateTUIDFromEObject(pkg)»'.''')
			saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceInstance)
			LOGGER.
				trace('''After adding the pkg to the new resource it has the tuid '«»«correspondenceInstance.calculateTUIDFromEObject(pkg)»'.''')
			assertRepositoryCorrespondences(repo, correspondenceInstance)
			return null
		], vsum)
	}

	@Test def void testMoveRootEObjectBetweenResource() {
		val VSUMImpl vsum = testMetaRepositoryAndVSUMCreation()
		EMFCommandBridge.createAndExecuteVitruviusRecordingCommand([
			var Repository repo = testLoadObject(vsum, getPCMInstanceUri(), Repository)
			var UPackage pkg = testLoadObject(vsum, getUMLInstanceURI(), UPackage)
			// create correspondence
			var InternalCorrespondenceInstance correspondenceInstance = testCorrespondenceInstanceCreation(vsum)
			correspondenceInstance.createAndAddCorrespondence(repo, pkg) // execute the test
			LOGGER.
				trace('''Before we remove the pkg from the resource it has the tuid '«»«correspondenceInstance.calculateTUIDFromEObject(pkg)»'.''')
			moveUMLPackageTo(pkg, getTmpUMLInstanceURI(), vsum, correspondenceInstance)
			moveUMLPackageTo(pkg, getNewUMLInstanceURI(), vsum, correspondenceInstance)
			assertRepositoryCorrespondences(repo, correspondenceInstance)
			return null
		], vsum)
	}

	def private void assertRepositoryCorrespondences(Repository repo,
		InternalCorrespondenceInstance correspondenceInstance) {
		// get the correspondence of repo
		var Set<Correspondence> correspondences = correspondenceInstance.getCorrespondences(repo.toList)
		assertEquals("Only one correspondence is expected for the repository.", 1, correspondences.size())
		for (Correspondence correspondence : correspondences) {
			assertTrue("Correspondence is not from the type EObjectCorrespondence",
				correspondence instanceof Correspondence)
			var Correspondence eoc = correspondence
			LOGGER.
				info('''EObject with TUID: «eoc.getElementATUID()» corresponds to EObject with TUID: «eoc.getElementBTUID()»''')
			var EObject a = correspondenceInstance.resolveEObjectFromTUID(eoc.getElementATUID())
			var EObject b = correspondenceInstance.resolveEObjectFromTUID(eoc.getElementBTUID())
			assertNotNull("Left Object is null", a)
			assertNotNull("Right Object is null", b)
			LOGGER.info('''A: «a» corresponds to B: «b»''')
		}

	}

	def private void moveUMLPackageTo(UPackage pkg, String string, VSUMImpl vsum,
		InternalCorrespondenceInstance correspondenceInstance) {
		saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceInstance)
	}

	def private void saveUPackageInNewFileAndUpdateCorrespondence(VSUMImpl vsum, UPackage pkg,
		InternalCorrespondenceInstance correspondenceInstance) {
		var TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(pkg)
		var VURI newVURI = VURI.getInstance(getNewUMLInstanceURI())
		vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(newVURI, pkg, oldTUID)
		correspondenceInstance.updateTUID(oldTUID, pkg)
	}

	def private String getNewUMLInstanceURI() {
		return '''«getCurrentProjectModelFolder()»MyNewUML.uml_mockup'''
	}

	def private String getTmpUMLInstanceURI() {
		return '''«getCurrentProjectFolderName()»MyTmpUML.uml_mockup'''
	}

	def private void removePkgFromFileAndUpdateCorrespondence(UPackage pkg,
		InternalCorrespondenceInstance correspondenceInstance) {
		var TUID oldTUID = correspondenceInstance.calculateTUIDFromEObject(pkg)
		EcoreUtil.remove(pkg)
		correspondenceInstance.updateTUID(oldTUID, pkg)
	}

	def private void testCorrespondencePersistence(VSUMImpl vsum, Repository repo, UPackage pkg,
		CorrespondenceInstance corresp) {
		// recreate the same correspondence as before
		var Correspondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, corresp)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		assertNotNull("Correspondence instance is null", corresp)
		if (corresp instanceof MappedCorrespondenceInstance) {
			var MIRMappingRealization mapping = new MIRMappingRealization() {
				static final long serialVersionUID = 1L

				override String getMappingID() {
					return null
				}

				override TransformationResult applyEChange(EChange eChange, Blackboard blackboard) {
					return null
				}
			}
			(corresp as MappedCorrespondenceInstance).registerMappingForCorrespondence(repo2pkg, mapping)
		}
		// save instances in order to trigger saving for CorrespondenceInstance(s)
		var VURI pcmVURI = VURI.getInstance(getPCMInstanceUri())
		vsum.saveExistingModelInstanceOriginal(pcmVURI) // create a new vsum from disk and load correspondence instance from disk
		var VSUMImpl vsum2 = testMetaRepositoryVSUMAndModelInstancesCreation()
		var Repository repo2 = testLoadObject(vsum2, getPCMInstanceUri(), Repository)
		var UPackage pkg2 = testLoadObject(vsum2, getUMLInstanceURI(), UPackage)
		var InternalCorrespondenceInstance corresp2 = testCorrespondenceInstanceCreation(vsum2)
		// do not create correspondences they have to be restored from disk
		assertTrue(corresp2.hasCorrespondences()) // obtain
		var Correspondence repo2pkg2 = corresp2.claimUniqueCorrespondence(repo2.toList, pkg2.toList)
		// test everything as if the correspondence would just have been created
		testAllClaimersAndGettersForEObjectCorrespondences(repo2, pkg2, corresp2, repo2pkg2)
	}

	def private <T extends EObject> T testLoadObject(VSUMImpl vsum, String uri, Class<T> clazz) {
		var VURI vURI = VURI.getInstance(uri)
		var ModelInstance instance = vsum.getAndLoadModelInstanceOriginal(vURI)
		var T obj = instance.getUniqueRootEObjectIfCorrectlyTyped(clazz)
		return obj
	}

	def private InternalCorrespondenceInstance testCorrespondenceInstanceCreation(VSUMImpl vsum) {
		var VURI pcmMMVURI = VURI.getInstance(PCM_MM_URI)
		var VURI umlMMVURI = VURI.getInstance(UML_MM_URI)
		var InternalCorrespondenceInstance corresp = vsum.getCorrespondenceInstanceOriginal(pcmMMVURI, umlMMVURI)
		assertNotNull(corresp)
		return corresp
	}

	def private Correspondence createRepo2PkgCorrespondence(Repository repo, UPackage pkg,
		CorrespondenceInstance corresp) {
		// until this point the correspondence instance is empty
		var Correspondence repo2pkg = corresp.createAndAddCorrespondence(repo, pkg)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		return repo2pkg
	}

	def private void testAllClaimersAndGettersForEObjectCorrespondences(Repository repo, UPackage pkg,
		CorrespondenceInstance corresp, Correspondence repo2pkg) {
		// claimAllCorrespondence is already indirectly tested via claimUniqueCorrespondence
		var Correspondence uniqueRepoCorrespondence = corresp.claimUniqueCorrespondence(repo)
		assertEquals(uniqueRepoCorrespondence, repo2pkg)
		var Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg)
		assertEquals(uniquePkgCorrespondence, repo2pkg) // claimCorrespondingEObject is already indirectly tested via
		// claimUniqueCorrespondingEObject
		var EObject correspForRepo = corresp.getCorrespondingEObjects(repo).claimOne
		assertEquals(correspForRepo, pkg)
		var EObject correspForPkg = corresp.getCorrespondingEObjects(pkg).claimOne
		assertEquals(correspForPkg, repo)
		var List<Interface> interfaces = repo.getInterfaces()
		assertEquals(interfaces.size(), 1)
		var Interface iface = interfaces.get(0)
		var Correspondence correspForIface = corresp.getCorrespondences(iface.toList).claimNotMany
		assertNull(correspForIface) // TODO test exception throwing of claimUniqueOrNullCorrespondenceForEObject
		var Set<Correspondence> allRepoCorrespondences = corresp.getCorrespondences(repo.toList)
		assertEquals(allRepoCorrespondences.size(), 1)
		assertTrue(allRepoCorrespondences.contains(repo2pkg))
		var Set<Correspondence> allPkgCorrespondences = corresp.getCorrespondences(pkg.toList)
		assertEquals(allPkgCorrespondences.size(), 1)
		assertTrue(allPkgCorrespondences.contains(repo2pkg))
		var Set<Repository> allRepoTypeCorresp = corresp.getAllEObjectsOfTypeInCorrespondences(Repository)
		assertTrue(allRepoTypeCorresp.contains(repo))
		var Set<UPackage> allPkgTypeCorresp = corresp.getAllEObjectsOfTypeInCorrespondences(UPackage)
		assertTrue(allPkgTypeCorresp.contains(pkg))
		var Set<EObject> allCorrespForRepo = corresp.getCorrespondingEObjects(repo)
		assertEquals(allCorrespForRepo.size(), 1)
		assertTrue(allCorrespForRepo.contains(pkg))
		var Set<EObject> allCorrespForPkg = corresp.getCorrespondingEObjects(pkg)
		assertEquals(allCorrespForPkg.size(), 1)
		assertTrue(allCorrespForPkg.contains(repo))
	}

	def private Interface testHasCorrespondences(Repository repo, UPackage pkg, CorrespondenceInstance corresp) {
		assertTrue(corresp.hasCorrespondences(repo.toList))
		assertTrue(corresp.hasCorrespondences(pkg.toList))
		var List<Interface> repoInterfaces = repo.getInterfaces()
		assertEquals(repoInterfaces.size(), 1)
		var Interface repoInterface = repoInterfaces.get(0)
		assertTrue(!corresp.hasCorrespondences(repoInterface.toList))
		return repoInterface
	}

	def private void testSimpleRemove(UPackage pkg, CorrespondenceInstance corresp, Correspondence repo2pkg,
		Interface repoInterface) {
		var List<uml_mockup.Interface> pkgInterfaces = pkg.getInterfaces()
		assertEquals(pkgInterfaces.size(), 1)
		var uml_mockup.Interface pkgInterface = pkgInterfaces.get(0)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// add correspondence
		corresp.createAndAddCorrespondence(repoInterface, pkgInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// 3. EOC: pcmIfac _tAgfwPxjEeOD3p0i_uuRbQ <=> umlIface _vWjxIPxjEeOD3p0i_uuRbQ
		// remove correspondence
		corresp.removeCorrespondencesOfEObjectAndChildrenOnBothSides(repoInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// check whether it is removed
		var Set<Correspondence> repoInterfaceCorresp = corresp.getCorrespondences(repoInterface.toList)
		assertTrue(repoInterfaceCorresp.isEmpty())
		var Set<Correspondence> pkgInterfaceCorresp = corresp.getCorrespondences(pkgInterface.toList)
		assertTrue(pkgInterfaceCorresp.isEmpty())
		var Set<EObject> correspForRepoInterface = corresp.getCorrespondingEObjects(repoInterface)
		assertTrue(correspForRepoInterface.isEmpty())
		var Set<EObject> correspForPkgInterface = corresp.getCorrespondingEObjects(pkgInterface)
		assertTrue(correspForPkgInterface.isEmpty())
		var Set<Interface> correspForRepoInterfaceType = corresp.getAllEObjectsOfTypeInCorrespondences(Interface)
		assertTrue(correspForRepoInterfaceType.isEmpty()) 
		var Set<uml_mockup.Interface> correspForPkgInterfaceType = corresp.
			getAllEObjectsOfTypeInCorrespondences(uml_mockup.Interface)
		assertTrue(correspForPkgInterfaceType.isEmpty())
	}

	def private void testRecursiveRemove(Repository repo, UPackage pkg, CorrespondenceInstance corresp,
		Correspondence repo2pkg) {
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		corresp.removeCorrespondencesOfEObjectAndChildrenOnBothSides(repo2pkg) // now the correspondence instance should be empty
		var Set<Correspondence> repoCorresp = corresp.getCorrespondences(repo.toList)
		assertTrue(repoCorresp.isEmpty())
		var Set<Correspondence> pkgCorresp = corresp.getCorrespondences(pkg.toList)
		assertTrue(pkgCorresp.isEmpty())
		var Set<EObject> correspForRepo = corresp.getCorrespondingEObjects(repo)
		assertTrue(correspForRepo.isEmpty())
		var Set<EObject> correspForPkg = corresp.getCorrespondingEObjects(pkg)
		assertTrue(correspForPkg.isEmpty())
		var Set<Repository> correspForRepoType = corresp.getAllEObjectsOfTypeInCorrespondences(Repository)
		assertTrue(correspForRepoType.isEmpty())
		var Set<UPackage> correspForPkgType = corresp.getAllEObjectsOfTypeInCorrespondences(UPackage)
		assertTrue(correspForPkgType.isEmpty()) // FeatureInstance repoIfaceFI = repoIfaceFIAndPkgIfaceFI.getFirst();
		// FeatureInstance pkgIfaceFI = repoIfaceFIAndPkgIfaceFI.getSecond();
		// Set<FeatureInstance> correspForRepoIfaceFI =
		// corresp.getAllCorrespondingFeatureInstances(repoIfaceFI);
		// assertTrue(correspForRepoIfaceFI.isEmpty());
		// Set<FeatureInstance> correspForPkgIfaceFI =
		// corresp.getAllCorrespondingFeatureInstances(pkgIfaceFI);
		// assertTrue(correspForPkgIfaceFI.isEmpty());
		// assertFalse(corresp.hasCorrespondences());
	}

	def private void testCreateRepo2PkgCorrespondenceAndUpdateTUID(Repository repo, UPackage pkg,
		CorrespondenceInstance corresp, Correspondence repo2pkg) {
		var Repository newRepo = Pcm_mockupFactory.eINSTANCE.createRepository()
		corresp.updateTUID(repo, newRepo)
		var Set<Correspondence> repoCorresp = corresp.getCorrespondences(repo.toList)
		assertTrue(repoCorresp.isEmpty())
		var Correspondence uniqueNewRepoCorrespondence = corresp.claimUniqueCorrespondence(newRepo)
		assertEquals(uniqueNewRepoCorrespondence, repo2pkg)
		var Correspondence uniquePkgCorrespondence = corresp.claimUniqueCorrespondence(pkg)
		assertEquals(uniquePkgCorrespondence, repo2pkg)
		var EObject correspForNewRepo = corresp.getCorrespondingEObjects(newRepo).claimOne
		assertEquals(correspForNewRepo, pkg)
		var EObject correspForPkg = corresp.getCorrespondingEObjects(pkg).claimOne
		assertEquals(correspForPkg, newRepo) // TODO is this really enough update testing?
	}

}
