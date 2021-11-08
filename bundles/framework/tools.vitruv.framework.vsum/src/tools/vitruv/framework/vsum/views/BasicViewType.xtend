package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector
import tools.vitruv.framework.vsum.VirtualModel

class BasicViewType implements ViewType {

    val ResourceSet modelResourceSet
    val String name
    val VirtualModel virtualModel

    new(String name, ResourceSet modelResourceSet, VirtualModel virtualModel) {
        this.name = name
        this.modelResourceSet = modelResourceSet
        this.virtualModel = virtualModel
    }

    override createSelector() {
        return new BasicViewSelector(this, modelResourceSet.resources.map[allContents.toList].flatten.toList)
    }

    override createView(ViewSelector selector) {
        return new FilterableModelView(modelResourceSet.resources, selector.selectedElements, virtualModel)
    }

    override getName() {
        return name
    }

}
