package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.ENamedElement

/**
 * {@linkplain ViewSelector} for tree structures.
 */
interface TreeBasedViewSelector extends ViewSelector {
    def ENamedElement getRoot()

    def Iterable<ENamedElement> getChildren(ENamedElement element)

    def void setSelected(ENamedElement element, boolean value) {
        setSelected(getIndexOf(element), value) // TODO TS we need to distinguish manual selection and automatic selection.
    }

    def boolean isComplete()

}
