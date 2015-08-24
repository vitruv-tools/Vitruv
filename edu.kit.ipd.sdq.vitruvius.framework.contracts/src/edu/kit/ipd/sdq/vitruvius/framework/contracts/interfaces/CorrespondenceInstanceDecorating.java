package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;

public interface CorrespondenceInstanceDecorating {
    /** The extension point ID. **/
    String ID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.correspondenceinstancedecorating";

    CorrespondenceInstanceDecorator decorate(CorrespondenceInstanceDecorator correspondenceInstance);
}
