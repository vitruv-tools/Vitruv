package tools.vitruv.framework.change.recording

import org.eclipse.emf.common.notify.Adapter
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class NotificationRecorder implements Adapter {
	private List<Notifier> rootObjects;
	private boolean isRecording = false
	List<EChange> changes;
	List<TransactionalChange> resultChanges;
	private val EChangeIdManager idManager
	
	new (EChangeIdManager idManager) {
		this.idManager = idManager;	
		rootObjects = newArrayList();
	}
	
	override getTarget() {
		return null;
	}

	override isAdapterForType(Object type) {
		return false;
	}

	override notifyChanged(Notification notification) {
		if (!isRecording) {
			return;
		}
		if (notification.newValue instanceof Notifier) {
			addToRecording(notification.newValue as Notifier);
		}
		val newChanges = new NotificationToEChangeConverter(idManager).convert(new NotificationInfo(notification), changes);
		for (newChange : newChanges) {
			changes += newChange;
		}
		if (!newChanges.empty) {
			val transactionalChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange
			newChanges.forEach[transactionalChange.addChange(VitruviusChangeFactory.instance.createConcreteApplicableChange(it))];
			resultChanges += transactionalChange;
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
		addAdapter(newTarget);
	}

	private def void addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters();
		if (!eAdapters.contains(this)) {
			eAdapters.add(this);
		}
	}
	
	def void addToRecording(Notifier notifier) {
		rootObjects += notifier;
		setTarget(notifier);
	}

	def beginRecording() {
		if (!isRecording) {
			changes = newArrayList();
			resultChanges = newArrayList();
			isRecording = true;
		} else {
			throw new IllegalStateException
		}
	}

	def endRecording() {
		isRecording = false;
	}

	def getChanges() {
		return resultChanges;
	}

	def isRecording() {
		return isRecording;
	}

}
