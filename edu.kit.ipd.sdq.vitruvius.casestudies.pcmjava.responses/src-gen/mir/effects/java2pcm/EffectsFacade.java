package mir.effects.java2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectsFacade;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EffectsFacade extends AbstractEffectsFacade {
  public EffectsFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
}
