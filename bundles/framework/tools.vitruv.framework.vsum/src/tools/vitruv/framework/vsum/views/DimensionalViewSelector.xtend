package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.ENamedElement

/**
 * {@linkplain ViewSelector} that offers several dimensions in which a selection can be made to select a specific view.
 */
interface DimensionalViewSelector extends ViewSelector {
    def Iterable<ViewDimension> getDimensions()

    def Iterable<ENamedElement> getElementsFor(ViewDimension dimension)

    def void setSelected(ViewDimension dimension, ENamedElement element, boolean value)
}
