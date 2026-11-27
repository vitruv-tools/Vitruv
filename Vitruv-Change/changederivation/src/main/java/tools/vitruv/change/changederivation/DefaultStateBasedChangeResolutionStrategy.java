package tools.vitruv.change.changederivation;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.match.IMatchEngine.Factory;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.utils.UseIdentifiers;

/**
 * This is a default strategy to resolve a diff as a sequence of individual changes.
 */
public class DefaultStateBasedChangeResolutionStrategy
    extends AbstractStateBasedChangeResolutionStrategy {

  /** The identifier matching behavior used by this strategy. */
  public final UseIdentifiers useIdentifiers;

  /**
   * Creates a new instance with the default identifier matching behavior
   * which is match by identifier when available.
   */
  public DefaultStateBasedChangeResolutionStrategy() {
    this(UseIdentifiers.WHEN_AVAILABLE);
  }

  /**
   * Creates a new instance with the provided identifier matching behavior.
   *
   * @param useIdentifiers The identifier matching behavior to use.
   */
  public DefaultStateBasedChangeResolutionStrategy(UseIdentifiers useIdentifiers) {
    this.useIdentifiers = useIdentifiers;
  }

  @Override
  protected Collection<Factory> getMatchEngineFactories() {
    return List.of(new MatchEngineFactoryImpl(useIdentifiers));
  }

  @Override
  protected IDiffEngine getDiffEngine() {
    return new DefaultDiffEngine(new DiffBuilder());
  }

  @Override
  protected Collection<IPostProcessor.Descriptor> getPostProcessors() {
    return List.of();
  }
}
