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
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

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
		val newChanges = new NotificationToEChangeConverter().convert(new NotificationInfo(notification));
		for (newChange : newChanges) {
			if (newChange instanceof ExplicitUnsetEAttribute) {
				if (newChange.subtractiveChanges.empty) {
					if (!(changes.last instanceof SubtractiveAttributeEChange<?, ?>)) {
						throw new IllegalStateException
					}
					val lastChange = changes.last as SubtractiveAttributeEChange<?, ?>
					var lastIndex = changes.size - 1;
					for (var i = changes.size - 2; i >= 0 && lastIndex - 1 == i; i--) {
						val currentChange = changes.get(i);
						if (currentChange instanceof SubtractiveAttributeEChange<?,?>) {
							if (currentChange.affectedEObject == lastChange.affectedEObject &&
								currentChange.affectedFeature == lastChange.affectedFeature) {
								lastIndex = i;
							}
						}
					}
					val size = changes.size;
					for (var i = lastIndex; i < size; i++) {
						val lastElement = changes.remove(lastIndex) as SubtractiveAttributeEChange<?,?>
						newChange.subtractiveChanges.add(lastElement);
					}
				}
			}
			changes += newChange;
		}
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
