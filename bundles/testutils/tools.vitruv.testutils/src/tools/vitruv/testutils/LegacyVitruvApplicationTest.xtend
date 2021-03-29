package tools.vitruv.testutils

import tools.vitruv.testutils.VitruvApplicationTest
import java.nio.file.Path
import edu.kit.ipd.sdq.activextendannotations.DelegateExcept
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import static com.google.common.base.Preconditions.checkArgument
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects
import org.eclipse.emf.ecore.util.EcoreUtil
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject
import static com.google.common.base.Preconditions.checkState

/** 
 * DO NOT USE THIS CLASS! Use {@link VitruvApplicationTest} instead.
 * <p>
 * This is a temporary fallback class for our existing application tests until we have adapted them to the 
 * transactional API of the {@link VitruvApplicationTest}.
 * This class serves as a temporary proxy to be removed after their adaptation and should not be used to implement any 
 * new application tests.
 */
abstract class LegacyVitruvApplicationTest extends VitruvApplicationTest implements NonTransactionalTestView, LegacyCorrespondenceRetriever {
	@DelegateExcept(TestView)
	NonTransactionalTestView testView

	override generateTestView(Path testProjectPath, TestUserInteraction userInteraction, VitruvDomainRepository targetDomains) {
		val testView = new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel, targetDomains)
		testView.renewResourceCacheAfterPropagation = false
		this.testView = testView
	}
	
	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type, String tag) {
		checkArgument(object !== null, "object must not be null")
		val resolvedObject = object.resolveInVirtualModel
		if (resolvedObject === null) {
			return emptyList
		} else {
			return correspondenceModel.getCorrespondingEObjects(resolvedObject, type, tag)
		}
	}
	
	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type) {
		getCorrespondingEObjects(object, type, null)
	}
	
	private def dispatch EObject resolveInVirtualModel(EObject object) {
		if (object.eResource !== null) {
			virtualModel.uuidResolver.getResource(object.eResource.URI).getEObject(object.resolvableUriFragment)
		}
	}
	
	private def dispatch EObject resolveInVirtualModel(EClass eClass) {
		eClass
	}
	
	def private static getResolvableUriFragment(EObject object) {
		// we cannot simply use EcoreUtil#getURI, because object’s metamodel might use XMI UUIDs. Since
		// XMI UUIDs can be different for different resource sets, we cannot use URIs with XMI UUIDs to identify objects
		// across resource sets. Hence, we force hierarchical URIs. This assumes that the resolved object’s graph
		// has the same topology in the resolving resource set. This assumption holds when we use this method.
		val resource = object.eResource
		var rootElementIndex = 0;
		val resourceRoot = if (resource.contents.size <= 1) {
				object.eResource.firstRootEObject
			} else {
				// move up containment hierarchy until some container is one of the resource's root elements
				var container = object
				while (container !== null && (rootElementIndex = resource.contents.indexOf(container)) == -1) {
					container = container.eContainer
				}
				checkState(container !== null, "some container of %s must be a root element of its resource", object)
				container
			}
		val fragmentPath = EcoreUtil.getRelativeURIFragmentPath(resourceRoot, object)
		if (fragmentPath.empty) {
			return '/' + rootElementIndex
		} else {
			return '/' + rootElementIndex + '/' + fragmentPath
		}
	}
	
}
