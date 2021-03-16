package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject

/**
 * {@linkplain ViewSelector} for tree structures.
 */
interface TreeBasedViewSelector extends ViewSelector {
    def EObject getRoot()

    def Iterable<EObject> getChildren(EObject element)

    def void setSelected(EObject element, boolean value) {
        setSelected(getIndexOf(element), value) // TODO TS we need to distinguish manual selection and automatic selection.
    }

    def boolean isComplete()

}
