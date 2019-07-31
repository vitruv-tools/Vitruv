package tools.vitruv.dsls.mappings.tests.pcmuml

import mir.reactions.umlXpcmTest_L2R.UmlXpcmTest_L2RChangePropagationSpecification
import mir.reactions.umlXpcmTest_R2L.UmlXpcmTest_R2LChangePropagationSpecification
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.domains.pcm.PcmDomainProvider
import tools.vitruv.domains.uml.UmlDomainProvider
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.testutils.VitruviusApplicationTest

abstract class PcmUmlClassTest extends VitruviusApplicationTest {

	override protected createChangePropagationSpecifications() {
		return #[
			new UmlXpcmTest_L2RChangePropagationSpecification,
			new UmlXpcmTest_R2LChangePropagationSpecification
		];
	}

	protected var ResourceSet testResourceSet;
	protected var PcmUmlClassHelper helper

	protected def getTestResource(URI uri) {
		return testResourceSet.getResource(uri, true)
	}

	override protected getVitruvDomains() {
		return #[new PcmDomainProvider().domain, new UmlDomainProvider().domain];
	}

	override protected cleanup() {
		testResourceSet = null
		helper = null
	}

	override protected setup() {
		MappingConstants.init
		testResourceSet = new ResourceSetImpl();
		helper = new PcmUmlClassHelper(correspondenceModel, [uri|uri.getModelElement], [uri|uri.modelResource])
	}

	protected def EObject reloadResourceAndReturnRoot(EObject modelElement) {
		stopRecordingChanges(modelElement)
		val resourceURI = modelElement.eResource.URI
		modelElement.eResource.unload
		val rootElement = getModelResource(resourceURI).contents.head
		if (rootElement !== null) {
			startRecordingChanges(rootElement)
		}
		return rootElement
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b) {
		return cm.getCorrespondingEObjects(#[a]).exists(corrSet|EcoreUtil.equals(corrSet.head, b))
	}

	def protected static corresponds(CorrespondenceModel cm, EObject a, EObject b, String tag) {
		return EcoreUtil.equals(b,
			ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(cm, a, tag, b.class).head)
	}

}
