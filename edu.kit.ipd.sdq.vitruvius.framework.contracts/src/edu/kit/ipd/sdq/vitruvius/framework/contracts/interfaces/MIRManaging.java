package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Responses;

public interface MIRManaging extends MappingManaging, InvariantProviding, ResponseProviding {
    void addInvariants(Invariants invariants);

    void addResponses(Responses responses);
}
