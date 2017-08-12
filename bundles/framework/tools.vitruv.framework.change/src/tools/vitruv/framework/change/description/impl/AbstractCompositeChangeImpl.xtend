package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange

abstract class AbstractCompositeChangeImpl<C extends VitruviusChange> implements CompositeChange<C> {
	val List<C> changes

	new() {
		// TODO PS Change back to LinkedList
		changes = new ArrayList<C>
	}

	new(List<? extends C> changes) {
		this.changes = new ArrayList<C>(changes)
	}

	override List<C> getChanges() {
		new ArrayList<C>(this.changes)
	}

	override addChange(C change) {
		if (change !== null) changes += change
	}

	override removeChange(C change) {
		if (change !== null) changes -= change
	}

	override containsConcreteChange() {
		var containsConcreteChange = false
		for (change : changes) {
			if (change instanceof CompositeChange<?>)
				containsConcreteChange = containsConcreteChange || change.containsConcreteChange
			else {
				containsConcreteChange = containsConcreteChange || true
			}
		}
		return containsConcreteChange
	}

	override getURI() {
		if (changes.empty)
			null
		else
			changes.get(0).URI
	}

	override validate() {
		if (!containsConcreteChange)
			return false

		var lastURI = changes.get(0).URI
		for (change : changes) {
			if (lastURI !== null && change.URI !== null && change.URI != lastURI) {
				return false;
			}
			lastURI = change.URI;
		}
		return true
	}

	override getEChanges() {
		changes.map[EChanges].fold(
			new ArrayList<EChange>,
			[ eChangeList, echanges |
				eChangeList += echanges
				return eChangeList
			]
		)
	}

	override applyBackward() throws IllegalStateException {
		changes.reverseView.forEach[applyBackward]
	}

	override applyForward() throws IllegalStateException {
		changes.reverseView.forEach[applyForward]
	}

	override resolveBeforeAndApplyForward(ResourceSet resourceSet) {
		changes.forEach[resolveBeforeAndApplyForward(resourceSet)]
	}

	override applyBackwardIfLegacy() {
		changes.reverseView.forEach[applyBackwardIfLegacy]
	}

	override getAffectedEObjects() {
		changes.fold(newArrayList, [list, element|list += element.affectedEObjects; return list]).filterNull.toList
	}

}
