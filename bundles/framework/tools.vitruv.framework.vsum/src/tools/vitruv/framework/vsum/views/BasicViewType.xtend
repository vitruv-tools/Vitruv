package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

class BasicViewType implements ViewType {

    val ResourceSet modelResourceSet
    val String name

    new(String name, ResourceSet modelResourceSet) {
        this.name = name
        this.modelResourceSet = modelResourceSet
    }

    override createSelector() {
        return new BasicViewSelector(this, modelResourceSet.resources.map[allContents.toList].flatten.toList)
    }

    override createView(ViewSelector selector) {
        return new FilterableModelView(modelResourceSet.resources, selector.selectedElements)
    }

    override getName() {
        return name
    }

}
