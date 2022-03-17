package tools.vitruv.framework.vsum.filtered.changemodification

import tools.vitruv.framework.change.echange.EChange
import java.util.Objects

interface EChangeModification<T extends Object> {
	def EChange getEChange()
	def T getOldValue()
	def T getNewValue()
	def EChangeModificationType getModificationType()
	def void apply()
	def void reverse()
	def String getRepresentation() {
		return String.format("Change %s from old value %s to new value %s for change %s", modificationType, oldValue, newValue, EChange);
	}
	def containsChange() {
		return !Objects.equals(oldValue, newValue)
	}
}