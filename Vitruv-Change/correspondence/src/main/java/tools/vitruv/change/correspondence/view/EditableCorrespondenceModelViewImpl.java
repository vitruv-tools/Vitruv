package tools.vitruv.change.correspondence.view;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.CorrespondenceModel;

class EditableCorrespondenceModelViewImpl<C extends Correspondence>
    extends CorrespondenceModelViewImpl<C> implements EditableCorrespondenceModelView<C> {
  final Supplier<C> correspondenceSupplier;

  public EditableCorrespondenceModelViewImpl(
      CorrespondenceModel correspondenceModel,
      Class<C> correspondenceType,
      Supplier<C> correspondenceSupplier) {
    super(correspondenceModel, correspondenceType);
    this.correspondenceSupplier = correspondenceSupplier;
  }

  @Override
  public C addCorrespondenceBetween(List<EObject> eObjects1, List<EObject> eObjects2, String tag) {
    return getCorrespondenceModel()
        .addCorrespondenceBetween(eObjects1, eObjects2, tag, correspondenceSupplier);
  }

  @Override
  public Set<C> removeCorrespondencesBetween(
      List<EObject> aEObjects, List<EObject> bEObjects, String tag) {
    return getCorrespondenceModel()
        .removeCorrespondencesBetween(getCorrespondenceType(), aEObjects, bEObjects, tag);
  }

  @Override
  public <V extends C> EditableCorrespondenceModelView<V> getEditableView(
      Class<V> correspondenceType, Supplier<V> correspondenceTypeSupplier) {
    return new EditableCorrespondenceModelViewImpl<V>(
        getCorrespondenceModel(), correspondenceType, correspondenceTypeSupplier);
  }
}
