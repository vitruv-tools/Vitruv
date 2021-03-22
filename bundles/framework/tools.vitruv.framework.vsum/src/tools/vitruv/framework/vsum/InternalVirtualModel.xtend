package tools.vitruv.framework.vsum

import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.correspondence.CorrespondenceModel

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()
	def void addChangePropagationListener(ChangePropagationListener propagationListener)
	def void removeChangePropagationListener(ChangePropagationListener propagationListener)
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}