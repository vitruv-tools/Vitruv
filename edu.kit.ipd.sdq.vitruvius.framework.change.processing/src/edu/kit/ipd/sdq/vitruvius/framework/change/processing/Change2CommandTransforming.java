package edu.kit.ipd.sdq.vitruvius.framework.change.processing;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationMetamodelPair;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;

public interface Change2CommandTransforming {
    List<Command> transformChange2Commands(VitruviusChange change, CorrespondenceModel correspondenceModel);
    TransformationMetamodelPair getTransformableMetamodels();
    void setUserInteracting(UserInteracting userInteracting);
}
