package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.vsum.views.ViewType
import tools.vitruv.framework.vsum.views.ChangeableViewSource

abstract class AbstractTreeBasedViewSelector extends BasicViewSelector implements TreeBasedViewSelector {
    val EObject root

    new(EObject root, ViewType<BasicViewSelector> owner, ChangeableViewSource viewSource) {
        super(owner, viewSource)
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
