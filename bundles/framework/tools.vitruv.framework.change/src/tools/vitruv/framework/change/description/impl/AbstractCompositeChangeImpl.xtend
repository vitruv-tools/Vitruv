package tools.vitruv.framework.change.description.impl

import java.util.ArrayList
import java.util.LinkedList
import java.util.List

import org.eclipse.emf.ecore.resource.ResourceSet

import tools.vitruv.framework.change.description.CompositeChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange

abstract class AbstractCompositeChangeImpl<C extends VitruviusChange> implements CompositeChange<C> {
	val List<C> changes

	new() {
		changes = new LinkedList<C>
	}

	new(List<? extends C> changes) {
		this.changes = new LinkedList<C>(changes)
	}

	override List<C> getChanges() {
		new LinkedList<C>(this.changes)
	}

	override addChange(C change) {
		if (change !== null) changes.add(change)
	}

	override removeChange(C change) {
		if (change !== null) changes.remove(change)
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
			if (change.URI != lastURI)
				return false
			lastURI = change.URI
		}
		return true
	}

	override getEChanges() {
		changes.fold(
			new ArrayList<EChange>,
			[ eChangeList, change |
				eChangeList.addAll(change.EChanges)
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

}
