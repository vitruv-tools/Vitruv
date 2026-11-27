package tools.vitruv.change.correspondence.model;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.correspondence.Correspondence;
import tools.vitruv.change.correspondence.view.CorrespondenceModelView;

/**
 * Contains and manages correspondences, each consisting of two pairs of elements in different
 * models. A correspondence describes the semantic relation between two sets of elements in
 * different models. This interface serves as a generic interface that is realized in a
 * implementations and can be accessed via views on that internal representation derived from {@link
 * CorrespondenceModelView}, which are aware of the concrete type of correspondences to be handled.
 *
 * @author kramerm
 * @author Heiko Klare
 */
public interface CorrespondenceModel extends AutoCloseable {
  /**
   * Creates a correspondence of given type C with the given tag between the given lists of {@link
   * EObject}s.
   *
   * @param firstEObjects the first list of {@link EObject}s, must not be {@code null} or empty
   * @param secondEObjects the second list of {@link EObject}s, must not be {@code null} or empty
   * @param tag the tag to be added to the correspondence or <code>null</code> if none shall be
   *     added
   * @param correspondenceCreator a supplier for creating correspondences of the appropriate type
   * @param <C> the type of correspondence to create
   * @return the created correspondence
   */
  public <C extends Correspondence> C addCorrespondenceBetween(
      List<EObject> firstEObjects,
      List<EObject> secondEObjects,
      String tag,
      Supplier<C> correspondenceCreator);

  /**
   * Returns whether at least one object corresponds to the given objects.
   *
   * @param sourceEObjects the objects for which correspondences should be looked up, must not be
   *     {@code null} or empty
   * @return {@code true} if number of corresponding objects > 0
   */
  public boolean hasCorrespondences(List<EObject> sourceEObjects);

  /**
   * Returns the elements corresponding to the given ones, if the correspondence is of the given
   * type and contains the given tag.
   *
   * @param correspondenceType the type of correspondence to filter for, must not be {@code null}
   * @param sourceEObjects the objects to get the corresponding ones for, must not be {@code null}
   * @param tag the tag to filter correspondences for. If the tag is {@code null}, all
   *     correspondences will be returned
   * @return the elements corresponding to the given ones
   */
  public Set<List<EObject>> getCorrespondingEObjects(
      Class<? extends Correspondence> correspondenceType, List<EObject> sourceEObjects, String tag);

  /**
   * Removes the correspondences of the given type and with the given tag between the given lists of
   * {@link EObject}s.
   *
   * @param correspondenceType the type of correspondence to filter for, must not be {@code null}
   * @param firstEObjects the first list of corresponding {@link EObject}s, must not be {@code null}
   *     or empty
   * @param secondEObjects the second list of corresponding {@link EObject}s, must not be {@code
   *     null} or empty
   * @param tag the tag to filter removed correspondences for or {@code null} if all correspondences
   *     shall be removed
   * @param <C> the type of correspondence to remove
   * @return the removed correspondences
   */
  public <C extends Correspondence> Set<C> removeCorrespondencesBetween(
      Class<C> correspondenceType,
      List<EObject> firstEObjects,
      List<EObject> secondEObjects,
      String tag);
}
