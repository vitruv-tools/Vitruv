package tools.vitruv.framework.tests

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.vsum.VirtualModel

/**
 * Interface to listen for changes on {@link ChangeObservable}s.
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
interface ChangeObserver {
	def void update(VURI vuri, PropagatedChange change, VirtualModel vm)
}
