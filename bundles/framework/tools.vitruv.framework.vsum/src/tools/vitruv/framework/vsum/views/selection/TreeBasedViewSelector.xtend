package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.vsum.views.ViewSelector

/**
 * {@linkplain ViewSelector} for tree structures.
 */
interface TreeBasedViewSelector extends ViewSelector {
    def EObject getRoot()

    def Iterable<EObject> getChildren(EObject element)

	// TODO TS/TK we need to distinguish manual selection and automatic selection.
    
    def boolean isComplete()
}
