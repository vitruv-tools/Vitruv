package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Responses;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface MIRManaging extends MappingManaging {
	void addInvariants(Invariants invariants);
	Invariants getInvariants(VURI mmURI);
	void addResponses(Responses responses);
	Responses getResponses(VURI mmURI);
}
