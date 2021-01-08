package tools.vitruv.testutils

import java.nio.file.Path
import tools.vitruv.framework.userinteraction.PredefinedInteractionResultProvider
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.vsum.VirtualModel

class NonTransactionalChangePublishingTestView extends ChangePublishingTestView implements NonTransactionalTestView {

	new(Path persistenceDirectory, PredefinedInteractionResultProvider interactionProvider, UriMode uriMode,
		VirtualModel virtualModel) {
		super(persistenceDirectory, interactionProvider, uriMode, virtualModel)
	}

	protected override isRenewResourceCacheAfterPropagation() {
		return false
	}

	override startRecordingChanges(Notifier notifier) {
		super.startRecordingChanges(notifier)
	}

	override stopRecordingChanges(Notifier notifier) {
		super.stopRecordingChanges(notifier)
	}

	override propagate() {
		super.propagate
	}

	override renewResourceCache() {
		super.renewResourceCache()
	}

}
