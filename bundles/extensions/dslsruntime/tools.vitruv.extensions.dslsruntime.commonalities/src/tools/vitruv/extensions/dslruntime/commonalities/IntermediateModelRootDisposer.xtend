package tools.vitruv.extensions.dslruntime.commonalities

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.Root

import static org.eclipse.emf.common.notify.Notification.*

/**
 * Deletes intermediate model root objects 
 */
package class IntermediateModelRootDisposer extends AdapterImpl {
	
	package static val INSTANCE = new IntermediateModelRootDisposer()
	
	private new(){}

	override notifyChanged(extension Notification msg) {
		val root = notifier as Root
		if (eventType == REMOVE || eventType == REMOVE_MANY) {
			if (root.intermediates.isEmpty) {
				// TODO this breaks change reverse application
				//EcoreUtil.remove(root)
			}
		}
	}
}
