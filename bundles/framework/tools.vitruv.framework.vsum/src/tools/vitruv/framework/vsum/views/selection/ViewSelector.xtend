package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.vsum.views.View

interface ViewSelector {
    def Iterable<EObject> getElements()
    
    def void setSelected(EObject eObject, boolean value)

    def boolean isValid()

    def View createView()
    
    def boolean isSelected(EObject eObject)

    def Iterable<EObject> getSelectedElements()
}
