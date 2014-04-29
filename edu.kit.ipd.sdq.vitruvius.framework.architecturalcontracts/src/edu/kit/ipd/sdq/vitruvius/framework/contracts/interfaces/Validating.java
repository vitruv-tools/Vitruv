package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;

public interface Validating {
    boolean validate(Invariants invariants);

    boolean validate(ModelInstance modelInstance, Invariants invariants);

    boolean validate(ModelInstance modelInstanceA, ModelInstance modelInstanceB, Invariants invariants);

    // TODO provide all three validate methods again with a detail diagnostics result
}
