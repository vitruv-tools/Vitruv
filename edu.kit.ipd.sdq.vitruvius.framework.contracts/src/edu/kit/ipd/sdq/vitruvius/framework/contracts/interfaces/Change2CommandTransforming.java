package edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;

public interface Change2CommandTransforming {
    /** The extension point ID. **/
    String ID = "edu.kit.ipd.sdq.vitruvius.framework.contracts.change2commandtransforming";

    void transformChanges2Commands(Blackboard blackboard);

    TransformationMetamodelPair getTransformableMetamodels();

    void setUserInteracting(UserInteracting userInteracting);
}
