package tools.vitruv.extensions.dslruntime.commonalities.resources

import java.util.List
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

// TODO remove once resources are handled by domains
class ResourceRemover extends AdapterImpl {
	public static val INSTANCE = new ResourceRemover()

	private new() {
	}

	override notifyChanged(extension Notification msg) {
		if (eventType == Notification.REMOVE || eventType == Notification.REMOVE_MANY) {
			if ((feature as EStructuralFeature) == IntermediateModelBasePackage.eINSTANCE.root_ResourceBridges) {
				switch eventType {
					case Notification.REMOVE:
						(oldValue as IntermediateResourceBridge).remove()
					case Notification.REMOVE_MANY:
						(oldValue as List<IntermediateResourceBridge>).forEach [remove]
				}
			}
		}
	}

}
