package tools.vitruv.change.composite.description.impl

import java.util.List
import tools.vitruv.change.composite.description.CompositeContainerChange
import tools.vitruv.change.composite.description.VitruviusChange

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class CompositeContainerChangeImpl<Element> extends AbstractCompositeChangeImpl<Element, VitruviusChange<Element>> implements CompositeContainerChange<Element> {
	new(List<? extends VitruviusChange<Element>> changes) {
		super(changes)
	}
	
	override CompositeContainerChangeImpl<Element> copy() {
		new CompositeContainerChangeImpl(changes.mapFixed [copy()])
	}
}
