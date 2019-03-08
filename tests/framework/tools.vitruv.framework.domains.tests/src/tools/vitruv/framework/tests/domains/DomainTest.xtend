package tools.vitruv.framework.tests.domains

import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
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
import uml_mockup.UPackage
import uml_mockup.Uml_mockupFactory
import uml_mockup.Uml_mockupPackage

// TODO TS This class was orignally a copy of VSUMTest!
abstract class DomainTest extends VitruviusTest {
	protected static final String VSUM_NAME = "DomainProject"
	protected static final String PCM_FILE_EXT = "pcm_mockup"
	protected static final String UML_FILE_EXT = "uml_mockup"

	static final VitruvDomain UmlDomain = new AbstractTuidAwareVitruvDomain("UML", Uml_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Uml_mockupPackage.eINSTANCE.nsURI, "id"), UML_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}

	static final VitruvDomain PcmDomain = new AbstractTuidAwareVitruvDomain("PCM", Pcm_mockupPackage.eINSTANCE,
		new AttributeTuidCalculatorAndResolver(Pcm_mockupPackage.eINSTANCE.nsURI, "id"), PCM_FILE_EXT) {
		override VitruviusProjectBuilderApplicator getBuilderApplicator() {
			return null
		}

		override boolean supportsUuids() {
			return false
		}
	}

	def protected URI getDefaultPcmInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.pcm_mockup"))
	}

	def protected URI getDefaultUMLInstanceURI() {
		return EMFBridge.getEmfFileUriForFile(new File(getCurrentProjectModelFolder(), "My.uml_mockup"))
	}

	def protected InternalVirtualModel createVirtualModelAndModelInstances() {
		var InternalVirtualModel vsum = createVirtualModel(VSUM_NAME)
		createPcmMockupModel(VURI.getInstance(defaultPcmInstanceURI), vsum)
		createUmlMockupModel(VURI.getInstance(defaultUMLInstanceURI), vsum)
		return vsum
	}

	def private File getCurrentProjectModelFolder() {
		return new File(getCurrentTestProjectFolder(), "model")
	}

	def private InternalVirtualModel createVirtualModel(String vsumName) {
		var List<VitruvDomain> vitruvDomains = new ArrayList<VitruvDomain>()
		vitruvDomains.add(UmlDomain)
		vitruvDomains.add(PcmDomain)
		return TestUtil.createVirtualModel(vsumName, true, vitruvDomains, Collections.emptyList(),
			UserInteractionFactory.instance.createDummyUserInteractor())
	}

	def private void createPcmMockupModel(VURI modelURI, InternalVirtualModel vsum) {
		var ModelInstance model = vsum.getModelInstance(modelURI)
		val EList<EObject> contents = model.resource.contents
		vsum.executeCommand([
			var Repository repo = Pcm_mockupFactory.eINSTANCE.createRepository()
			repo.interfaces.add(Pcm_mockupFactory.eINSTANCE.createPInterface())
			repo.components.add(Pcm_mockupFactory.eINSTANCE.createComponent())
			contents.add(repo)
			return null
		])
		vsum.save()
	}

	def private void createUmlMockupModel(VURI modelURI, InternalVirtualModel vsum) {
		var ModelInstance model = vsum.getModelInstance(modelURI)
		val EList<EObject> contents = model.resource.contents
		vsum.executeCommand([
			var UPackage pckg = Uml_mockupFactory.eINSTANCE.createUPackage()
			pckg.interfaces.add(Uml_mockupFactory.eINSTANCE.createUInterface())
			pckg.classes.add(Uml_mockupFactory.eINSTANCE.createUClass())
			contents.add(pckg)
			return null
		])
		vsum.save()
	}
}
