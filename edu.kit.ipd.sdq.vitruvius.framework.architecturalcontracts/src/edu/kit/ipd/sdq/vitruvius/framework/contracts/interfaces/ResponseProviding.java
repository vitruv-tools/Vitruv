package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Responses;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;

public interface ResponseProviding {

    Responses getResponses(VURI mmURI);
}
