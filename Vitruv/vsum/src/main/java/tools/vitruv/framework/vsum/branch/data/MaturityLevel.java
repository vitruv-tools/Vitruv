package tools.vitruv.framework.vsum.branch.data;

/**
 * Maturity level of a branch, model version, or individual change (delta).
 *
 * <p>Levels are ordered from least to most mature: DRAFT, REVIEWED, FINAL.
 *
 * <p>Semantics (aligned with the Vitruv-Change {@code MaturityLevel} Ecore enum):
 * <ul>
 *   <li>{@link #DRAFT}: work-in-progress; not yet ready for propagation.</li>
 *   <li>{@link #REVIEWED}: has been reviewed but not yet finalized.</li>
 *   <li>{@link #FINAL}: production-ready; eligible for full propagation.</li>
 * </ul>
 *
 * <p>Default for all newly created branches, versions, and changes is {@link #DRAFT}.
 * The user can manually promote or demote the level at any time.
 * No automatic rules are enforced by this layer.
 */
public enum MaturityLevel {
  DRAFT,
  REVIEWED,
  FINAL
}
