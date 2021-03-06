package tools.vitruv.testutils

import tools.vitruv.testutils.VitruvApplicationTest
import java.nio.file.Path
import edu.kit.ipd.sdq.activextendannotations.DelegateExcept
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClass
import static com.google.common.base.Preconditions.checkArgument
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.getCorrespondingEObjects
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment

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

	override generateTestView(Path testProjectPath, TestUserInteraction userInteraction,
		VitruvDomainRepository targetDomains) {
		val testView = new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel,
			targetDomains)
		testView.renewResourceCacheAfterPropagation = false
		this.testView = testView
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type, String tag) {
		checkArgument(object !== null, "object must not be null")
		val resolvedObject = object.resolveInVirtualModel
		if (resolvedObject === null) {
			return emptyList
		} else {
			return internalVirtualModel.correspondenceModel.getCorrespondingEObjects(resolvedObject, type, tag)
		}
	}

	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject object, Class<T> type) {
		getCorrespondingEObjects(object, type, null)
	}

	private def dispatch EObject resolveInVirtualModel(EObject object) {
		if (object.eResource !== null) {
			internalVirtualModel.getModelInstance(object.eResource.URI).resource.getEObject(object.hierarchicUriFragment)
		}
	}

	private def dispatch EObject resolveInVirtualModel(EClass eClass) {
		eClass
	}

}
