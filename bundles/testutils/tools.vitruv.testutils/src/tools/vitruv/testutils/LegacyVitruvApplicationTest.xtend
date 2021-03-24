package tools.vitruv.testutils

import tools.vitruv.testutils.VitruvApplicationTest
import java.nio.file.Path
import edu.kit.ipd.sdq.activextendannotations.DelegateExcept
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import org.eclipse.emf.ecore.EObject
import java.util.List

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
	
	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type) {
		// TODO Catching exceptions is only necessary because element UUIDs may not be resolvable by the correspondence model
		// which becomes obsolete as soon as correspondences do not use UUIDs anymore
		try {
			return correspondenceModel.getCorrespondingEObjects(List.of(eObject)).flatten.filter(type)	
		} catch (IllegalStateException e) {
			return emptyList
		}
	}
	
	override <T extends EObject> Iterable<T> getCorrespondingEObjects(EObject eObject, Class<T> type, String tag) {
		// TODO Catching exceptions is only necessary because element UUIDs may not be resolvable by the correspondence model
		// which becomes obsolete as soon as correspondences do not use UUIDs anymore
		try {
			return correspondenceModel.getCorrespondingEObjects(List.of(eObject), tag).flatten.filter(type)	
		} catch (IllegalStateException e) {
			return emptyList
		}
	}
	
}
