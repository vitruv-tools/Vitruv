package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import java.util.List
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.id.IdResolver
import java.util.Optional

class CompositeContainerChangeImpl extends AbstractCompositeChangeImpl<VitruviusChange> implements CompositeContainerChange {
	new(List<? extends VitruviusChange> changes) {
		super(changes)
	}
	
	override CompositeContainerChangeImpl copy() {
		new CompositeContainerChangeImpl(changes.mapFixed [copy()])
	}
	
	override resolveAndApply(ResourceSet resourceSet) {
		new CompositeContainerChangeImpl(changes.mapFixed[resolveAndApply(resourceSet)])
	}
	
	override resolveAndApply(ResourceSet resourceSet, Optional<IdResolver> idResolver) {
		new CompositeContainerChangeImpl(changes.mapFixed[resolveAndApply(resourceSet, idResolver)])
	}
	
	override unresolve() {
		new CompositeContainerChangeImpl(changes.mapFixed[unresolve()])
	}
	
}