package tools.vitruv.change.correspondence

import java.nio.file.Path
import java.util.List
import java.util.Set
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
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
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.change.testutils.TestLogging
import tools.vitruv.change.testutils.TestProject
import tools.vitruv.change.testutils.TestProjectManager
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory

import static org.junit.jupiter.api.Assertions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import tools.vitruv.change.correspondence.model.CorrespondenceModelFactory
import tools.vitruv.change.correspondence.view.CorrespondenceModelViewFactory
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
class CorrespondenceTest {
	static val Logger LOGGER = LogManager.getLogger(CorrespondenceTest)
	static val CORRESPONDENCE_MODEL_NAME = "correspondence.correspondence"
	var ResourceSet testResourceSet

	Path testProjectFolder

	@BeforeEach
	def void acquireTestProjectFolder(@TestProject Path testProjectFolder) {
		this.testProjectFolder = testProjectFolder
	}

	@BeforeEach
	def void setupResourceSet() {
		this.testResourceSet = new ResourceSetImpl().withGlobalFactories
	}

	private def createCorrespondenceModel(boolean loadPersistence) {
		val correspondenceModel = CorrespondenceModelFactory.createPersistableCorrespondenceModel(
			CORRESPONDENCE_MODEL_NAME.createModelUri)
		assertNotNull(correspondenceModel)
		if (loadPersistence) {
			correspondenceModel.loadSerializedCorrespondences(testResourceSet)
		}
		return correspondenceModel
	}

	private def createCorrespondenceModelAndReturnView() {
		return createCorrespondenceModel(false).wrapCorrespondenceModelIntoView
	}

	private def wrapCorrespondenceModelIntoView(PersistableCorrespondenceModel correspondenceModel) {
		return CorrespondenceModelViewFactory.createEditableCorrespondenceModelView(correspondenceModel)
	}

	@Test
	def void testCorrespondenceAfterModelPersistence() {
		val repo = createPcmRepositoryWithInterfaceAndComponent()
		val pkg = createUmlPackageWithInterfaceAndClass()
		val correspondenceModel = createCorrespondenceModelAndReturnView()
		correspondenceModel.addCorrespondenceBetween(repo, pkg, null)
		saveUMLPackageInNewFile(pkg)
		assertRepositoryCorrespondences(correspondenceModel, repo)
	}

	@Test
	def void testCorrespondenceAfterMovingRootEObjectBetweenResources() {
		val repo = createPcmRepositoryWithInterfaceAndComponent()
		val pkg = createUmlPackageWithInterfaceAndClass()
		val correspondenceModel = createCorrespondenceModelAndReturnView()
		correspondenceModel.addCorrespondenceBetween(repo, pkg, null)
		moveUMLPackage(pkg)
		validateSingleCorrespondence(correspondenceModel, repo, pkg)
		assertRepositoryCorrespondences(correspondenceModel, repo)
	}

	@Test
	def void testReloadingCorrespondencesFromPersistence() {
		val repo = createPcmRepositoryWithInterfaceAndComponent()
		val pkg = createUmlPackageWithInterfaceAndClass()
		val firstCorrespondenceModel = createCorrespondenceModel(false)
		firstCorrespondenceModel.wrapCorrespondenceModelIntoView.addCorrespondenceBetween(repo, pkg, null)
		firstCorrespondenceModel.save()
		val repo2 = createPcmRepositoryWithInterfaceAndComponent(alternativePcmInstanceURI)
		val pkg2 = createUmlPackageWithInterfaceAndClass(alternativeUMLInstanceURI)
		val secondCorrespondenceModel = createCorrespondenceModel(true).wrapCorrespondenceModelIntoView()
		val secondCorrespondence = secondCorrespondenceModel.addCorrespondenceBetween(repo2, pkg2, null)
		secondCorrespondenceModel.getCorrespondingEObjects(repo2).claimOne
		assertEquals(Set.of(repo2, pkg2),
			(secondCorrespondence.leftEObjects + secondCorrespondence.rightEObjects).toSet)
		validateSingleCorrespondence(secondCorrespondenceModel, repo2, pkg2)
	}

	@Test
	def void testRemoveCorrespondence() {
		val repo = createPcmRepositoryWithInterfaceAndComponent()
		val pkg = createUmlPackageWithInterfaceAndClass()
		val repoInterface = repo.interfaces.claimOne
		val pkgInterface = pkg.interfaces.claimOne
		val correspondenceModel = createCorrespondenceModelAndReturnView()
		correspondenceModel.addCorrespondenceBetween(repoInterface, pkgInterface, null)
		correspondenceModel.removeCorrespondencesBetween(repoInterface, pkgInterface, null)
		val Set<EObject> correspForRepoInterface = correspondenceModel.getCorrespondingEObjects(repoInterface)
		assertTrue(correspForRepoInterface.isEmpty())
		val Set<EObject> correspForPkgInterface = correspondenceModel.getCorrespondingEObjects(pkgInterface)
		assertTrue(correspForPkgInterface.isEmpty())
	}

	def private void validateSingleCorrespondence(EditableCorrespondenceModelView<Correspondence> correspondenceModel,
		Repository repo, UPackage pkg) {
		val EObject correspForRepo = correspondenceModel.getCorrespondingEObjects(repo).claimOne
		assertEquals(correspForRepo, pkg)
		val EObject correspForPkg = correspondenceModel.getCorrespondingEObjects(pkg).claimOne
		assertEquals(correspForPkg, repo)
		val List<PInterface> interfaces = repo.getInterfaces()
		assertEquals(interfaces.size(), 1)
		val PInterface iface = interfaces.get(0)
		assertFalse(correspondenceModel.hasCorrespondences(iface))
		val correspondingPkg = correspondenceModel.getCorrespondingEObjects(repo).claimOne
		assertEquals(pkg, correspondingPkg)
		val correspondingRepo = correspondenceModel.getCorrespondingEObjects(pkg).claimOne
		assertEquals(repo, correspondingRepo)
	}

	def private void assertRepositoryCorrespondences(EditableCorrespondenceModelView<?> correspondenceModel,
		Repository repo) {
		val correspondingObjects = correspondenceModel.getCorrespondingEObjects(repo)
		assertEquals(1, correspondingObjects.size(), "Only one corresponding object is expected for the repository.")
		for (correspondingObject : correspondingObjects) {
			assertNotNull(correspondingObject, "Corresponding object is null")
			val reverseCorrespondingObjects = correspondenceModel.getCorrespondingEObjects(
				correspondingObject)
			assertNotNull(reverseCorrespondingObjects.claimOne, "Reverse corresponding object is null")
			LOGGER.info('''A: «reverseCorrespondingObjects» corresponds to B: «correspondingObject»''')
		}

	}

	private def createPcmRepositoryWithInterfaceAndComponent() {
		createPcmRepositoryWithInterfaceAndComponent(defaultPcmInstanceURI)
	}

	private def createPcmRepositoryWithInterfaceAndComponent(URI persistenceURI) {
		return Pcm_mockupFactory.eINSTANCE.createRepository() => [
			interfaces += Pcm_mockupFactory.eINSTANCE.createPInterface()
			components += Pcm_mockupFactory.eINSTANCE.createComponent()
			testResourceSet.createResource(persistenceURI).contents += it
		]
	}

	private def createUmlPackageWithInterfaceAndClass() {
		createUmlPackageWithInterfaceAndClass(defaultUMLInstanceURI)
	}

	private def createUmlPackageWithInterfaceAndClass(URI persistenceURI) {
		return Uml_mockupFactory.eINSTANCE.createUPackage() => [
			interfaces += Uml_mockupFactory.eINSTANCE.createUInterface()
			classes += Uml_mockupFactory.eINSTANCE.createUClass()
			testResourceSet.createResource(persistenceURI).contents += it
		]
	}

	private def URI getDefaultPcmInstanceURI() {
		return createModelUri("My.pcm_mockup")
	}

	private def URI getDefaultUMLInstanceURI() {
		return createModelUri("My.uml_mockup")
	}

	private def URI getAlternativePcmInstanceURI() {
		return createModelUri("NewPCMInstance.pcm_mockup")
	}

	private def URI getAlternativeUMLInstanceURI() {
		return createModelUri("NewUMLInstance.uml_mockup")
	}

	private def URI createModelUri(String fileName) {
		return this.testProjectFolder.resolve("model").resolve(fileName).toFile().createFileURI()
	}

	def private void moveUMLPackage(UPackage umlPackage) {
		umlPackage.eResource.URI = alternativeUMLInstanceURI
	}

	def private void saveUMLPackageInNewFile(UPackage umlPackage) {
		EcoreUtil.delete(umlPackage)
		new ResourceSetImpl().createResource(alternativeUMLInstanceURI).contents += umlPackage
	}

}
