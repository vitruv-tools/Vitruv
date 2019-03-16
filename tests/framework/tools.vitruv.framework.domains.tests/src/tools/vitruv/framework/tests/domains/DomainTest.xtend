package tools.vitruv.framework.tests.domains

import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.URI
import pcm_mockup.Pcm_mockupPackage
import tools.vitruv.framework.domains.AbstractTuidAwareVitruvDomain
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.VitruviusProjectBuilderApplicator
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.util.bridges.EMFBridge
import tools.vitruv.framework.vsum.InternalVirtualModel
import tools.vitruv.testutils.VitruviusApplicationTest
import tools.vitruv.testutils.util.TestUtil
import uml_mockup.Uml_mockupPackage

// TODO TS (MEDIUM) This class was orignally a copy of VSUMTest! Either merge with subclass or reuse original.
abstract class DomainTest extends VitruviusApplicationTest {
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

	def protected InternalVirtualModel createVirtualModel(String vsumName) {
		var List<VitruvDomain> vitruvDomains = new ArrayList<VitruvDomain>()
		vitruvDomains.add(UmlDomain)
		vitruvDomains.add(PcmDomain)
		return TestUtil.createVirtualModel(vsumName, true, vitruvDomains, Collections.emptyList(),
			UserInteractionFactory.instance.createDummyUserInteractor())
	}

	def private File getCurrentProjectModelFolder() {
		return new File(getCurrentTestProjectFolder(), "model")
	}
}