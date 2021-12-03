package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.vsum.views.ViewType

abstract class AbstractTreeBasedViewSelector extends BasicViewSelector implements TreeBasedViewSelector {
    val EObject root

    new(EObject root, ViewType owner) {
        super(owner)
        this.root = root
        addRecursively(root)
    }

    override getRoot() {
        return root
    }

    def private void addRecursively(EObject parent) {
        elementsSelection.put(parent, false)
        parent.children.forEach[addRecursively]
    }

}
