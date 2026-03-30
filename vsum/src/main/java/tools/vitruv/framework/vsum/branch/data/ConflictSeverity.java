package tools.vitruv.framework.vsum.branch.data;

/**
 * Severity of a semantic conflict detected during branch replay.
 *
 * <p>Determined by the types of changes involved:
 * <ul>
 *   <li>{@link #LOW} – independent additions or insertions; unlikely to break consistency,
 *       may be auto-resolvable.</li>
 *   <li>{@link #MEDIUM} – same attribute or reference on the same element changed to different
 *       values on both branches; developer must pick the winner.</li>
 *   <li>{@link #HIGH} – one branch deleted an element the other still modifies, or both
 *       branches changed a reference in incompatible directions; requires explicit decision.</li>
 * </ul>
 */
public enum ConflictSeverity {

  /** Independent additions/insertions; low risk, often auto-resolvable. */
  LOW,

  /** Same scalar attribute or single-valued reference changed to different values. */
  MEDIUM,

  /** Delete-vs-modify or incompatible reference change; highest resolution priority. */
  HIGH
}
