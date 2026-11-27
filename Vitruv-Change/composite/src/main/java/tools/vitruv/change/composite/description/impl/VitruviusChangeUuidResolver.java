package tools.vitruv.change.composite.description.impl;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.change.atomic.uuid.AtomicEChangeUuidResolver;
import tools.vitruv.change.atomic.uuid.Uuid;
import tools.vitruv.change.composite.description.TransactionalChange;
import tools.vitruv.change.composite.description.VitruviusChange;

/** Resolves and applies {@link VitruviusChange}s of {@link Uuid}s. */
public class VitruviusChangeUuidResolver extends AbstractVitruviusChangeResolver<Uuid> {
  private AtomicEChangeUuidResolver atomicChangeResolver;

  /**
   * Creates a new {@link VitruviusChangeUuidResolver} with the given {@link
   * AtomicEChangeUuidResolver}.
   *
   * @param atomicChangeResolver the {@link AtomicEChangeUuidResolver} to use for resolving and
   *     applying atomic changes
   */
  public VitruviusChangeUuidResolver(AtomicEChangeUuidResolver atomicChangeResolver) {
    this.atomicChangeResolver = atomicChangeResolver;
  }

  @Override
  public VitruviusChange<EObject> resolveAndApply(VitruviusChange<Uuid> change) {
    return transformVitruviusChange(
        change, atomicChangeResolver::resolveAndApplyForward, this::onTransactionEnd);
  }

  @Override
  public VitruviusChange<Uuid> assignIds(VitruviusChange<EObject> change) {
    return transformVitruviusChange(
        change, atomicChangeResolver::assignIds, this::onTransactionEnd);
  }

  private void onTransactionEnd(TransactionalChange<?> transactionalChange) {
    atomicChangeResolver.endTransaction();
  }
}
