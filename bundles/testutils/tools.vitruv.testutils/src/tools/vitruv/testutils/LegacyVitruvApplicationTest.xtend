package tools.vitruv.testutils

import tools.vitruv.testutils.VitruvApplicationTest
import java.nio.file.Path
import edu.kit.ipd.sdq.activextendannotations.DelegateExcept

/** 
 * DO NOT USE THIS CLASS! Use {@link VitruvApplicationTest} instead.
 * <p>
 * This is a temporary fallback class for our existing application tests until we have adapted them to the 
 * transactional API of the {@link VitruvApplicationTest}.
 * This class serves as a temporary proxy to be removed after their adaptation and should not be used to implement any 
 * new application tests.
 */
abstract class LegacyVitruvApplicationTest extends VitruvApplicationTest implements NonTransactionalTestView {
	@DelegateExcept(TestView)
	NonTransactionalTestView testView

	override generateTestView(Path testProjectPath, TestUserInteraction userInteraction) {
		val testView = new ChangePublishingTestView(testProjectPath, userInteraction, this.uriMode, virtualModel)
		testView.renewResourceCacheAfterPropagation = false
		this.testView = testView
	}
}
