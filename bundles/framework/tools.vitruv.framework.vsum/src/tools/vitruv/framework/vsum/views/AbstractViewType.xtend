package tools.vitruv.framework.vsum.views

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import static extension com.google.common.base.Preconditions.checkNotNull

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

abstract class AbstractViewType implements ViewType {

    val String name
    protected val VirtualModel virtualModel

    new(String name, VirtualModel virtualModel) { // TODO TS: How should the viewtype access the models? inversion of control?
        this.name = name
        this.virtualModel = virtualModel
    }

    override createView(ViewSelector selector) {
        return new BasicModelView(this, virtualModel)
    }

    override getName() {
        return name
    }

    override updateView(View view) {
        val viewResourceSet = new ResourceSetImpl().withGlobalFactories
        virtualModel.resourceSet.resources.forEach [
            val viewResource = viewResourceSet.resourceFactoryRegistry?.getFactory(it.URI)?.createResource(it.URI).
                checkNotNull("Cannot create view resource: " + it.URI)
            viewResource.contents.addAll(EcoreUtil.copyAll(it.contents))
            viewResourceSet.resources += viewResource
        ]
        return viewResourceSet
    }
}
