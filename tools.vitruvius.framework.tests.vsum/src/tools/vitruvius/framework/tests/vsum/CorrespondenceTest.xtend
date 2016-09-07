package tools.vitruvius.framework.tests.vsum

import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard
import tools.vitruvius.framework.metamodel.ModelInstance
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.correspondence.Correspondence
import tools.vitruvius.framework.tuid.TUID
import tools.vitruvius.framework.vsum.VSUMImpl
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

import static extension tools.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import static extension tools.vitruvius.framework.util.bridges.CollectionBridge.*
import tools.vitruvius.extensions.dslsruntime.mapping.MappedCorrespondenceModel
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.MappingRealization
import tools.vitruvius.extensions.dslsruntime.mapping.MappingExecutionState
import pcm_mockup.PInterface
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel

class CorrespondenceTest extends VSUMTest {
	static final Logger LOGGER = Logger.getLogger(CorrespondenceTest.getSimpleName())

	@Test def void testAllInCommand() {
		val VSUMImpl vsum = createMetaRepositoryVSUMAndModelInstances()
		vsum.createRecordingCommandAndExecuteCommandOnTransactionalDomain([testAll(vsum) return null]);
	}

	def private void testAll(VSUMImpl vsum) {
		var Repository repo = testLoadObject(vsum, getDefaultPCMInstanceURI(), Repository)
		var UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
		var CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
		assertFalse(correspondenceModel.hasCorrespondences())
		var Correspondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceModel)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, correspondenceModel, repo2pkg)
		var PInterface repoInterface = testHasCorrespondences(repo, pkg, correspondenceModel)
		testSimpleRemove(pkg, correspondenceModel, repo2pkg, repoInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testRecursiveRemove(repo, pkg, correspondenceModel, repo2pkg) // now the correspondence instance should be empty
		// recreate the same correspondence as before
		repo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceModel) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testCreateRepo2PkgCorrespondenceAndUpdateTUID(repo, pkg, correspondenceModel, repo2pkg)
		correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(pkg.toSet) // now the correspondence instance should be empty
		testCorrespondencePersistence(vsum, repo, pkg, correspondenceModel)
	}

	@Test def void testCorrespondenceUpdate() {
		val VSUMImpl vsum = createMetaRepositoryVSUMAndModelInstances()
		vsum.createRecordingCommandAndExecuteCommandOnTransactionalDomain([ // create vsum and Repo and UPackage
			var Repository repo = testLoadObject(vsum, getDefaultPCMInstanceURI(), Repository)
			var UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
			// create correspondence
			var CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
			correspondenceModel.createAndAddCorrespondence(repo, pkg)
			LOGGER.
				trace('''Before we remove the pkg from the resource it has the tuid '«»«correspondenceModel.calculateTUIDFromEObject(pkg)»'.''')
			removePkgFromFileAndUpdateCorrespondence(pkg, correspondenceModel)
			LOGGER.
				trace('''After removing the pkg from the resource it has the tuid '«»«correspondenceModel.calculateTUIDFromEObject(pkg)»'.''')
			saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceModel)
			LOGGER.
				trace('''After adding the pkg to the new resource it has the tuid '«»«correspondenceModel.calculateTUIDFromEObject(pkg)»'.''')
			assertRepositoryCorrespondences(repo, correspondenceModel)
			return null
		])
	}

	@Test def void testMoveRootEObjectBetweenResource() {
		val VSUMImpl vsum = createMetaRepositoryVSUMAndModelInstances()
		vsum.createRecordingCommandAndExecuteCommandOnTransactionalDomain([
			var Repository repo = testLoadObject(vsum, getDefaultPCMInstanceURI(), Repository)
			var UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
			// create correspondence
			var CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
			correspondenceModel.createAndAddCorrespondence(repo, pkg) // execute the test
			LOGGER.
				trace('''Before we remove the pkg from the resource it has the tuid '«»«correspondenceModel.calculateTUIDFromEObject(pkg)»'.''')
			moveUMLPackageTo(pkg, getTmpUMLInstanceURI(), vsum, correspondenceModel)
			moveUMLPackageTo(pkg, getNewUMLInstanceURI(), vsum, correspondenceModel)
			assertRepositoryCorrespondences(repo, correspondenceModel)
			return null
		])
	}

	def private void assertRepositoryCorrespondences(Repository repo,
		CorrespondenceModel correspondenceModel) {
		// get the correspondence of repo
		var Set<Correspondence> correspondences = correspondenceModel.getCorrespondences(repo.toList)
		assertEquals("Only one correspondence is expected for the repository.", 1, correspondences.size())
		for (Correspondence correspondence : correspondences) {
			assertTrue("Correspondence is not from the type EObjectCorrespondence",
				correspondence instanceof Correspondence)
			var Correspondence eoc = correspondence
			LOGGER.
				info('''EObject with TUID: «eoc.ATUIDs» corresponds to EObject with TUID: «eoc.BTUIDs»''')
			var EObject a = correspondenceModel.resolveEObjectFromTUID(eoc.ATUIDs.claimOne)
			var EObject b = correspondenceModel.resolveEObjectFromTUID(eoc.BTUIDs.claimOne)
			assertNotNull("Left Object is null", a)
			assertNotNull("Right Object is null", b)
			LOGGER.info('''A: «a» corresponds to B: «b»''')
		}

	}

	def private void moveUMLPackageTo(UPackage pkg, String string, VSUMImpl vsum,
		CorrespondenceModel correspondenceModel) {
		saveUPackageInNewFileAndUpdateCorrespondence(vsum, pkg, correspondenceModel)
	}

	def private void saveUPackageInNewFileAndUpdateCorrespondence(VSUMImpl vsum, UPackage pkg,
		CorrespondenceModel correspondenceModel) {
		var TUID oldTUID = correspondenceModel.calculateTUIDFromEObject(pkg)
		var VURI newVURI = VURI.getInstance(getNewUMLInstanceURI())
		vsum.saveModelInstanceOriginalWithEObjectAsOnlyContent(newVURI, pkg, oldTUID)
		correspondenceModel.updateTUID(oldTUID, pkg)
	}

	def private String getNewUMLInstanceURI() {
		return '''«getCurrentProjectModelFolder()»/MyNewUML.uml_mockup'''
	}

	def private String getTmpUMLInstanceURI() {
		return '''«getCurrentProjectFolderName()»/MyTmpUML.uml_mockup'''
	}

	def private void removePkgFromFileAndUpdateCorrespondence(UPackage pkg,
		CorrespondenceModel correspondenceModel) {
		var TUID oldTUID = correspondenceModel.calculateTUIDFromEObject(pkg)
		EcoreUtil.remove(pkg)
		correspondenceModel.updateTUID(oldTUID, pkg)
	}

	def private void testCorrespondencePersistence(VSUMImpl vsum, Repository repo, UPackage pkg,
		CorrespondenceModel corresp) {
		// recreate the same correspondence as before
		var Correspondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, corresp)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		assertNotNull("Correspondence instance is null", corresp)
		if (corresp instanceof MappedCorrespondenceModel) {
			var MappingRealization mapping = new MappingRealization() {
				protected static final long serialVersionUID = 1L

				override String getMappingID() {
					return null
				}
				
				override applyEChange(EChange eChange, Blackboard blackboard, MappingExecutionState state) {
					
				}
			}
			(corresp as MappedCorrespondenceModel).registerMappingForCorrespondence(repo2pkg, mapping)
		}
		// save instances in order to trigger saving for CorrespondenceModel(s)
		var VURI pcmVURI = VURI.getInstance(getDefaultPCMInstanceURI())
		vsum.saveExistingModelInstanceOriginal(pcmVURI)
		// create a new vsum from disk and load correspondence instance from disk
		var VSUMImpl vsum2 = createMetaRepositoryVSUMAndModelInstances(alternativePCMInstanceURI, alterantiveUMLInstanceURI);
		var Repository repo2 = testLoadObject(vsum2, alternativePCMInstanceURI, Repository)
		var UPackage pkg2 = testLoadObject(vsum2, alterantiveUMLInstanceURI, UPackage)
		var CorrespondenceModel corresp2 = testCorrespondenceModelCreation(vsum2)
		corresp2.createAndAddCorrespondence(repo2, pkg2);
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

	def private CorrespondenceModel testCorrespondenceModelCreation(VSUMImpl vsum) {
		var VURI pcmMMVURI = VURI.getInstance(PCM_MM_URI)
		var VURI umlMMVURI = VURI.getInstance(UML_MM_URI)
		var CorrespondenceModel corresp = vsum.getCorrespondenceModel(pcmMMVURI, umlMMVURI)
		assertNotNull(corresp)
		return corresp
	}

	def private Correspondence createRepo2PkgCorrespondence(Repository repo, UPackage pkg,
		CorrespondenceModel corresp) {
		// until this point the correspondence instance is empty
		var Correspondence repo2pkg = corresp.createAndAddCorrespondence(repo, pkg)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		return repo2pkg
	}

	def private void testAllClaimersAndGettersForEObjectCorrespondences(Repository repo, UPackage pkg,
		CorrespondenceModel corresp, Correspondence repo2pkg) {
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
		var List<PInterface> interfaces = repo.getInterfaces()
		assertEquals(interfaces.size(), 1)
		var PInterface iface = interfaces.get(0)
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

	def private PInterface testHasCorrespondences(Repository repo, UPackage pkg, CorrespondenceModel corresp) {
		assertTrue(corresp.hasCorrespondences(repo.toList))
		assertTrue(corresp.hasCorrespondences(pkg.toList))
		var List<PInterface> repoInterfaces = repo.getInterfaces()
		assertEquals(repoInterfaces.size(), 1)
		var PInterface repoInterface = repoInterfaces.get(0)
		assertTrue(!corresp.hasCorrespondences(repoInterface.toList))
		return repoInterface
	}

	def private void testSimpleRemove(UPackage pkg, CorrespondenceModel corresp, Correspondence repo2pkg,
		PInterface repoInterface) {
		var List<uml_mockup.UInterface> pkgInterfaces = pkg.getInterfaces()
		assertEquals(pkgInterfaces.size(), 1)
		var uml_mockup.UInterface pkgInterface = pkgInterfaces.get(0)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// add correspondence
		corresp.createAndAddCorrespondence(repoInterface, pkgInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// 3. EOC: pcmIfac _tAgfwPxjEeOD3p0i_uuRbQ <=> umlIface _vWjxIPxjEeOD3p0i_uuRbQ
		// remove correspondence
		corresp.removeCorrespondencesThatInvolveAtLeastAndDependend(repoInterface.toSet) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
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

	def private void testRecursiveRemove(Repository repo, UPackage pkg, CorrespondenceModel corresp,
		Correspondence repo2pkg) {
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		corresp.removeCorrespondencesAndDependendCorrespondences(repo2pkg) // now the correspondence instance should be empty
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
		CorrespondenceModel corresp, Correspondence repo2pkg) {
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
