package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ValidationResult;

public interface Validating {
    ValidationResult validate(Invariants invariants);

    ValidationResult validate(ModelInstance modelInstance, Invariants invariants);

    ValidationResult validate(ModelInstance modelInstanceA, ModelInstance modelInstanceB, Invariants invariants);

    // TODO provide all three validate methods again with a detail diagnostics result
}
