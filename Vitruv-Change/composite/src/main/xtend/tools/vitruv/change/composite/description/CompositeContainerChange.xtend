package tools.vitruv.change.composite.description

import tools.vitruv.change.composite.description.VitruviusChange

/**
 * A {@link CompositeContainerChange} defines a container for {@link VitruviusChange}s.
 * It does not have any specific semantics but containing one or more {@link VitruviusChange}s. 
 */
interface CompositeContainerChange<Element> extends CompositeChange<Element, VitruviusChange<Element>> {
	override CompositeContainerChange<Element> copy()
}