package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.ENamedElement

abstract class AbstractTreeBasedViewSelector extends BasicViewSelector implements TreeBasedViewSelector {
    val ENamedElement root

    new(ENamedElement root, ViewType owner) {
        super(owner)
        this.root = root
        addRecursively(root)
    }

    override getRoot() {
        return root
    }

    def private void addRecursively(ENamedElement parent) {
        selectableElements.add(new SelectableElement(parent))
        parent.children.forEach[addRecursively]
    }

}
