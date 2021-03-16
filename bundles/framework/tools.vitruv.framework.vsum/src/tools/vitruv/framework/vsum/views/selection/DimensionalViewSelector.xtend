package tools.vitruv.framework.vsum.views.selection

import org.eclipse.emf.ecore.EObject

/**
 * {@linkplain ViewSelector} that offers several dimensions in which a selection can be made to select a specific view.
 */
interface DimensionalViewSelector extends ViewSelector {
    def Iterable<ViewDimension> getDimensions()

    def Iterable<EObject> getElementsFor(ViewDimension dimension)

    def void setSelected(ViewDimension dimension, EObject element, boolean value)
}
