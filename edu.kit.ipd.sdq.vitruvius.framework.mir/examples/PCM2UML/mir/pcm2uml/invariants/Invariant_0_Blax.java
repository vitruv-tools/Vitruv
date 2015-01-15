package mir.pcm2uml.invariants;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicInvariant;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;

@DynamicInvariant
@SuppressWarnings("all")
public class Invariant_0_Blax {
  public boolean check(final Action context) {
    Activity _activity = context.getActivity();
    return (!Objects.equal(_activity, null));
  }
}
