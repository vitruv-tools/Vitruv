package tools.vitruv.framework.vsum.modelsynchronization;

import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;

public interface ChangePropagator {
    List<PropagatedChange> propagateChange(VitruviusChange change);

    void addChangePropagationListener(ChangePropagationListener propagationListener);

    void removeChangePropagationListener(ChangePropagationListener propagationListener);
}