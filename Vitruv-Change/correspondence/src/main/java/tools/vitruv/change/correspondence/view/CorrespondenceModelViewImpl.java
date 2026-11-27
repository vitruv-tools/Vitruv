package tools.vitruv.change.correspondence.view;

import java.util.List;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.CorrespondenceModel;

class CorrespondenceModelViewImpl<C extends Correspondence> implements CorrespondenceModelView<C> {
  private final CorrespondenceModel correspondenceModel;
  private final Class<C> correspondenceType;

  public CorrespondenceModelViewImpl(
      CorrespondenceModel correspondenceModel, Class<C> correspondenceType) {
    this.correspondenceModel = correspondenceModel;
    this.correspondenceType = correspondenceType;
  }

  protected CorrespondenceModel getCorrespondenceModel() {
    return correspondenceModel;
  }

  protected Class<C> getCorrespondenceType() {
    return correspondenceType;
  }

  @Override
  public boolean hasCorrespondences(List<EObject> sourceEObjects) {
    return this.getCorrespondingEObjects(sourceEObjects).size() > 0;
  }

  @Override
  public Set<List<EObject>> getCorrespondingEObjects(List<EObject> sourceEObjects) {
    return correspondenceModel.getCorrespondingEObjects(correspondenceType, sourceEObjects, null);
  }

  @Override
  public Set<List<EObject>> getCorrespondingEObjects(List<EObject> sourceEObjects, String tag) {
    return correspondenceModel.getCorrespondingEObjects(correspondenceType, sourceEObjects, tag);
  }

  @Override
  public <V extends C> CorrespondenceModelView<V> getView(Class<V> correspondenceType) {
    return new CorrespondenceModelViewImpl<>(correspondenceModel, correspondenceType);
  }
}
