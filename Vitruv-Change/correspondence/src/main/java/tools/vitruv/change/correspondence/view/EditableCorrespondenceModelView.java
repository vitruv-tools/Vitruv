package tools.vitruv.change.correspondence.view;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.model.CorrespondenceModel;

/**
 * A editable view on a {@link CorrespondenceModel} that is aware of the actual correspondence type
 * to be handled.
 *
 * @author Heiko Klare
 * @param <C> - the type of correspondences to be handled, i.e., to be retrieved, added or removed
 */
public interface EditableCorrespondenceModelView<C extends Correspondence>
    extends CorrespondenceModelView<C> {
  /**
   * Creates a correspondence of type C with the given tag between the given lists of {@link
   * EObject}s.
   *
   * @param firstEObjects the first list of {@link EObject}s, must not be {@code null} or empty
   * @param secondEObjects the second list of {@link EObject}s, must not be {@code null} or empty
   * @param tag the tag to be added to the correspondence, must not be {@code null}
   * @return the created correspondence
   */
  public C addCorrespondenceBetween(
      List<EObject> firstEObjects, List<EObject> secondEObjects, String tag);

  /**
   * Creates a correspondence of type C with the given tag between the given {@link EObject}s.
   *
   * @param firstEObject the first {@link EObject}, must not be {@code null}
   * @param secondEObject the second {@link EObject}, must not be {@code null}
   * @param tag the tag to be added to the correspondence, must not be {@code null}
   * @return the created correspondence
   */
  public default C addCorrespondenceBetween(
      EObject firstEObject, EObject secondEObject, String tag) {
    return addCorrespondenceBetween(List.of(firstEObject), List.of(secondEObject), tag);
  }

  /**
   * Removes the correspondences between the given lists of {@link EObject}s with the given tag.
   *
   * @param firstEObjects the first list of {@link EObject}s, must not be {@code null} or empty
   * @param secondEObjects the second list of {@link EObject}s, must not be {@code null} or empty
   * @param tag the tag to filter correspondences for or {@code null} if all correspondences shall
   *     be removed
   * @return the removed correspondences
   */
  public Set<C> removeCorrespondencesBetween(
      List<EObject> firstEObjects, List<EObject> secondEObjects, String tag);

  /**
   * Removes the correspondences between the given {@link EObject}s with the given tag.
   *
   * @param firstEObject the first {@link EObject}, must not be {@code null}
   * @param secondEObject the second {@link EObject}, must not be {@code null}
   * @param tag the tag to filter correspondences for or {@code null} if all correspondences shall
   *     be removed
   * @return the removed correspondences
   */
  public default Set<C> removeCorrespondencesBetween(
      EObject firstEObject, EObject secondEObject, String tag) {
    return removeCorrespondencesBetween(List.of(firstEObject), List.of(secondEObject), tag);
  }

  /**
   * Returns an editable view on the {@link CorrespondenceModel} restricted to the specified kind of
   * {@link Correspondence}, which must be a subtype of the {@link Correspondence} type used in this
   * view. The functions of the view will only act on the given implementation of {@link
   * Correspondence}s.
   *
   * @param correspondenceType the type of correspondence to create a view for
   * @param correspondenceTypeSupplier a supplier for creating correspondences of the type to create
   *     a view for
   * @param <V> the type of correspondence to create a view for
   * @return the restricted view on the {@link CorrespondenceModel}
   */
  public <V extends C> EditableCorrespondenceModelView<V> getEditableView(
      Class<V> correspondenceType, Supplier<V> correspondenceTypeSupplier);
}
