package tools.vitruv.framework.change.recording

import org.eclipse.emf.common.notify.Adapter
import java.util.Collection
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource

class NotificationRecorder implements Adapter {
	private Collection<? extends Notifier> rootObjects;
	private boolean isRecording = false
	List<EChange> changes;

	override getTarget() {
		return null;
	}

	override isAdapterForType(Object type) {
		return false;
	}

	override notifyChanged(Notification notification) {
		changes += new NotificationToEChangeConverter().convert(new NotificationInfo(notification));
	}

	override setTarget(Notifier newTarget) {
		if (newTarget instanceof EObject) {
			for (EObject eObject : newTarget.eContents()) {
				addAdapter(eObject);
			}
		} else {
			val contents = if (newTarget instanceof ResourceSet)
					(newTarget as ResourceSet).getResources().iterator()
				else if (newTarget instanceof Resource)
					(newTarget as Resource).getContents().iterator()
				else
					null;

			if (contents !== null) {
				while (contents.hasNext()) {
					val notifier = contents.next() as Notifier;
					addAdapter(notifier);
				}
			}
		}
	}

	protected def void addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters();
		if (!eAdapters.contains(this)) {
			eAdapters.add(this);
		}
	}

	def beginRecording(Collection<? extends Notifier> rootObjects) {
		if (!isRecording) {
			changes = newArrayList();
			this.rootObjects = rootObjects;
			for (rootObject : rootObjects) {
				addAdapter(rootObject)
			}
			isRecording = true;
		} else {
			throw new IllegalStateException
		}
	}

	def endRecording() {
		isRecording = false;
	}

	def getChanges() {
		return changes;
	}

	def isRecording() {
		return isRecording;
	}

}
