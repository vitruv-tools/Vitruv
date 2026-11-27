package tools.vitruv.change.propagation;

/**
 * A {@link ChangePropagationObservableRegistry} (de)registers observers
 * for change propagation.
 */
public interface ChangePropagationObservableRegistry {
  /**
   * Registers <code>observer</code>.
   *
   * @param observer - {@link ChangePropagationObserver}
   */
  void registerObserver(final ChangePropagationObserver observer);

  /**
   * Deregisters <code>observer</code>.
   *
   * @param observer - {@link ChangePropagationObserver}
   */
  void deregisterObserver(final ChangePropagationObserver observer);
}
