package tools.vitruv.change.correspondence.view;

import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.CorrespondenceFactory;
import tools.vitruv.change.correspondence.model.CorrespondenceModel;

/** Factory for creating correspondence model views. */
public final class CorrespondenceModelViewFactory {

  /** Private constructor to prevent instantiation. */
  private CorrespondenceModelViewFactory() {}

  /**
   * Creates a new correspondence model view for the given correspondence model.
   *
   * @param correspondenceModel The correspondence model.
   * @return The new correspondence model view.
   */
  public static CorrespondenceModelView<Correspondence> createCorrespondenceModelView(
      CorrespondenceModel correspondenceModel) {
    return new CorrespondenceModelViewImpl<Correspondence>(
        correspondenceModel, Correspondence.class);
  }

  /**
   * Creates a new editable correspondence model view for the given correspondence model.
   *
   * @param correspondenceModel The correspondence model.
   * @return The new editable correspondence model view.
   */
  public static EditableCorrespondenceModelView<Correspondence>
      createEditableCorrespondenceModelView(CorrespondenceModel correspondenceModel) {
    return new EditableCorrespondenceModelViewImpl<>(
        correspondenceModel,
        Correspondence.class,
        () -> CorrespondenceFactory.eINSTANCE.createManualCorrespondence());
  }
}
