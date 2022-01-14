package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.vsum.views.impl.ViewCreatingViewType
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import java.util.Collection

abstract class AbstractTreeBasedViewSelector extends BasicViewElementSelector implements TreeBasedViewSelector {
	val EObject root

	new(EObject root, ViewCreatingViewType<BasicViewElementSelector> viewType, ChangeableViewSource viewSource) {
		super(viewType, viewSource, root.collectRecursively(newArrayList))
		this.root = root
	}

	override getRoot() {
		return root
	}

	def static private Collection<EObject> collectRecursively(EObject parent, Collection<EObject> objects) {
		objects.add(parent)
		parent.calculateChildren.forEach[collectRecursively(objects)]
		return objects
	}

	override getChildren(EObject element) {
		element.calculateChildren
	}

	def static Iterable<EObject> calculateChildren(EObject element) {
		// Needs to be implemented
		#[]
	}

}
