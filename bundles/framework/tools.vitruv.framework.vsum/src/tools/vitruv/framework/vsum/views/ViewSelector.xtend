package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.EObject

interface ViewSelector {
    def int size()

    def Iterable<EObject> getElements()

    def void setSelected(int index, boolean value)

    def boolean isValid()

    def View createView()

    def boolean isSelected(int index)

    def Iterable<EObject> getSelectedElements()

    def int getIndexOf(EObject element)
}
