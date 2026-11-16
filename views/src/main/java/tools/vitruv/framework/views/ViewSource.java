package tools.vitruv.framework.views;

import java.util.Collection;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.resource.Resource;
import tools.vitruv.change.atomic.uuid.UuidResolver;

/** A view source giving access to the underlying source models of the view. */
public interface ViewSource {
  /**
   * Returns {@link Resource}s representing the source models of a view to be instantiated.
   *
   * @return {@link Resource}s as the sources of a view
   */
  Collection<Resource> getViewSourceModels();

  /**
   * Returns the {@link UuidResolver} associated with the resources in this view source.
   *
   * @return the {@link UuidResolver} of this view source.
   */
  public UuidResolver getUuidResolver();

  /**
   * Executes the given action while no change is propagated into this view source, so that the
   * action sees a consistent state of the {@link #getViewSourceModels() source models} and the
   * {@link #getUuidResolver() UUID resolver}. View types read the source models only within such
   * an action. The action itself may propagate changes into this view source.
   *
   * <p>The default implementation executes the action directly, which suits view sources that are
   * not changed concurrently.
   *
   * @param <T> the type of the result of the action
   * @param action the action to execute
   * @return the result of the action
   */
  default <T> T executeExclusively(Supplier<T> action) {
    return action.get();
  }
}
