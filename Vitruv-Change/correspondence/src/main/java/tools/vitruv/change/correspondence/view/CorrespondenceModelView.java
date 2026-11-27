package tools.vitruv.change.correspondence.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.correspondence.Correspondence;

/**
 * A view on a {@link tools.vitruv.change.correspondence.model.CorrespondenceModel} that is aware of
 * the actual correspondence type to be handled.
 *
 * @author Heiko Klare
 * @param <C> - the type of correspondences to be handled, i.e., to be retrieved, added or removed
 */
public interface CorrespondenceModelView<C extends Correspondence> {
  /**
   * Returns whether at least one object corresponds to the given objects.
   *
   * @param eObjects the objects for which correspondences should be looked up, must not be {@code
   *     null} or empty
   * @return {@code true} if number of corresponding objects > 0
   */
  public boolean hasCorrespondences(List<EObject> eObjects);

  /**
   * Returns whether at least one object corresponds to the given object.
   *
   * @param eObject the object for which correspondences should be looked up, must not be {@code
   *     null}
   * @return {@code true} if number of corresponding objects > 0
   */
  public default boolean hasCorrespondences(EObject eObject) {
    if (eObject == null) {
      return false;
    }
    return hasCorrespondences(List.of(eObject));
  }

  /**
   * Returns all elements corresponding to the given ones.
   *
   * @param sourceObjects the objects to get the corresponding ones for, must not be {@code null} or
   *     empty
   * @return the elements corresponding to the given ones
   */
  public Set<List<EObject>> getCorrespondingEObjects(List<EObject> sourceObjects);

  /**
   * Returns the elements corresponding to the given one.
   *
   * @param sourceObject the object to get the corresponding ones for, may be {@code null}
   * @return the elements corresponding to the given one. Is empty if the {@code sourceObject} is
   *     {@code null}.
   */
  public default Set<EObject> getCorrespondingEObjects(EObject sourceObject) {
    if (sourceObject == null) {
      return Collections.emptySet();
    }
    return flatten(getCorrespondingEObjects(List.of(sourceObject)));
  }

  /**
   * Returns the elements corresponding to the given ones for all correspondences between these
   * elements containing the given tag.
   *
   * @param sourceObjects the objects to get the corresponding ones for, must not be {@code null} or
   *     empty
   * @param tag the tag to filter correspondences for. If the tag is {@code null}, all
   *     correspondences will be checked
   * @return the elements corresponding to the given ones
   */
  public Set<List<EObject>> getCorrespondingEObjects(List<EObject> sourceObjects, String tag);

  /**
   * Returns the elements corresponding to the given ones for all correspondences between these
   * elements containing the given tag.
   *
   * @param sourceObject the object to get the corresponding ones for, may be {@code null}
   * @param tag the tag to filter correspondences for. If the tag is {@code null}, all
   *     correspondences will be checked
   * @return the elements corresponding to the given one. Is empty if the {@code sourceObject} is
   *     {@code null}.
   */
  public default Set<EObject> getCorrespondingEObjects(EObject sourceObject, String tag) {
    if (sourceObject == null) {
      return Collections.emptySet();
    }
    return flatten(getCorrespondingEObjects(List.of(sourceObject), tag));
  }

  private static <O extends EObject> Set<O> flatten(Set<List<O>> toFlatten) {
    Set<O> correspondingElements = new HashSet<>();
    toFlatten.forEach(correspondingElements::addAll);
    return correspondingElements;
  }

  /**
   * Returns a view on the {@link tools.vitruv.change.correspondence.model.CorrespondenceModel}
   * restricted to the specified kind of {@link Correspondence}, which must be a subtype of the
   * {@link Correspondence} type used in this view. The functions of the view will only act on the
   * given implementation of {@link Correspondence}s.
   *
   * @param correspondenceType the type of correspondence to create a view for
   * @param <V> the type of correspondence to create a view for
   * @return the restricted view on the {@link
   *     tools.vitruv.change.correspondence.model.CorrespondenceModel}
   */
  public <V extends C> CorrespondenceModelView<V> getView(Class<V> correspondenceType);
}
