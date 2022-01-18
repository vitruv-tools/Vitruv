package tools.vitruv.framework.vsum.views.selectors

import org.eclipse.emf.ecore.EObject

interface ViewDimension { // TODO TS if these signatures stay this way, extract shared interface with ViewSelector (ElementSelector/Selecting/SelectionMaking)

    def Iterable<EObject> getElements()

    def void setSelected(int index, boolean value)

    def boolean isSelected(int index)

    def Iterable<EObject> getSelectedElements()
}
