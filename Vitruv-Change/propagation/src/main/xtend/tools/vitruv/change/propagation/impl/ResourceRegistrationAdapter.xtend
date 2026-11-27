package tools.vitruv.change.propagation.impl

import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.ecore.resource.ResourceSet
import java.util.function.Consumer
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier

/**
 * An adapter that reacts to additions of resources in a {@link ResourceSet} to call the
 * callback function for that resource given to the constructor. 
 */
class ResourceRegistrationAdapter implements Adapter {
	val Consumer<Resource> resourceRegistrationFunction

	new(Consumer<Resource> resourceRegistrationFunction) {
		this.resourceRegistrationFunction = resourceRegistrationFunction
	}

	override getTarget() {
		return null
	}

	override isAdapterForType(Object type) {
		return false
	}

	override notifyChanged(Notification notification) {
		if (notification.eventType == Notification.ADD) {
			if (notification.notifier instanceof ResourceSet) {
				if (notification.newValue instanceof Resource) {
					val resource = notification.newValue as Resource
					resourceRegistrationFunction.accept(resource)
				}
			}
		}
	}

	override setTarget(Notifier newTarget) {
		// Do nothing
	}

}
