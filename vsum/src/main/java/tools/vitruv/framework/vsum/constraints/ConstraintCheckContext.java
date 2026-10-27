package tools.vitruv.framework.vsum.constraints;

import java.util.Collection;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.CorrespondenceModelView;

/**
 * Read access to the state of a virtual model for a {@link ConstraintChecker}. The elements must
 * not be modified while checking constraints.
 */
public interface ConstraintCheckContext {

  /**
   * Gets the model resources of the virtual model.
   *
   * @return the model resources
   */
  Collection<Resource> getModelResources();

  /**
   * Gets the correspondences between the elements of the virtual model.
   *
   * @return a read-only view on the correspondence model
   */
  CorrespondenceModelView<Correspondence> getCorrespondenceModel();
}
