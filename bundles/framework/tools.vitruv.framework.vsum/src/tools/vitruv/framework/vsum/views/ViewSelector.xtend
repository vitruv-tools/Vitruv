package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.ENamedElement

interface ViewSelector {
    def int size()

    def Iterable<ENamedElement> getElements()

    def void setSelected(int index, boolean value)

    def boolean isValid()

    def View createView()

    def boolean isSelected(int index)

    def Iterable<ENamedElement> getSelectedElements()

    def int getIndexOf(ENamedElement element)
}
