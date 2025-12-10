package tools.vitruv.framework.vsum.internal;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.vsum.VirtualModel;

/**
 * Internal interface for virtual models in the VSUM framework.
 */
public interface InternalVirtualModel extends VirtualModel, ChangeableViewSource {

    /**
     * Gets the correspondence model view associated with this virtual model.
     * 
     * @return the editable correspondence model view
     */
    EditableCorrespondenceModelView<Correspondence> getCorrespondenceModel();

    /**
     * Gets the model instance for the given model URI.
     * 
     * @param modelUri the URI of the model
     * @return the model instance
     */
    ModelInstance getModelInstance(URI modelUri);

    /**
     * Disposes of the virtual model, releasing any resources held.
     */
    void dispose();
}
