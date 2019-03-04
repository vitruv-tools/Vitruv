package tools.vitruv.framework.tests.vsum

import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import pcm_mockup.Component
import pcm_mockup.PInterface
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Pcm_mockupPackage
import pcm_mockup.Repository
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.testutils.VitruviusTest
import tools.vitruv.testutils.util.TestUtil
import uml_mockup.UClass
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory
import uml_mockup.Uml_mockupPackage

abstract class DomainTest extends VitruviusTest {
	protected static final String PROJECT_FOLDER_NAME = "MockupProject"
	protected static final String VSUM_NAME = "DomainProject"
	protected static final String PCM_MM_URI = Pcm_mockupPackage.eNS_URI
	protected static final String UML_MM_URI = Uml_mockupPackage.eNS_URI
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"

	def String getCurrentProjectFolderName() {
		return getCurrentTestProjectFolder().getName()
	}

	static final VitruvDomain UmlDomain = new AbstractTuidAwareVitruvDomain("UML", Uml_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Uml_mockupPackage.eINSTANCE.getNsURI(), "id"), UML_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}
	static final VitruvDomain PcmDomain = new AbstractTuidAwareVitruvDomain("PCM", Pcm_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eINSTANCE.getNsURI(), "id"), PCM_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}

	def protected File getCurrentProjectModelFolder() {
		return new File(getCurrentTestProjectFolder(), "model")
	}

	def protected URI getDefaultPcmInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.pcm_mockup"))
	}

	def protected URI getDefaultUMLInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.uml_mockup"))
	}

	def protected URI getAlternativePcmInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "NewPCMInstance.pcm_mockup"))
	}

	def protected URI getAlterantiveUMLInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "NewUMLInstance.uml_mockup"))
	}

	def protected ModelInstance fillVsum(InternalVirtualModel vsum) {
		// create PCM
		var VURI vuri = VURI.getInstance(getAlternativePcmInstanceURI())
		var ModelInstance mi = vsum.getModelInstance(vuri)
		val Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository()
		vsum.persistRootElement(vuri, repo)
		vsum.executeCommand([var Component component = Pcm_mockupFactory.eINSTANCE.createComponent() repo.getComponents().add(component) return null])
		vsum.save()
		// (vuri);
		vsum.executeCommand([var PInterface mockIf = Pcm_mockupFactory.eINSTANCE.createPInterface() repo.getInterfaces().add(mockIf) return null])
		vsum.save()
		// (vuri);
		// create UML
		var VURI vuriUML = VURI.getInstance(getAlterantiveUMLInstanceURI())
		val UPackage uPackage = Uml_mockupFactory.eINSTANCE.createUPackage()
		vsum.persistRootElement(vuriUML, uPackage)
		vsum.executeCommand([
			var UClass uClass = Uml_mockupFactory.eINSTANCE.createUClass()
			uPackage.getClasses().add(uClass)
			var uml_mockup.UInterface uInterface = Uml_mockupFactory.eINSTANCE.createUInterface()
			uPackage.getInterfaces().add(uInterface)
			return null
		])
		vsum.save()
		// (vuriUML);
		return mi
	}

	def protected InternalVirtualModel createAlternativeVirtualModelAndModelInstances(URI pcmModelUri, URI umlModelUri) {
		var InternalVirtualModel vsum = createVirtualModel('''«VSUM_NAME»2''')
		createMockupModels(pcmModelUri, umlModelUri, vsum)
		return vsum
	}

	def protected InternalVirtualModel createVirtualModelAndModelInstances() {
		var InternalVirtualModel vsum = createDefaultVirtualModel()
		createMockupModelsWithDefaultUris(vsum)
		return vsum
	}

	def protected InternalVirtualModel createDefaultVirtualModel() {
		return createVirtualModel(VSUM_NAME)
	}

	def protected InternalVirtualModel createVirtualModel(String vsumName) {
		var List<VitruvDomain> vitruvDomains = new ArrayList<VitruvDomain>()
		vitruvDomains.add(UmlDomain)
		vitruvDomains.add(PcmDomain)
		return TestUtil.createVirtualModel(vsumName, true, vitruvDomains, Collections.emptyList(),
			UserInteractionFactory.instance.createDummyUserInteractor())
	}

	def private void createMockupModelsWithDefaultUris(InternalVirtualModel vsum) {
		createMockupModels(getDefaultPcmInstanceURI(), getDefaultUMLInstanceURI(), vsum)
	}

	def protected void createMockupModels(URI pcmModelUri, URI umlModelUri, InternalVirtualModel vsum) {
		createPcmMockupModel(VURI.getInstance(pcmModelUri), vsum)
		createUmlMockupModel(VURI.getInstance(umlModelUri), vsum)
	}

	def private void createPcmMockupModel(VURI modelURI, InternalVirtualModel vsum) {
		var ModelInstance model = vsum.getModelInstance(modelURI)
		val EList<EObject> contents = model.getResource().getContents()
		vsum.executeCommand([
			var Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository()
			repo.getInterfaces().add(Pcm_mockupFactory.eINSTANCE.createPInterface())
			repo.getComponents().add(Pcm_mockupFactory.eINSTANCE.createComponent())
			contents.add(repo)
			return null
		])
		vsum.save() // (modelURI);
	}

	def private void createUmlMockupModel(VURI modelURI, InternalVirtualModel vsum) {
		var ModelInstance model = vsum.getModelInstance(modelURI)
		val EList<EObject> contents = model.getResource().getContents()
		vsum.executeCommand([
			var UPackage pckg = Uml_mockupFactory.eINSTANCE.createUPackage()
			pckg.getInterfaces().add(Uml_mockupFactory.eINSTANCE.createUInterface())
			pckg.getClasses().add(Uml_mockupFactory.eINSTANCE.createUClass())
			contents.add(pckg)
			return null
		])
		vsum.save() // (modelURI);
	}
}
