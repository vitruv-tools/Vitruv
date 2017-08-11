package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.echange.EChange

interface ChangeCloner {
	def VitruviusChange cloneVitruviusChange(VitruviusChange vitruviusChange)

	def EChange cloneEChange(EChange change)
}
