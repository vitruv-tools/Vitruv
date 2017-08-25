package tools.vitruv.extensions.dslruntime.commonalities

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

import static org.eclipse.emf.common.notify.Notification.*
import static tools.vitruv.extensions.dslruntime.commonalities.CommonalitiesConstants.*

import static extension tools.vitruv.extensions.dslruntime.commonalities.IntermediateModelManagement.*

/**
 * Assigns ids to intermediate model non root object added to a root object. 
 */
package class IntermediateModelNonRootIdAssigner extends AdapterImpl {

	private new() {
	}

	package static val INSTANCE = new IntermediateModelNonRootIdAssigner()

	override notifyChanged(extension Notification msg) {
		val root = notifier as EObject
		val changedFeature = feature as EStructuralFeature
		if (changedFeature?.name == INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME) {
			switch eventType {
				case ADD: assignId(root, newValue as EObject)
				case ADD_MANY: (newValue as Iterable<? extends EObject>).forEach[assignId(root, it)]
			}
		}
	}

	def private assignId(EObject root, EObject nonRoot) {
		nonRoot.assignId(root.nextId)
	}
	
	def private getNextId(EObject root) {
		val idCounterFeature = root.eClass.EAllStructuralFeatures.findFirst [
			name == INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER
		]
		if (idCounterFeature === null) {
			throw new IllegalStateException('''The intermediate model root “«root»” does not have the id counter attribute called “«INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER»!”''')
		}
		var int oldCounter
		synchronized (root) {
			oldCounter = root.eGet(idCounterFeature) as Integer
			root.eSet(idCounterFeature, oldCounter + 1)
		}
		return oldCounter
	}

}
