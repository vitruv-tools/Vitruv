package tools.vitruv.framework.tests.vsum

import tools.vitruv.testutils.RegisterMetamodelsInStandalone
import tools.vitruv.testutils.TestLogging
import tools.vitruv.testutils.TestProjectManager
import org.junit.jupiter.api.^extension.ExtendWith
import org.apache.log4j.Logger
import java.nio.file.Path
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.TestProject
import org.eclipse.emf.common.util.URI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.createFileURI
import static org.junit.jupiter.api.Assertions.*
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import tools.vitruv.framework.vsum.VirtualModelBuilder
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import uml_mockup.Uml_mockupFactory
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.testutils.domains.UmlMockupDomainProvider
import tools.vitruv.testutils.domains.PcmMockupDomainProvider
import org.junit.jupiter.api.Test
import tools.vitruv.framework.vsum.variability.InternalVaveModel

@ExtendWith(TestProjectManager, TestLogging, RegisterMetamodelsInStandalone)
class VaveModelTest {
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

	private def URI getAlterantiveUMLInstanceURI() {
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
			.withDomains(new UmlMockupDomainProvider().getDomain(), new PcmMockupDomainProvider().getDomain())
			.buildAndInitialize()
	}

	private def void createMockupModels(URI pcmModelUri, URI umlModelUri, InternalVirtualModel vsum) {
		val pcmResource = new ResourceSetImpl().createResource(pcmModelUri)
		val repo = Pcm_mockupFactory.eINSTANCE.createRepository()
		repo.interfaces += Pcm_mockupFactory.eINSTANCE.createPInterface()
		repo.components += Pcm_mockupFactory.eINSTANCE.createComponent()
		pcmResource.contents += repo
		vsum.propagateChangedState(pcmResource)
		
		val umlResource = new ResourceSetImpl().createResource(umlModelUri)
		val pckg = Uml_mockupFactory.eINSTANCE.createUPackage()
		pckg.interfaces += Uml_mockupFactory.eINSTANCE.createUInterface()
		pckg.classes += Uml_mockupFactory.eINSTANCE.createUClass()
		umlResource.contents += pckg
		vsum.propagateChangedState(umlResource)
	}
	
//	@Test
//	def void testVaveModelCreation() {
//		val InternalVirtualModel vsum = createVirtualModelAndModelInstances()
//		val InternalVaveModel vavemodel = vsum.getVaveModel()
//		assertNotNull(vavemodel, "Vave Model is null")
//	}
}