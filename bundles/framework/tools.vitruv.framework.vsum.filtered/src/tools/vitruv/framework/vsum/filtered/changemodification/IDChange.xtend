package tools.vitruv.framework.vsum.filtered.changemodification

import tools.vitruv.framework.change.echange.EChange

interface IDChange {
	def void changeID(EChange change, String newValue)
}