package tools.vitruv.framework.vsum

import java.nio.file.Path
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import pcm_mockup.PInterface
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.change.correspondence.Correspondence
import tools.vitruv.change.correspondence.CorrespondenceModel
import tools.vitruv.change.interaction.UserInteractionFactory
import tools.vitruv.framework.views.View
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.TestProject
import tools.vitruv.testutils.TestProjectManager
import tools.vitruv.testutils.TestViewFactory
import uml_mockup.UInterface
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static extension tools.vitruv.change.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
class CorrespondenceTest {
	static final Logger LOGGER = Logger.getLogger(CorrespondenceTest)
	static final String VSUM_NAME = "VsumProject"
	
	Path testProjectFolder
	
	@BeforeEach
	def void acquireTestProjectFolder(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder
	}

	private def Path getCurrentProjectModelFolder() {
		return this.testProjectFolder.resolve("model")
	}

	private def URI getDefaultPcmInstanceURI() {
		return currentProjectModelFolder.resolve("My.pcm_mockup").toFile().createFileURI()
	}

	private def URI getDefaultUMLInstanceURI() {
		return currentProjectModelFolder.resolve("My.uml_mockup").toFile().createFileURI()
	}

	private def URI getAlternativePcmInstanceURI() {
		return currentProjectModelFolder.resolve("NewPCMInstance.pcm_mockup").toFile().createFileURI()
	}

	private def URI getAlternativeUMLInstanceURI() {
		return currentProjectModelFolder.resolve("NewUMLInstance.uml_mockup").toFile().createFileURI()
	}

	private def InternalVirtualModel createAlternativeVirtualModelAndModelInstances(URI pcmModelUri, URI umlModelUri) {
		val vsum = createVirtualModel(VSUM_NAME + "2")
		createMockupModels(pcmModelUri, umlModelUri, vsum)
		return vsum
	}

	private def InternalVirtualModel createVirtualModelAndModelInstances() {
		val vsum = createVirtualModel(VSUM_NAME)
		createMockupModels(getDefaultPcmInstanceURI(), getDefaultUMLInstanceURI(), vsum)
		return vsum
	}

	private def InternalVirtualModel createVirtualModel(String vsumName) {
		return new VirtualModelBuilder()
			.withStorageFolder(testProjectFolder.resolve(vsumName))
			.withUserInteractorForResultProvider(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null))
			.buildAndInitialize()
	}

	private def void createMockupModels(URI pcmModelUri, URI umlModelUri, InternalVirtualModel vsum) {
	    changePcmView(vsum) [
	        val repo = Pcm_mockupFactory.eINSTANCE.createRepository()
	        registerRoot(repo, pcmModelUri)
	        repo.interfaces += Pcm_mockupFactory.eINSTANCE.createPInterface()
            repo.components += Pcm_mockupFactory.eINSTANCE.createComponent()
	    ]

	    changeUmlView(vsum) [
	        val pckg = Uml_mockupFactory.eINSTANCE.createUPackage()
	        registerRoot(pckg, umlModelUri)
	        pckg.interfaces += Uml_mockupFactory.eINSTANCE.createUInterface()
            pckg.classes += Uml_mockupFactory.eINSTANCE.createUClass()
	    ]
	}


	@Test
	def void testAllInCommand() {
		val InternalVirtualModel vsum = createVirtualModelAndModelInstances()
		val Repository repo = testLoadObject(vsum, getDefaultPcmInstanceURI(), Repository)
		val UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
		val CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
		assertFalse(correspondenceModel.hasCorrespondences())
		val Correspondence repo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceModel)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testAllClaimersAndGettersForEObjectCorrespondences(repo, pkg, correspondenceModel, repo2pkg)
		val PInterface repoInterface = testHasCorrespondences(repo, pkg, correspondenceModel)
		testSimpleRemove(pkg, correspondenceModel, repo2pkg, repoInterface) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		testRecursiveRemove(repo, pkg, correspondenceModel, repo2pkg) // now the correspondence instance should be empty
		// recreate the same correspondence as before
		val newRepo2pkg = createRepo2PkgCorrespondence(repo, pkg, correspondenceModel) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		val removedCorrespondences = correspondenceModel.removeCorrespondencesBetween(#[repo], #[pkg], null) // now the correspondence instance should be empty
		assertEquals(newRepo2pkg, removedCorrespondences.claimOne)
		testCorrespondencePersistence(vsum, repo, pkg, correspondenceModel)
	}

	@Test
	def void testCorrespondenceUpdate() {
		val InternalVirtualModel vsum = createVirtualModelAndModelInstances()
		// create vsum and Repo and UPackage
		val Repository repo = testLoadObject(vsum, getDefaultPcmInstanceURI(), Repository)
		val UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
		// create correspondence
		val CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
		correspondenceModel.createAndAddCorrespondence(List.of(repo), List.of(pkg))
		saveUPackageInNewFile(vsum)
		assertRepositoryCorrespondences(repo, correspondenceModel)
	}

	@Test
	def void testMoveRootEObjectBetweenResource() {
		val InternalVirtualModel vsum = createVirtualModelAndModelInstances()
		val Repository repo = testLoadObject(vsum, getDefaultPcmInstanceURI(), Repository)
		val UPackage pkg = testLoadObject(vsum, getDefaultUMLInstanceURI(), UPackage)
		// create correspondence
		val CorrespondenceModel correspondenceModel = testCorrespondenceModelCreation(vsum)
		correspondenceModel.createAndAddCorrespondence(List.of(repo), List.of(pkg)) // execute the test
		moveUMLPackage(vsum)
		assertRepositoryCorrespondences(repo, correspondenceModel)
	}

	def private void assertRepositoryCorrespondences(Repository repo, CorrespondenceModel correspondenceModel) {
		// get the correspondence of repo
		correspondenceModel.getCorrespondences(List.of(repo)).claimOne
		val correspondingObjects = correspondenceModel.getCorrespondingEObjects(List.of(repo)).flatten
		assertEquals(1, correspondingObjects.size(), "Only one corresponding object is expected for the repository.")
		for (correspondingObject : correspondingObjects) {
			assertNotNull(correspondingObject, "Corresponding object is null")
			val reverseCorrespondingObjects = correspondenceModel.getCorrespondingEObjects(
				List.of(correspondingObject)).flatten
			assertNotNull(reverseCorrespondingObjects.claimOne, "Reverse corresponding object is null")
			LOGGER.info('''A: «reverseCorrespondingObjects» corresponds to B: «correspondingObject»''')
		}

	}

	def private void moveUMLPackage(InternalVirtualModel vsum) {
		val newURI = getNewUMLInstanceURI()
        changeUmlView(vsum) [
            val pkg = rootObjects.filter(UPackage).head
            moveRoot(pkg, newURI)
        ]
	}

	def private void saveUPackageInNewFile(InternalVirtualModel vsum) {
		val newURI = getNewUMLInstanceURI()
		changeUmlView(vsum) [
		    val pkg = rootObjects.filter(UPackage).head
		    EcoreUtil.delete(pkg)
		    registerRoot(pkg, newURI)
		]
	}

	def private URI getNewUMLInstanceURI() {
		return URI.createFileURI('''«currentProjectModelFolder»/MyNewUML.uml_mockup''')
	}

	def private void testCorrespondencePersistence(InternalVirtualModel vsum, Repository repo, UPackage pkg,
		CorrespondenceModel corresp) {
		// recreate the same correspondence as before
		createRepo2PkgCorrespondence(repo, pkg, corresp)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		assertNotNull(corresp, "Correspondence instance is null")
		// create a new vsum from disk and load correspondence instance from disk
		val InternalVirtualModel vsum2 = createAlternativeVirtualModelAndModelInstances(alternativePcmInstanceURI,
			alternativeUMLInstanceURI)
		val Repository repo2 = testLoadObject(vsum2, alternativePcmInstanceURI, Repository)
		val UPackage pkg2 = testLoadObject(vsum2, alternativeUMLInstanceURI, UPackage)
		val CorrespondenceModel corresp2 = testCorrespondenceModelCreation(vsum2)
		corresp2.createAndAddCorrespondence(List.of(repo2), List.of(pkg2))
		assertTrue(corresp2.hasCorrespondences()) // obtain
		val Correspondence repo2pkg2 = corresp2.getCorrespondences(List.of(repo2)).claimOne
		assertEquals(Set.of(repo2, pkg2), (repo2pkg2.leftEObjects + repo2pkg2.rightEObjects).toSet)
		// test everything as if the correspondence would just have been created
		testAllClaimersAndGettersForEObjectCorrespondences(repo2, pkg2, corresp2, repo2pkg2)
	}

	def private <T extends EObject> T testLoadObject(InternalVirtualModel vsum, URI uri, Class<T> clazz) {
		val instance = vsum.getModelInstance(uri)
		assertEquals(1, instance.resource.contents.size)
		val root = instance.resource.contents.get(0)
		assertThat(root, instanceOf(clazz))
		return root as T
	}

	def private CorrespondenceModel testCorrespondenceModelCreation(InternalVirtualModel vsum) {
		val CorrespondenceModel corresp = vsum.getCorrespondenceModel()
		assertNotNull(corresp)
		return corresp
	}

	def private Correspondence createRepo2PkgCorrespondence(Repository repo, UPackage pkg,
		CorrespondenceModel corresp) {
		// until this point the correspondence instance is empty
		val Correspondence repo2pkg = corresp.createAndAddCorrespondence(List.of(repo), List.of(pkg))
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		return repo2pkg
	}

	def private void testAllClaimersAndGettersForEObjectCorrespondences(Repository repo, UPackage pkg,
		CorrespondenceModel corresp, Correspondence repo2pkg) {
		val Correspondence uniqueRepoCorrespondence = corresp.getCorrespondences(List.of(repo)).claimOne
		assertEquals(uniqueRepoCorrespondence, repo2pkg)
		val Correspondence uniquePkgCorrespondence = corresp.getCorrespondences(List.of(repo)).claimOne
		assertEquals(uniquePkgCorrespondence, repo2pkg)
		val EObject correspForRepo = corresp.getCorrespondingEObjects(repo).claimOne
		assertEquals(correspForRepo, pkg)
		val EObject correspForPkg = corresp.getCorrespondingEObjects(pkg).claimOne
		assertEquals(correspForPkg, repo)
		val List<PInterface> interfaces = repo.getInterfaces()
		assertEquals(interfaces.size(), 1)
		val PInterface iface = interfaces.get(0)
		assertFalse(corresp.hasCorrespondences(List.of(iface)))
		val Set<Correspondence> allRepoCorrespondences = corresp.getCorrespondences(List.of(repo))
		assertEquals(allRepoCorrespondences.size(), 1)
		assertTrue(allRepoCorrespondences.contains(repo2pkg))
		val Set<Correspondence> allPkgCorrespondences = corresp.getCorrespondences(List.of(pkg))
		assertEquals(allPkgCorrespondences.size(), 1)
		assertTrue(allPkgCorrespondences.contains(repo2pkg))
		val correspondingPkg = corresp.getCorrespondingEObjects(repo).claimOne
		assertEquals(pkg, correspondingPkg)
		val correspondingRepo = corresp.getCorrespondingEObjects(pkg).claimOne
		assertEquals(repo, correspondingRepo)
	}

	def private PInterface testHasCorrespondences(Repository repo, UPackage pkg, CorrespondenceModel corresp) {
		assertTrue(corresp.hasCorrespondences(List.of(repo)))
		assertTrue(corresp.hasCorrespondences(List.of(pkg)))
		val List<PInterface> repoInterfaces = repo.getInterfaces()
		assertEquals(repoInterfaces.size(), 1)
		val PInterface repoInterface = repoInterfaces.get(0)
		assertFalse(corresp.hasCorrespondences(List.of(repoInterface)))
		return repoInterface
	}

	def private void testSimpleRemove(UPackage pkg, CorrespondenceModel corresp, Correspondence repo2pkg,
		PInterface repoInterface) {
		val List<UInterface> pkgInterfaces = pkg.getInterfaces()
		assertEquals(pkgInterfaces.size(), 1)
		val UInterface pkgInterface = pkgInterfaces.get(0)
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// add correspondence
		corresp.createAndAddCorrespondence(List.of(repoInterface), List.of(pkgInterface)) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// 3. EOC: pcmIfac _tAgfwPxjEeOD3p0i_uuRbQ <=> umlIface _vWjxIPxjEeOD3p0i_uuRbQ
		// remove correspondence
		corresp.removeCorrespondencesFor(List.of(repoInterface), null) // 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		// check whether it is removed
		val Set<Correspondence> repoInterfaceCorresp = corresp.getCorrespondences(List.of(repoInterface))
		assertTrue(repoInterfaceCorresp.isEmpty())
		val Set<Correspondence> pkgInterfaceCorresp = corresp.getCorrespondences(List.of(pkgInterface))
		assertTrue(pkgInterfaceCorresp.isEmpty())
		val Set<EObject> correspForRepoInterface = corresp.getCorrespondingEObjects(repoInterface)
		assertTrue(correspForRepoInterface.isEmpty())
		val Set<EObject> correspForPkgInterface = corresp.getCorrespondingEObjects(pkgInterface)
		assertTrue(correspForPkgInterface.isEmpty())
	}

	def private void testRecursiveRemove(Repository repo, UPackage pkg, CorrespondenceModel corresp,
		Correspondence repo2pkg) {
		// 1. EOC: repo _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg _sJD6YPxjEeOD3p0i_uuRbQ
		// 2. CRC: repo.ifaces _r5CW0PxiEeO_U4GJ6Zitkg <=> pkg.ifaces _sJD6YPxjEeOD3p0i_uuRbQ
		val removedCorrespondences = corresp.removeCorrespondencesBetween(#[repo], #[pkg], null) // now the correspondence instance should be empty
		assertEquals(repo2pkg, removedCorrespondences.claimOne)
		val Set<Correspondence> repoCorresp = corresp.getCorrespondences(List.of(repo))
		assertTrue(repoCorresp.isEmpty())
		val Set<Correspondence> pkgCorresp = corresp.getCorrespondences(List.of(pkg))
		assertTrue(pkgCorresp.isEmpty())
		val Set<EObject> correspForRepo = corresp.getCorrespondingEObjects(repo)
		assertTrue(correspForRepo.isEmpty())
		val Set<EObject> correspForPkg = corresp.getCorrespondingEObjects(pkg)
		assertTrue(correspForPkg.isEmpty())
	}
	
	private def void changePcmView(InternalVirtualModel vsum, (View)=>void modelModification) {
	    val factory = new TestViewFactory(vsum)
        factory.changeViewRecordingChanges(factory.createViewOfElements("PCM", #{Repository}), modelModification)
    }
    
    private def void changeUmlView(InternalVirtualModel vsum, (View)=>void modelModification) {
        val factory = new TestViewFactory(vsum)
        factory.changeViewRecordingChanges(factory.createViewOfElements("UML", #{UPackage}), modelModification)
    }

}
