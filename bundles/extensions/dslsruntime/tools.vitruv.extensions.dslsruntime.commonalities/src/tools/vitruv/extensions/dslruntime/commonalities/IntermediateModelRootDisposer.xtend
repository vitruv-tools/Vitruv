package tools.vitruv.extensions.dslruntime.commonalities

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

import static org.eclipse.emf.common.notify.Notification.*

import static extension tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement.*

/**
 * Deletes intermediate model root objects 
 */
package class IntermediateModelRootDisposer extends AdapterImpl {
	
	package static val INSTANCE = new IntermediateModelRootDisposer()
	
	private new(){}

	override notifyChanged(extension Notification msg) {
		val root = notifier as EObject
		if (eventType == REMOVE || eventType == REMOVE_MANY) {
			if (root.childrenList.isEmpty) {
				EcoreUtil.remove(root)
			}
		}
	}
}
