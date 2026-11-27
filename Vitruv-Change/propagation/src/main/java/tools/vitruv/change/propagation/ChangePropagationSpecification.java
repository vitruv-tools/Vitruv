package tools.vitruv.change.propagation;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.composite.MetamodelDescriptor;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;
import tools.vitruv.change.interaction.UserInteractor;
import tools.vitruv.change.utils.ResourceAccess;

/**
 * A specification of change propagation, which is able to process changes
 * and update other, dependent models to reflect these changes as well.
 */
public interface ChangePropagationSpecification extends ChangePropagationObservable {
  /**
   * Sets the <code>UserInteractor</code> to be used by this
   * <code>ChangePropagationSpecification</code> whenever the user needs to be involved
   * into the decision how to propagate a change. This may happen during the execution
   * of this specification, i.e., when calling {@link #propagateChange}.
   *
   * @param userInteractor - The {@link UserInteractor} to be used when executing 
   *      this specification. If set to <code>null</code>, will lead to exceptions
   *      if it is accessed within {@link #propagateChange}.
   */
  void setUserInteractor(final UserInteractor userInteractor);

  /**
   * Returns the descriptor for the metamodel containing the namespace URIs of the root packages
   * of the metamodel of elements whose changes are handled by this 
   * <code>ChangePropagationSpecification</code>.
   *
   * @return a metamodel descriptor representing the source namespace URIs
   */
  MetamodelDescriptor getSourceMetamodelDescriptor();

  /**
   * Returns the descriptor for the metamodel containing the namespace URIs of the root packages
   * of the metamodel of elements whose are changed by this 
   * <code>ChangePropagationSpecification</code> when executing {@link #propagateChange}.
   *
   * @return a metamodel descriptor representing the target namespace URIs
   */
  MetamodelDescriptor getTargetMetamodelDescriptor();

  /**
   * Returns whether this <code>ChangePropagationSpecification</code> handles the given change,
   * i.e., whether {@link #propagateChange} will perform changes when applied to that change.
   *
   * @param change - the atomic change for which to check
   *      whether this specification reacts to it. Must not be <code>null</code>.
   * @param correspondenceModel - the correspondence model to retrieve information
   *      about the target model from. Must not be <code>null</code>.
   * @return <code>true</code> if {@link #propagateChange} will perform modifications 
   *      in response to the given change, <code>false</code> otherwise.
   */
  boolean doesHandleChange(EChange<EObject> change,
      EditableCorrespondenceModelView<Correspondence> correspondenceModel);

  /**
   * Performs modifications in target models identified by accessing the given
   * <code>CorrespondenceModel</code> for the elements changed by the given <code>EChange</code>
   * in order to reflect the changes in the target model.
   *
   * @param change - the atomic change which shall be propagated.
   *     Should affect only elements in an instance of a source metamodel of this specification
   *     (see {@link #getSourceMetamodelDescriptor}). Must not be <code>null</code>.
   * @param correspondenceModel - the correspondence model to retrieve information about the target
   *     model from. Must not be <code>null</code>.
   * @param resourceAccess - an object for resource access, 
   *     in particular to create new model files. Must not be <code>null</code>.
   */
  void propagateChange(EChange<EObject> change,
      EditableCorrespondenceModelView<Correspondence> correspondenceModel, 
      ResourceAccess resourceAccess);
}
